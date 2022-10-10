package Cat.chat.mini.project.aau.med3;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

class readMessage extends Thread{
    final DataInputStream dis;
    final JTextArea chatText;

    public readMessage(DataInputStream dis, JTextArea chatText){
        this.dis = dis;
        this.chatText = chatText;
    }

    @Override
    public void run() {
        while(true){
            String received;

            try {
                received = dis.readUTF(); // Getting the message from the server
                chatText.append(received  + "\n"); // Appending it to the TextArea
                System.out.println(received); // Printing out to the client
            }
            catch (IOException e) {
                client.isConnected = false;
                e.printStackTrace();
                break;
            }
        }
    }
}