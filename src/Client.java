
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static Socket socket = null;
    private static DataOutputStream oStream = null;
    private static DataInputStream iStream = null;
    private static Scanner scanner = null;

    static void initClient(String ipAddr, int port) {
        try {
            scanner = new Scanner(System.in);
            socket = new Socket(ipAddr, port);
            iStream = new DataInputStream(socket.getInputStream());
            oStream = new DataOutputStream(socket.getOutputStream());
    
        } catch (IOException ex) {
            System.out.println("IOException:" + ex.getMessage());
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
            System.out.println("IOException:" + ex.getMessage());
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        
        if (args.length < 2) {
            Utils.clientUsage();
            return;
        }
        String ipAddr = args[0];
        try {
            int port = Integer.parseInt(args[1]);
            initClient(ipAddr, port);
        } catch (NumberFormatException ex) {
            System.out.println("Incorrect port number");
            Utils.clientUsage();
            return;
        }
        
        System.out.println("Client process started");

        System.out.println("Connected to server:" + socket.getInetAddress());
        Utils.clientKeyAccessUsage();

        while (true) {
            try {
                String sentCommand = scanner.nextLine();
                String receivedMessage = "";

                boolean isValid = Utils.checkCommandSyntax(sentCommand);
                if(!isValid) {
                    System.out.println("Invalid command");
                    oStream.flush();
                    continue;
                }
                System.out.println(Utils.getCurrentTime() + ": Request: " + sentCommand);
                oStream.writeUTF(sentCommand);
                oStream.flush();

                if (sentCommand.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection");
                    closeClient();
                    break;
                }
                receivedMessage = iStream.readUTF();
                System.out.println(Utils.getCurrentTime() + ": Response: " + receivedMessage);
            } catch (IOException ex) {
                closeClient();
                System.out.println("Check socket connection");
                //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }

        }

    }
}
