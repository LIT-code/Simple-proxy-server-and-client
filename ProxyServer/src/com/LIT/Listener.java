package com.LIT;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener implements Runnable{
    private static int CLIENTS=0;
    private Socket socket;
    private String address;
    
    
    public Listener(Socket socket){
        this.socket=socket;
    }
    
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            CLIENTS++;
            System.out.println("New client - user"+ CLIENTS);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedOutputStream out1;
            out1 = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));
                              
            address = in.readLine();
            System.out.println("Received address : " + address);
            checkAddress();
            getContent(address, out1);
            
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void getContent(String address, BufferedOutputStream out1){
        BufferedInputStream in = null;
        try {

            URL url = new URL(address);
            in = new BufferedInputStream(new DataInputStream(url.openStream()));
            byte tmpPocket[] = new byte[1024*20];
            int bytes_read;
            while((bytes_read = in.read(tmpPocket)) !=-1){
                out1.write(tmpPocket, 0, bytes_read);
                out1.flush();
                
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void checkAddress(){
        if(address.startsWith("http"))
            return;
        else if(address.startsWith("GET "))
            address = address.substring(4);
        else if (address.startsWith("POST "))
            address = address.substring(5);
        else if(address.startsWith("CONNECT ")){
            address = address.substring(8);
            address = "https://" + address;
            if(address.endsWith(" HTTP/1.1"))
                 address = address.substring(0, address.length()-13);
        }else{
            System.err.println("Address is incorrect");
        }
        if(address.endsWith(" HTTP/1.1"))
                 address = address.substring(0, address.length()-9);
        System.out.println("Correct address : " + address);
    } 

    
}
