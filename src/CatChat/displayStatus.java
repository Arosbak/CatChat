package CatChat;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>displayStatus</h1>
 * A class that displays a message or a given status in a form of a graphical user interface.
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

    /**
     * Main method for calling the GUI.
     */
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
     * Converts a String to HTML format, by replacing all \n with br element.
     *
     * @param orig A String containing \n
     * @return An HTML formatted String
     */
    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    /**
     * Changes the String to display.
     *
     * @param newMessage A new String to display in the GUI
     */
    public void updateLabel(String newMessage) {
        statusLabel.setText(convertToMultiline(newMessage));
        if (pack) {
            Frame.pack();
        }
    }
}
