import java.io.*;
import java.math.*;

import javax.swing.*;

public class ServerUI {
    public static ServerConnection serverConnection;
    public static DHService dhService;
    public static BufferedReader in;
    public static PrintStream out;
    public static BigInteger key;

    public static void main(String[] args) throws IOException {
        // Create the main frame
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JTextArea displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false); // Make the display area non-editable

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.add(new JLabel("Messages:"));
        panel.add(new JScrollPane(displayArea));

        // Add the panel to the frame
        frame.add(panel);

        // Set the frame visibility to true
        frame.setVisible(true);

        displayArea.append("Listening on port no 6060\n");
        serverConnection = new ServerConnection();

        // Setup input/output
        in = new BufferedReader(new InputStreamReader(serverConnection.link.getInputStream()));
        out = new PrintStream(serverConnection.link.getOutputStream());

        // Setup DHservice
        dhService = new DHService();
        BigInteger q = new BigInteger(in.readLine());
        BigInteger a = new BigInteger(in.readLine());
        BigInteger ya = new BigInteger(in.readLine());
        dhService.generateForServer(q, a, ya);

        out.println(dhService.yb);
        dhService.generateKeyForServer();

        key = dhService.key;
        displayArea
                .append("The secret key with " + serverConnection.link.getInetAddress().toString() + " is: \n" + key);

        // Display text in the text area
        displayArea.append("\n\n Waiting for messages\n");
        // serverConnection.link.isClosed();

        while (true) {
                String encryptedMessage = in.readLine();
                String decryptedMessage = AESService.decrypt(encryptedMessage, key.toString());

                if(encryptedMessage.equals("quit123")) {
                    out.println("quit123");
                    System.exit(0);
                }
                // Display text in the text area
                displayArea.append("The recieved message is: " + encryptedMessage + "\n");
                displayArea.append("The decrypted message is: " + decryptedMessage);

                displayArea.append("\n\n Waiting for messages\n");
        }
    }
}
