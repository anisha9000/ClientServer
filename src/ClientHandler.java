/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anisha
 */
public class ClientHandler implements Runnable {

    Socket clientSocket;
    DataInputStream iStream;
    DataOutputStream oStream;
    
    ClientHandler(Socket clientSocket, DataInputStream iStream, DataOutputStream oStream) {
        this.clientSocket = clientSocket;
        this.iStream = iStream;
        this.oStream = oStream;
    }

    @Override
    public void run() {
        String receivedMessage = "";
        String sentMessage = "";
        
        while (!receivedMessage.equalsIgnoreCase("exit")) {
            try {
                receivedMessage = iStream.readUTF();
                if(receivedMessage.equalsIgnoreCase("exit")) {
                    Server.closeServer(clientSocket, iStream, oStream);
                    break;
                }
                System.out.println("line read:" + receivedMessage);
                oStream.writeUTF(receivedMessage.toUpperCase());
            } catch (IOException ex) {
                Server.closeServer(clientSocket, iStream, oStream);
                System.out.println("IOException:" + ex.getMessage());
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        Server.closeServer(clientSocket, iStream, oStream);

    }

}
