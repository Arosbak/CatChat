package testing.blah.blah;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


public class MainGUI {

    String appName = "Cat Chat";
    MainGUI mainGUI;
    JFrame newFrame = new JFrame(appName);
    JButton sendMessage;
    JButton	person1;
    JButton person2;
    JButton	person3;
    JTextField messageBox;
    JTextArea chatBox;
    JTextArea peopleChat;
    String username = "You boi";
    Icon icon = new ImageIcon("send.png");
    Icon profile1 = new ImageIcon("cat1.png");
    Icon profile2 = new ImageIcon("cat2.png");
    Icon profile3 = new ImageIcon("cat3.png");
    ImageIcon img = new ImageIcon("icon.png");
    JSplitPane splitPane; 
    JLabel chat =  new JLabel ("HAVE A CHAT WITH A CAT", SwingConstants.CENTER);
    JLabel name;
    JLabel messages = new JLabel ("    Chats ", SwingConstants.CENTER);
    JPanel southPanel;
    JPanel leftPanel;
    JPanel mainPanel;
   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainGUI mainGUI = new MainGUI();
                mainGUI.displayMessages();
            }
        });
    }

    public void displayMessages() {
    	newFrame.setIconImage(img.getImage());
    	
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        southPanel = new JPanel();
        southPanel.setBackground(Color.PINK);
        southPanel.setLayout(new GridBagLayout());
        
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.GRAY);
        BoxLayout boxlayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
        leftPanel.setLayout(boxlayout);
        leftPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        
        messageBox = new JTextField("Type a message...");
        messageBox.setForeground(Color.GRAY);
        messageBox.setFont(new Font("Monospaced", Font.PLAIN, 30));
        messageBox.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                messageBox.setText("");
                messageBox.setForeground(Color.BLACK);
            	}
            }
        );
            
        
        messageBox.requestFocusInWindow();

        sendMessage = new JButton(icon);
        sendMessage.setBackground(Color.WHITE);
        sendMessage.setForeground(Color.BLUE);
        sendMessage.addActionListener(new sendMessageButtonListener());
        
        person1 = new JButton("Sneezed boi", profile1);
        person1.setFont(new Font("Monospaced", Font.PLAIN, 30));
        person1.setBackground(Color.WHITE);
        person1.setForeground(Color.BLACK);
        person2 = new JButton("Sadness boi", profile2);
        person2.setFont(new Font("Monospaced", Font.PLAIN, 30));
        person2.setBackground(Color.WHITE);
        person2.setForeground(Color.BLACK);
        person3 = new JButton("Shocked boi", profile3);
        person3.setFont(new Font("Monospaced", Font.PLAIN, 30));
        person3.setBackground(Color.WHITE);
        person3.setForeground(Color.BLACK);
        //person1.addMouseListener(new person1ButtonListener());
        
        chat.setFont(new Font("Monospaced", Font.PLAIN, 50));
        chat.setVerticalAlignment(SwingConstants.CENTER);
        chat.setForeground(Color.BLACK);
        
        messages.setFont(new Font("Monospaced", Font.PLAIN, 40));
        messages.setVerticalAlignment(SwingConstants.CENTER);
        messages.setForeground(Color.PINK);
        
        peopleChat = new JTextArea();
        peopleChat.setEditable(false);
        peopleChat.setFont(new Font("Monospaced", Font.PLAIN, 30));
        peopleChat.setLineWrap(true);
        
        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Monospaced", Font.PLAIN, 30));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

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

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);
        
        leftPanel.setBackground(Color.white);
        leftPanel.add(messages);
        leftPanel.add(person1);
        leftPanel.add(person2);
        leftPanel.add(person3);
        
        mainPanel.add(BorderLayout.SOUTH, southPanel);
        mainPanel.add(BorderLayout.EAST, leftPanel);
        mainPanel.add(BorderLayout.PAGE_START, chat);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(1100, 900);
        newFrame.setVisible(true);
    }
   
    
    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
            } else {
                chatBox.append(username + ": " + messageBox.getText() + "\n");
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode()==KeyEvent.VK_ENTER){
            	if (messageBox.getText().length() < 1) {
                } else {
                    chatBox.append(username + ": " + messageBox.getText() + "\n");
                    messageBox.setText("");
                }
            messageBox.requestFocusInWindow();

    }
        }
    
    class person1ButtonListener{
    	public void actionPerformed(ActionEvent event) {
    	}
   }
    }}