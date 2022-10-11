package CatChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>getUserInfo</h1>
 *
 * @author Arijus Grotuzas
 */
public class getUserInfo {

    private final JLabel infoLabel = new JLabel("Please enter your username and a Server IP address", SwingConstants.CENTER);
    private final JFrame Frame = new JFrame("Cat chat");
    private final String title;

    public getUserInfo(String title){
        this.title = title;
    }

    /**
     * Main method for calling the GUI.
     */
    public void run(){
        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add the label to the panel
        infoLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(infoLabel, BorderLayout.CENTER);

        // Add a button for sending the info
        JButton sendInfo = new JButton("Apply");
        sendInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Frame.dispose();
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.add(sendInfo);
        panel.add(BorderLayout.SOUTH, bottomPanel);

        // Add panel to frame and make frame visible
        Frame.setTitle(title);
        Frame.add(panel);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(250, 100);
        Frame.setVisible(true);
    }
}
