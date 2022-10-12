package CatChat;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.List;

/**
 * <h1>Server</h1>
 * A simple chat server-side application that instantiates a new thread for handling communication with each client.
 * The server listens on port 59298 and creates a communication between multiple clients over a local network.
 *
 * @author  Arijus Grotuzas
 */
public class server
{
    static List<clientHandler> clients = new ArrayList<>();
    static int numOfClients = 0;
    final static String localIP = server.getLocalIP();
    protected static int portNumber = 59298;

    /**
     * A method for broadcasting a message to all the connected clients.
     *
     * @param msg A message to broadcast to all the clients
     * @param notSendTo Client ID to which the message should not be sent e.g. the sender of the message
     * @throws IOException A specific client in the list has disconnected, thus data output stream (dos) has been closed
     */
    static void broadcastMessage(String msg, int notSendTo) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            if(i != notSendTo) {
                clientHandler handler = clients.get(i);
                handler.dos.writeUTF(msg);
            }
        }
    }

    /**
     * A method for reassigning the IDs of all the clients in case one of the clients had disconnected.
     */
    static void reassignHandlerIDs(){
        for (int i = 0; i < clients.size(); i++){
            clientHandler handler = clients.get(i);
            handler.reassignID(i);
        }
    }

    /**
     * A method for retrieving the local IP address of the current machine.
     *
     * @return Local IP address of the machine the application is running on
     * @throws RuntimeException The method cannot make a UDP connection to port 12345
     */
    public static String getLocalIP() throws RuntimeException {
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
            return datagramSocket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, NumberFormatException
    {
        // Initialize a gui that displays server's info
        displayStatus serverStatus = new displayStatus("Server status" , false);
        serverStatus.run();

        if (args.length > 0){
            try {
                portNumber = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid port number...");
            }
        }

        // Open server socket for communication
        ServerSocket serverSock = new ServerSocket(portNumber);
        System.out.println("Running on IP Address: " + localIP + ", Listening on port: " + portNumber);

        // Loop for getting client request
        while (true)
        {
            Socket sock = null;
            try
            {
                serverStatus.updateLabel("Number of connected clients: " + clients.size() +
                        "\n Local IP Address: " + localIP + "\n Listening on port: " + portNumber);

                // Socket object to receive incoming client requests
                sock = serverSock.accept();

                // Prints out if the client is connected
                System.out.println("A new client is connected : " + sock);

                // Obtaining input and out streams
                DataInputStream dis = new DataInputStream(sock.getInputStream());
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

                // Prints out if the thread has been assigned to the client
                System.out.println("Assigning new thread for this client");

                // Create a new thread object for handling the client
                clientHandler handler =  new clientHandler(sock, dis, dos, numOfClients, serverStatus);

                // Invoking the start() method
                handler.start();

                // Adding the client to the list
                clients.add(handler);
                numOfClients++;

                // Prints out the number of connected clients
                System.out.println("Number of connected clients: " + clients.size());
            }
            catch (IOException e){
                assert sock != null;
                sock.close();
                e.printStackTrace();
                break;
            }
        }
    }
}
