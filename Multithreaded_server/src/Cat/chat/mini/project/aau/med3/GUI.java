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
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    displayMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                readMessage reader = new readMessage(dis, chatText);
                reader.start();
            }




    public void displayMessages() throws IOException {
        newFrame.setIconImage(logo.getImage());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.setLayout(new GridBagLayout());

        messageTyped = new JTextField("Type a message...");
        messageTyped.setForeground(Color.GRAY);
        messageTyped.setFont(new Font("Monospaced", Font.PLAIN, 30));
        messageTyped.addMouseListener(new MouseAdapter(){
                                          @Override
                                          public void mouseClicked(MouseEvent e){
                                              messageTyped.setText("");
                                              messageTyped.setForeground(Color.BLACK);
                                          }
                                      }
        );


        messageTyped.requestFocusInWindow();

        sendMessage = new JButton(sendIcon);
        sendMessage.setBackground(Color.WHITE);
        sendMessage.setForeground(Color.BLUE);
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatText = new JTextArea();
        chatText.setEditable(false);
        chatText.setFont(new Font("Monospaced", Font.PLAIN, 30));
        chatText.setBackground(Color.WHITE);
        chatText.setLineWrap(true);

        //client 1
        chat =  new JLabel("", profile , SwingConstants.CENTER);
        chat.setFont(new Font("Monospaced", Font.PLAIN, 50));
        chat.setText(username);
        chat.setVerticalAlignment(SwingConstants.CENTER);


        mainPanel.add(new JScrollPane(chatText), BorderLayout.CENTER);

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



    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageTyped.getText().length() < 1) {
            } else {
                try {
                    dos.writeUTF(messageTyped.getText());
                    chatText.append(username + ": " + messageTyped.getText() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                messageTyped.setText("");
            }
            messageTyped.requestFocusInWindow();
        }

    }
}
