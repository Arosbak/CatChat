package CatChat;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * <h1>readMessage</h1>
 * A class that runs asynchronously and reads the messages sent from the server.
 *
 * @author Arijus Grotuzas
 */
class readMessage extends Thread{
    final DataInputStream dis;
    final JTextArea chatText;

    public readMessage(DataInputStream dis, JTextArea chatText){
        this.dis = dis;
        this.chatText = chatText;
    }

    /**
     * Main method for running the thread.
     */
    @Override
    public void run() {
        while(true){
            String received;

            try {
                received = dis.readUTF();
                chatText.append(received  + "\n");
            }
            catch (IOException e) {
                client.isConnected = false;
                System.out.print("\n");
                e.printStackTrace();
                break;
            }
        }
    }
}