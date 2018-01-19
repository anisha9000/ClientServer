
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static ServerSocket serverSocket = null;
    private static HashMap<Integer, Integer> dataMap = null;
    
    static void initServer() {
        try {
            dataMap = new HashMap<>();
            serverSocket = new ServerSocket(Constants.PORT);
            System.out.println("Waiting for client");
        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getMessage());
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void closeServer(Socket clientSocket, DataInputStream iStream, DataOutputStream oStream) {
        try {
            clientSocket.close();
            iStream.close();
            oStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    static void closeServer() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("Server process started");
        initServer();
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected client:"+ clientSocket.getInetAddress());
                
                DataInputStream iStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                DataOutputStream oStream = new DataOutputStream(clientSocket.getOutputStream());
                
                ClientHandler clientHandler = new ClientHandler(clientSocket, 
                        iStream, oStream, dataMap);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

                

            } catch (IOException ex) {
                closeServer();
                System.out.println("IOException:" + ex.getMessage());
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        

    }
}
