package Cat.chat.mini.project.aau.med3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;
    final int whichClient;
    final displayStatus serverStatus;


    // Constructor
    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos, int whichClient, displayStatus serverStatus) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
        this.whichClient = whichClient;
        this.serverStatus = serverStatus;
    }

    @Override
    public void run() {
        Boolean condition = false;
        String received = "";
        String tosend;
        String username = null;

        try {

            // sends initial message to the client that greets the client to the server
            dos.writeUTF("Hello and welcome to the chat!");
            // retrieves the username from the client
            username = dis.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true) {
            try {

                // thread waits to receive a message from the client
                received = dis.readUTF();

                System.out.print(received);

                if (received == "") {
                    condition = true;
                }

                // prints the message to the console that the server received the message
                System.out.println("Message received from client: " + whichClient + received );

                // creates a string with a passed username of a client and the received message
                tosend = (username + ": " + received);
                // sends that message to all the clients except the current client
                Server.broadcast(tosend, whichClient);


                System.out.print(condition);


                // if exit is received then the thread closes the socket and ends the while loop
               /* if (condition == true) {
                    System.out.println("Closing this connection.");
                    this.socket.close(); // closes the socket
                    break; // breaks the loop
                }*/

            } catch (IOException e) {
                System.out.println("connection to client:" + whichClient + " has been lost");
                //e.printStackTrace();
                try {
                    this.dis.close();
                    this.dos.close();
                    this.socket.close();
                    Server.whichClient --;
                    Server.clients.remove(whichClient);
                    serverStatus.updateLabel("number of connected clients: " + Server.clients.size());
                    break;

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        /*
        try {
            // to close resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}
