import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create components
        JTextField inputField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        JTextArea displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false); // Make the display area non-editable

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Text:"));
        panel.add(inputField);
        panel.add(submitButton);
        panel.add(new JLabel("Display Text:"));
        panel.add(new JScrollPane(displayArea));

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get text from the input field
                String inputText = inputField.getText();

                // Display text in the text area
                displayArea.append(inputText + "\n");

                // Clear the input field
                inputField.setText("");
            }
        });

        // Add the panel to the frame
        frame.add(panel);

        // Set the frame visibility to true
        frame.setVisible(true);
    }
}
