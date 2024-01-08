import java.io.*;
import java.net.*;

public class ServerConnection {
    public static ServerSocket servSock;
    public static final int PORT = 6060;
    public static Socket link = null; 


    public ServerConnection() {
        // create ServerSocket object with port number
        try {
           servSock = new ServerSocket(PORT);
        } 
        catch (IOException e) {
           System.out.println("Unable to reach to the port!");
           System.exit(-1);
        }
  
        // create Socket object for socket connection with the client program.       
        try {
           link = servSock.accept();
        } 
        catch (IOException e) {
           System.out.println("Accept failed: Port 1234");
        }
     }
}
