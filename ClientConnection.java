import java.io.*;
import java.net.*;

public class ClientConnection {
    public static String host;
    public static final int PORT = 6060;
    public static Socket link = null; 

    public ClientConnection(String newHost) {
        host = newHost;
        try {
            link = new Socket(host, PORT);
        } 
        catch (IOException e) {
            System.out.println("Accept failed: Port 1234");
        }
    } 
}
