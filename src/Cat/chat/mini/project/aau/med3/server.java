package Cat.chat.mini.project.aau.med3;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.List;


public class server
{
    static List<clientHandler> clients = new ArrayList<>();
    static int numOfClients = 0;
    static String localIP = server.getLocalIP();
    static int portNumber = 59298;

    // Method for sending the message to all clients except one
    static void broadcast(String msg, int notSendTo) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            if(i != notSendTo) {
                clientHandler handler = clients.get(i);
                handler.dos.writeUTF(msg);
            }
        }
    }

    // Method for reassigning client IDs
    static void reassignHandlerIDs(){
        for (int i = 0; i < clients.size(); i++){
            clientHandler handler = clients.get(i);
            handler.reassignID(i);
        }
    }

    // Method for retrieving the Sever's local IP address
    public static String getLocalIP(){
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
            return datagramSocket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(portNumber);

        // Initialize a gui that displays server's info
        displayStatus serverStatus = new displayStatus("Number of connected clients: " + clients.size(), false);
        serverStatus.run();
        System.out.println("Running on IP Address: " + localIP);

        // Running infinite loop for getting client request
        while (true)
        {
            Socket sock = null;
            try
            {
                serverStatus.updateLabel("Number of connected clients: " + clients.size() +
                        "\n Local IP Address: " + localIP + "\n Listening on port: " + portNumber);

                // Socket object to receive incoming client requests
                sock = ss.accept();

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
            catch (Exception e){
                assert sock != null;
                sock.close();
                e.printStackTrace();
                break;
            }
        }
    }
}
