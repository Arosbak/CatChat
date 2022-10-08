package Cat.chat.mini.project.aau.med3;

import javax.swing.*;
import java.awt.*;

public class displayStatus {
    private JFrame Frame = new JFrame("Status");
    private JLabel statusLabel;
    private JPanel Panel;
    private String name;
    private Boolean pack;

    public displayStatus(String name, Boolean pack) {
        this.name = name;
        this.pack = pack;
    }

    public void run() {

        Frame.setTitle(name);
        Panel = new JPanel(); // initializing a JPanel
        Panel.setLayout(new BorderLayout()); // setting the layout of JPanel

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setVerticalAlignment(JLabel.CENTER);

        Panel.add(statusLabel, BorderLayout.CENTER);

        Frame.add(Panel);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the thread when exit button is pressed
        Frame.setSize(250, 100); // setting the size of the gui
        Frame.setVisible(true); // making it visible
    }

    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public void updateLabel(String newMessage) {
        statusLabel.setText(convertToMultiline(newMessage));

        if (pack == true) {
            Frame.pack();
        }
    }
}
