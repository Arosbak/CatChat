package CatChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * <h1>clientHandler</h1>
 * A class that runs asynchronously and handles all the communication between a single client and the main server.
 *
 * @author Arijus Grotuzas
 */
class clientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket socket;
    final displayStatus serverStatus;
    private int clientID;

    protected clientHandler(Socket socket, DataInputStream dis, DataOutputStream dos, int clientID, displayStatus serverStatus) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
        this.clientID = clientID;
        this.serverStatus = serverStatus;
    }

    /**
     * Main method for running the thread.
     */
    @Override
    public void run() {
        String receivedMessage;
        String messageToSend;
        String username = "";

        try {
            // Sends initial message to the client that greets the client to the server
            dos.writeUTF("Server: Hello and welcome to the chat!");

            // Retrieves the username from the client
            username = dis.readUTF();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {

                // Thread waits to receive a message from the client
                receivedMessage = dis.readUTF();

                // Prints the message to the console that the server received
                System.out.println("Message received from client: " + clientID + ", '" + receivedMessage + "'");

                // Creates a string with a passed username of a client and the received message
                messageToSend = (username + ": " + receivedMessage);

                // Sends that message to all the clients except the current client
                server.broadcastMessage(messageToSend, clientID);

            }
            catch (IOException e) {
                System.out.println("Connection to client: " + clientID + " has been lost...");

                try {
                    this.dis.close();
                    this.dos.close();
                    this.socket.close();
                    server.clients.remove(clientID);
                    server.numOfClients--;
                    server.reassignHandlerIDs();
                    serverStatus.updateLabel("Number of connected clients: " + server.clients.size() +
                            "\n Local IP Address: " + server.localIP + "\n Listening on port: " + server.portNumber);
                    break;
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    break;
                }
            }
        }
    }

    /**
     * Allows to reassign the private ID value of the client handler class.
     *
     * @param newID a new ID for the client handler (client)
     */
    public void reassignID(int newID){
        this.clientID = newID;
    }
}
