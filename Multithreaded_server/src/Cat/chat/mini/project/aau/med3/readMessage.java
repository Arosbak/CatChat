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
                received = dis.readUTF();
                chatText.append(received  + "\n");
                System.out.println(received);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}