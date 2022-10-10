package Cat.chat.mini.project.aau.med3;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>displayStatus</h1>
 *
 * @author Arijus Grotuzas
 */
public class displayStatus {

    private final JFrame Frame = new JFrame("Status");
    private final String title;
    private final Boolean pack;
    private JLabel statusLabel;

    public displayStatus(String title, Boolean pack) {
        this.title = title;
        this.pack = pack;
    }

    public void run() {
        // Create the panel
        Frame.setTitle(title);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a new label for displaying info
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setVerticalAlignment(JLabel.CENTER);
        panel.add(statusLabel, BorderLayout.CENTER);

        // Add the panel to frame and display the gui
        Frame.add(panel);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(250, 100);
        Frame.setVisible(true);
    }

    /**
     *
     * @param orig f
     * @return f
     */
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    /**
     *
     * @param newMessage A new message to display in the GUI
     */
    public void updateLabel(String newMessage) {
        statusLabel.setText(convertToMultiline(newMessage));
        if (pack) {
            Frame.pack();
        }
    }
}
