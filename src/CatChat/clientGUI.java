package CatChat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * <h1>clientGUI</h1>
 * A class that provides a graphical user interface for the client app.
 *
 * @author Arijus Grotuzas
 */
class clientGUI extends Thread {

    private final Icon sendIcon = new ImageIcon("resources/send.png");
    private final ImageIcon logo = new ImageIcon("resources/icon.png");

    private final String appName = "Cat Chat";
    private final JFrame newFrame = new JFrame(appName);
    private JTextField messageTyped;
    private JTextArea chatText;

    final DataInputStream dis;
    final DataOutputStream dos;
    final String username;
    final ImageIcon profile;

    public clientGUI(DataInputStream dis, DataOutputStream dos, String username, ImageIcon profile){
        this.dis = dis;
        this.dos = dos;
        this.username = username;
        this.profile = profile;
    }

    /**
     * Main method for calling the GUI.
     */
    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            displayMessages();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Making a thread for reading messages
        readMessage reader = new readMessage(dis, chatText);
        reader.start();

        while (true) {
            if (!client.isConnected) {
                newFrame.dispose();
                break;
            }

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                newFrame.dispose();
                break;
            }
        }

    }

    public void displayMessages() throws IOException {
        // Panel settings
        newFrame.setIconImage(logo.getImage());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.setLayout(new GridBagLayout());

        // Text field for entering messages
        messageTyped = new JTextField("Type a message...");
        messageTyped.setForeground(Color.GRAY);
        messageTyped.setFont(new Font("Monospaced", Font.PLAIN, 30));
        messageTyped.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                messageTyped.setText("");
                messageTyped.setForeground(Color.BLACK);}
        });
        messageTyped.requestFocusInWindow();

        // Send message button
        JButton sendMessage = new JButton(sendIcon);
        sendMessage.setBackground(Color.WHITE);
        sendMessage.setForeground(Color.BLUE);
        sendMessage.addActionListener(new sendMessageButtonListener());

        // Text area for viewing messages
        chatText = new JTextArea();
        chatText.setEditable(false);
        chatText.setFont(new Font("Monospaced", Font.PLAIN, 30));
        chatText.setBackground(Color.WHITE);
        chatText.setLineWrap(true);

        // User info area
        JLabel chat = new JLabel("", profile, SwingConstants.CENTER);
        chat.setFont(new Font("Monospaced", Font.PLAIN, 50));
        chat.setText(username);
        chat.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(new JScrollPane(chatText), BorderLayout.CENTER);

        // Setting up the layout of the panel and objects in it
        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        // Spacing of elements
        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        // Adding elements to main panel and lower panel
        bottomPanel.add(messageTyped, left);
        bottomPanel.add(sendMessage, right);
        mainPanel.add(BorderLayout.SOUTH, bottomPanel);
        mainPanel.add(BorderLayout.PAGE_START, chat);

        // Adding the panel to the frame
        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(600, 600);
        newFrame.setVisible(true);
    }

    /**
     * An event listener for the sendMessage button.
     */
    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // Send a message if it's not empty
            if (messageTyped.getText().length() > 1) {
                try {
                    dos.writeUTF(messageTyped.getText());
                    chatText.append(username + ": " + messageTyped.getText() + "\n");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                // Clears the text field after sending a message
                messageTyped.setText("");
            }
            messageTyped.requestFocusInWindow();
        }
    }
}
