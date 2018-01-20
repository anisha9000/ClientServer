
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author anisha
 */
public class Utils {

    static void clientUsage() {
        System.out.println("Usage: java Client <Server ip address> <Server Port number>");
    }

    static void serverUsage() {
        System.out.println("Usage: java Server <Server Port number>");
    }

    static void clientKeyAccessUsage() {
        System.out.println("Key<Integer>-Value<Integer> usage:");
        System.out.println("PUT <Key> <Value>");
        System.out.println("GET <Key>");
        System.out.println("DELETE <Key>");
        System.out.println("EXIT");
    }

    static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    static void saveHashMapToFile(HashMap<Integer, Integer> map) {
        try {
            FileOutputStream fos = new FileOutputStream("valueStore.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static HashMap<Integer, Integer> getHashMapFromFile() {
        HashMap<Integer, Integer> map = null;
        FileInputStream fis;
        try {
            File valueFile = new File("valueStore.ser");
            boolean isFile = valueFile.isFile();
            if (!isFile) {
                map = new HashMap<>();
            } else {
                fis = new FileInputStream("valueStore.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                map = (HashMap) ois.readObject();
                ois.close();
                fis.close();
            }

        } catch (FileNotFoundException | ClassNotFoundException ex) {
            map = new HashMap<>();
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            map = new HashMap<>();
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    static void checkCommandSyntax() {
        //TODO
    }
}
