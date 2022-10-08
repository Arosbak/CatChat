package Cat.chat.mini.project.aau.med3;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;

// class for receiving messages from the server
class readMessage extends Thread{
    final DataInputStream dis;
    final JTextArea chatText;

    public readMessage(DataInputStream dis, JTextArea chatText){
        this.dis = dis; // setting passed input stream as the instance
        this.chatText = chatText; // setting passed JtextArea as the instance
    }

    @Override
    public void run() {
        while(true){
            String received; // string for received message

            try {
                received = dis.readUTF(); // getting the message from the server
                chatText.append(received  + "\n"); // appending it to the TextArea
                System.out.println(received); // printing out to the client


            } catch (IOException e) {
                client.isConnected = false;
                e.printStackTrace();
                break;
            }
        }
    }
}