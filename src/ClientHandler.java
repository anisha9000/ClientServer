/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
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
    private static HashMap<Integer, Integer> dataMap = null;

    ClientHandler(Socket clientSocket, DataInputStream iStream,
            DataOutputStream oStream, HashMap<Integer, Integer> map) {
        this.clientSocket = clientSocket;
        this.iStream = iStream;
        this.oStream = oStream;
        dataMap = map;
    }

    @Override
    public void run() {
        String receivedMessage = "";
        String sentMessage = "";

        while (true) {
            try {
                receivedMessage = iStream.readUTF();
                if (receivedMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection with client:"+ clientSocket.getInetAddress());
                    Server.closeServer(clientSocket, iStream, oStream);
                    break;
                }
                System.out.println("Command received:" + receivedMessage);
                runCommand(receivedMessage);

            } catch (IOException ex) {
                Server.closeServer(clientSocket, iStream, oStream);
                System.out.println("IOException:" + ex.getMessage());
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        Server.closeServer(clientSocket, iStream, oStream);

    }

    private void runCommand(String receivedMessage) {
        String[] command = receivedMessage.split(" ");
        int key = Integer.parseInt(command[1]);
        int value;
        switch (command[0]) {
            case "PUT":
                value = Integer.parseInt(command[2]);
                synchronized (dataMap) {
                    dataMap.put(key, value);
                    try {
                        oStream.writeUTF("Value Stored");
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "GET":
                synchronized (dataMap) {
                    try {
                        if (!dataMap.containsKey(key)) {
                            oStream.writeUTF("Value for " + key + " not found.");
                        } else {
                            value = dataMap.get(key);
                            oStream.writeUTF("Value for " + key + ": " + value);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "DELETE":
                synchronized (dataMap) {
                    dataMap.remove(key);
                    try {
                        oStream.writeUTF("Value for " + key + " removed.");
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

        }
    }

}
