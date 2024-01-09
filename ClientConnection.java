import java.io.*;
import java.net.*;

public class ClientConnection {
    public String host;
    public final int PORT = 6060;
    public Socket link = null; 

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
