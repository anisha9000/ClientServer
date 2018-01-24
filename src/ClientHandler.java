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
                    System.out.println("Closing connection with client:" + clientSocket.getInetAddress());
                    Server.closeServer(clientSocket, iStream, oStream);
                    break;
                }
                System.out.println(Utils.getCurrentTime() + ": Request: " + receivedMessage);
                runCommand(receivedMessage);

            } catch (IOException ex) {
                Server.closeServer(clientSocket, iStream, oStream);
                System.out.println("IOException:" + ex.getMessage());
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }

        }

        Server.closeServer(clientSocket, iStream, oStream);
        Utils.saveHashMapToFile(dataMap);

    }

    private void runCommand(String receivedMessage) {
        String responseStr = "";

        if (!Utils.checkCommandSyntax(receivedMessage)) {
            System.out.println("Invalid command");
            responseStr = "Invalid command:" + receivedMessage;
            return;
        } else {
            String[] command = receivedMessage.split(" ");

            int key = Integer.parseInt(command[1]);
            int value;
            switch (command[0]) {
                case "PUT":
                    value = Integer.parseInt(command[2]);
                    synchronized (dataMap) {
                        dataMap.put(key, value);
                        responseStr = "Value Stored";
                    }
                    break;
                case "GET":
                    synchronized (dataMap) {
                        if (!dataMap.containsKey(key)) {
                            responseStr = "Value for " + key + " not found.";
                        } else {
                            value = dataMap.get(key);
                            responseStr = "Value for " + key + ": " + value;
                        }
                    }
                    break;
                case "DELETE":
                    synchronized (dataMap) {
                        if (!dataMap.containsKey(key)) {
                            responseStr = "Key " + key + " not found.";
                        } else {
                            dataMap.remove(key);
                            responseStr = "Value for " + key + " removed.";
                        }
                    }
                    break;

            }
        }

        try {
            oStream.writeUTF(responseStr);
            System.out.println(Utils.getCurrentTime() + ": Response: " + responseStr);
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
