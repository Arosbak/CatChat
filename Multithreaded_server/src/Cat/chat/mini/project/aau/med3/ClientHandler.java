package Cat.chat.mini.project.aau.med3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    int whichClient;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, int whichClient) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.whichClient = whichClient;

    }

    @Override
    public void run() {
        String received;
        String toreturn;
        String username = null;
        try {
            dos.writeUTF("Hello and welcome to the chat!");
            username = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                // Ask user what he wants
                // receive the answer from client
                received = dis.readUTF();
                System.out.println("Message received from client: " + whichClient + " '" + received + "'");

                toreturn = (username + ": " + received);
                Server.broadcast(toreturn, whichClient);


                if (received == "Exit") {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

