
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static Socket socket = null;
    private static ServerSocket server = null;
    private static DataInputStream iStream = null;
    private static DataOutputStream oStream = null;
    
    static void initServer() {
        try {
            server = new ServerSocket(Constants.PORT);
            oStream = new DataOutputStream(System.out);
            System.out.println("Waiting for client");
        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getMessage());
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    static void closeServer() {
        try {
            socket.close();
            server.close();
            iStream.close();
            oStream.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        System.out.println("Server process started");
        initServer();
        try {
            socket = server.accept();
            iStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            
            

            String line = "";
            while (!line.equalsIgnoreCase("exit")) {
                try {
                    line = iStream.readUTF();
                    System.out.println("line read:"+line);
                    oStream.writeUTF(line);
                } catch (IOException ex) {
                    System.out.println("IOException:" + ex.getMessage());
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getMessage());
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        closeServer();

    }
}
