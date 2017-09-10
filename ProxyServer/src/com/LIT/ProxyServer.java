package com.LIT;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProxyServer {
    public static final int PORT = 2500;
    private ServerSocket servSocket;
    
    public ProxyServer() {
        try {
            servSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            Logger.getLogger(ProxyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ProxyServer server = new ProxyServer();
        server.go();
    }
    
    public void go() {
        System.out.println("Server started");
        while(true){
            try {
                Socket socket = servSocket.accept();
                Listener listener = new Listener(socket);
                Thread thread = new Thread(listener);
                thread.start();
                
            } catch (IOException ex) {
                Logger.getLogger(ProxyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
