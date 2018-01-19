import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    
    private static Socket socket = null;
    private static DataOutputStream oStream = null;
    private static DataInputStream iStream = null;
    private static BufferedReader in = null;
    private static Scanner scanner = null;
    
    static void initClient() {
        try {
            scanner = new Scanner(System.in);
            socket = new Socket(Constants.SERVER_IP, Constants.PORT);
            iStream = new DataInputStream(socket.getInputStream());
            oStream = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(System.in));
            
        } catch (IOException ex) {
            System.out.println("IOException:"+ex.getMessage());
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    static void closeClient() {
        try {
            scanner.close();
            socket.close();
            iStream.close();
            oStream.close();
        } catch (IOException ex) {
            System.out.println("IOException:"+ex.getMessage());
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Client process started");
        initClient();
        System.out.println("socket:"+socket);
        System.out.println("iStream:"+iStream);
        System.out.println("oStream:"+oStream);
        String sentMessage = "";
        String receivedMessage = "";
        
        while(true) {
            try {
                sentMessage = scanner.nextLine();
                System.out.println("Line read:"+sentMessage);
                oStream.writeUTF(sentMessage);
                
                if(sentMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection");
                    closeClient();
                    break;
                }
                receivedMessage = iStream.readUTF();
                System.out.println("Received Message:"+ receivedMessage);
            } catch (IOException ex) {
                closeClient();
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        
    }
}
