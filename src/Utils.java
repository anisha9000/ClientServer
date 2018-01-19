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
    
    static void clientKeyAccessUsage() {
        System.out.println("Key<Integer>-Value<Integer> usage:");
        System.out.println("PUT <Key> <Value>");
        System.out.println("GET <Key>");
        System.out.println("DELETE <Key>");
        System.out.println("EXIT");
    }

    static void checkCommandSyntax() {
        //TODO
    }
}
