import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    
    private static Socket socket = null;
    private static DataOutputStream oStream = null;
    private static DataInputStream iStream = null;
    private static BufferedReader in = null;
    
    static void initClient() {
        try {
            socket = new Socket(Constants.SERVER_IP, Constants.PORT);
            iStream = new DataInputStream(System.in);
            oStream = new DataOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(System.in));
            
        } catch (IOException ex) {
            System.out.println("IOException:"+ex.getMessage());
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    static void closeClient() {
        try {
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
        String line = "";
        while(!line.equalsIgnoreCase("exit")) {
            try {
                line = in.readLine();
                System.out.println("Line read:"+line);
                oStream.writeUTF(line);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        closeClient();
        
    }
}
