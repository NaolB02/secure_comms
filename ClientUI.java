import java.io.*;
import java.math.BigInteger;
import javax.swing.*;

import java.awt.event.*;

public class ClientUI {
    public static ClientConnection clientConnection;
    public static DHService dhService;
    public static boolean hostSet = false;
    public static BufferedReader in;
    public static PrintStream out;
    public static BigInteger key;

    public static void main(String[] args) throws IOException {

        // Create the main frame
        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // Create components
        JTextField inputField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        JTextArea displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false); // Make the display area non-editable

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Text:"));
        panel.add(inputField);
        panel.add(submitButton);
        panel.add(new JScrollPane(displayArea));

        KeyListener textFieldKeyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Trigger the button click when Enter is pressed in the text field
                    submitButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        inputField.addKeyListener(textFieldKeyListener);


        // Add the panel to the frame
        frame.add(panel);

        // Set the frame visibility to true
        frame.setVisible(true);

        displayArea.append("Enter host name or IP of server above and click submit:\n");

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check whether host is set and if not, set it
                if (!hostSet) {
                    // Get text from the input field
                    String newHost = inputField.getText();

                    // Set the host
                    clientConnection = new ClientConnection(newHost);
                    displayArea.append("Host set to " + newHost + "\n");

                    // Set the hostSet flag
                    hostSet = true;

                    // Setup input/output
                    try {
                        in = new BufferedReader(new InputStreamReader(clientConnection.link.getInputStream()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        out = new PrintStream(clientConnection.link.getOutputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    // Setup dhservice
                    dhService = new DHService();
                    dhService.generateForClient();

                    // Send a, q and ya, accept yb and generate key
                    out.println(dhService.q);
                    out.println(dhService.a);
                    out.println(dhService.ya);
                    try {
                        dhService.acceptYB(new BigInteger(in.readLine()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    dhService.generateKeyForClient();
                    key = dhService.key;

                    // Display the key
                    displayArea.append("The secret key is:\n" + key);
                    submitButton.setText("Send");
                    displayArea.append("\n\nWrite a message and click send or write 'quit123' to close the connection:\n");

                    // Clear the input field
                    inputField.setText("");
                } else {
                    // Get text from the input field
                    String message = inputField.getText();
                    if(message.equals("quit123")) {
                        out.println("quit123");
                        System.exit(0);
                    }

                    String encryptedMessage = AESService.encrypt(message, key.toString());

                    // Display text in the text area
                    displayArea.append("The encrypted message is: " + encryptedMessage + "\n");
                    out.println(encryptedMessage);
                    displayArea.append("Message Sent");
                    displayArea.append("\n\nWrite a message and click send or write quit to close the connection:\n");

                    // Clear the input field
                    inputField.setText("");
                }

            }
        });
    }
}
