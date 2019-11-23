package Cat.chat.mini.project.aau.med3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


class GUI extends Thread {
    // declaring and initializing needed objects
    String appName = "Cat Chat";
    JFrame newFrame = new JFrame(appName);
    JButton sendMessage;
    JTextField messageTyped;
    JTextArea chatText;
    Icon sendIcon = new ImageIcon("send2.jpg");
    ImageIcon logo = new ImageIcon("icon.png");
    JLabel chat;
    JPanel bottomPanel;
    JPanel mainPanel;

    final DataInputStream dis; // datainputputstream for retrieving messages and displaying them
    final DataOutputStream dos; // dataoutputstream for sending the messages
    final String username;   // username for Jlabel that is passed as an argument
    final ImageIcon profile; // profile icon that is passed as an argument

    public GUI(DataInputStream dis, DataOutputStream dos, String username, ImageIcon profile){
        this.dis = dis;
        this.dos = dos;
        this.username = username;
        this.profile = profile;
    }

            @Override
            public void run() { // run method for displaying gui and receiving and sending messages from gui
                
                try {
                    // making the GUi able to run and display all components properly
                    UIManager.setLookAndFeel(UIManager 
                            .getSystemLookAndFeelClassName());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                try {
                    
                    displayMessages(); // method that displays the GUI
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // making a thread for reading messages, so that a client could simoultaniously receive and send messages
                readMessage reader = new readMessage(dis, chatText);
                reader.start(); // starting the thread
            }




    public void displayMessages() throws IOException {
        newFrame.setIconImage(logo.getImage()); // giving the frame a new icon

        mainPanel = new JPanel(); // initializing a JPanel
        mainPanel.setLayout(new BorderLayout()); // settign the layout of JPanel

        bottomPanel = new JPanel(); // initializing another JPanel
        bottomPanel.setBackground(Color.PINK); // setting its background to pink
        bottomPanel.setLayout(new GridBagLayout()); // setting its layout to grid layout

        messageTyped = new JTextField("Type a message..."); // giving the text field and initial message to display
        messageTyped.setForeground(Color.GRAY); // setting the colour of the initial message to grey
        messageTyped.setFont(new Font("Monospaced", Font.PLAIN, 30)); // setting the font
        messageTyped.addMouseListener(new MouseAdapter(){   // adding an action listener so that the "type a message..." would dissapear
                                          @Override         // when clicked
                                          public void mouseClicked(MouseEvent e){
                                              messageTyped.setText("");
                                              messageTyped.setForeground(Color.BLACK); // the typed message colour is set to black once
                                          }                                            // once the user presses on the text field
                                      }
        );


        messageTyped.requestFocusInWindow();

        sendMessage = new JButton(sendIcon); // a button for sending the message is initialized
        sendMessage.setBackground(Color.WHITE); // setting the colour of its background to white
        sendMessage.setForeground(Color.BLUE); // settign the colour of text to blue
        sendMessage.addActionListener(new sendMessageButtonListener()); // assigning a message listener

        chatText = new JTextArea(); // initializing text area for displaying the chat messages
        chatText.setEditable(false); // making it not editable
        chatText.setFont(new Font("Monospaced", Font.PLAIN, 30)); // setting the font
        chatText.setBackground(Color.WHITE); // setting the background colour to white
        chatText.setLineWrap(true); // makes sure that the text is not pilled next to each other

        
        chat =  new JLabel("", profile , SwingConstants.CENTER); // a label for displaying the current user's username and profile picture
        chat.setFont(new Font("Monospaced", Font.PLAIN, 50)); // setting the font
        chat.setText(username); // setting the use's name to the passed string parameter from the client
        chat.setVerticalAlignment(SwingConstants.CENTER); // setting the position of the component


        mainPanel.add(new JScrollPane(chatText), BorderLayout.CENTER); // adding the scroll bar to the text area

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        bottomPanel.add(messageTyped, left);
        bottomPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, bottomPanel);
        mainPanel.add(BorderLayout.PAGE_START, chat);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(600, 600);
        newFrame.setVisible(true);

    }



    class sendMessageButtonListener implements ActionListener {  // creating an action listener for the message button
        public void actionPerformed(ActionEvent event) {
            if (messageTyped.getText().length() < 1) {  // if the text field is empty the message is not sent
            } else {
                try {
                    dos.writeUTF(messageTyped.getText()); // retreives the message from the text field and sends it to the server
                    chatText.append(username + ": " + messageTyped.getText() + "\n"); // adds the same message by appending it to the
                } catch (IOException e) {                                             // text area for the current gui, so that the
                    e.printStackTrace();                                              // client can see its own sent message
                }
                messageTyped.setText(""); // sets the text field to empty after sending the message
            }
            messageTyped.requestFocusInWindow();
        }

    }
}
