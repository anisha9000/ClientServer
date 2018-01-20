
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static ServerSocket serverSocket = null;
    private static HashMap<Integer, Integer> dataMap = null;

    static void initServer(int port) {
        try {
            dataMap = Utils.getHashMapFromFile();
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for client");
        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getMessage());
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void closeServer(Socket clientSocket, DataInputStream iStream,
            DataOutputStream oStream) {
        try {
            clientSocket.close();
            iStream.close();
            oStream.close();
            Utils.saveHashMapToFile(dataMap);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void closeServer() {
        try {
            serverSocket.close();
            Utils.saveHashMapToFile(dataMap);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        
        if (args.length < 1) {
            Utils.serverUsage();
            return;
        }
        try {
            int port = Integer.parseInt(args[0]);
            initServer(port);
        } catch (NumberFormatException ex) {
            System.out.println("Incorrect port number");
            Utils.serverUsage();
            return;
        }
        
        System.out.println("Server process started");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected client:"
                        + clientSocket.getInetAddress());

                DataInputStream iStream = new DataInputStream(
                        new BufferedInputStream(clientSocket.getInputStream()));
                DataOutputStream oStream = new DataOutputStream(
                        clientSocket.getOutputStream());

                ClientHandler clientHandler = new ClientHandler(clientSocket,
                        iStream, oStream, dataMap);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

            } catch (IOException ex) {
                closeServer();
                System.out.println("IOException:" + ex.getMessage());
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, 
                        ex);
            }
        }

    }
}
