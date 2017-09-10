/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.LIT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Артем
 */
public class Client implements Runnable{
    private static int PORT = 2500;
    private static String HOST = "localhost";
    private static String address;
    
    public static void main(String[] args){
        if(args.length!=3){
            System.err.println("insuficient arguments");
        }
        
        address = args[0];
        HOST = args[1];
        PORT = Integer.parseInt(args[2]);
        
        Client client = new Client();
        Thread thread = new Thread(client);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Client started");
        BufferedReader in = null;
        PrintWriter out = null;
        
        try {
            Socket socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            //address = "http://novsu.ru";
            out.println(address);
       
            String s;
            while((s=in.readLine())!=null){
                System.out.println(s);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
                //out.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           

    }
    
    
}
