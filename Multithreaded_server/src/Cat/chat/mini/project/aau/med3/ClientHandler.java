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


    // Constructor
    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos, int whichClient) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
        this.whichClient = whichClient;

    }

    @Override
    public void run() {
        
        String received;
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
                // prints the message to the console that the server received the message
                System.out.println("Message received from client: " + whichClient + " '" + received + "'");
                
                // creates a string with a passed username of a client and the received message
                toreturn = (username + ": " + received);
                // sends that message to all the clients except the current client
                Server.broadcast(tosend, whichClient);

                // if exit is received then the thread closes the socket and ends the while loop
                if (received == "Exit") {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close(); // closes the socket
                    break; // breaks the loop
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // to close resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

