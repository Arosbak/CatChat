package Cat.chat.mini.project.aau.med3;

import java.io.*;
import java.util.*;
import java.net.*;



public class Server
{
    // making a list for all connected clients
    static List<ClientHandler> clients = new ArrayList<ClientHandler>();
    
    // method for sending the message to all clients except one
    static void broadcast(String msg, int whichClient) throws IOException {
        for (int i = 0; i < clients.size(); i++) { // shuffles through the list fo connected clients
            if(i != whichClient) { // if the client is not the passed number of the client
                ClientHandler st = clients.get(i);  // gets data output stream of that client
                st.dos.writeUTF(msg); // sending the passed message to that client
            }
        }
    }
    
    // server main method
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);
        int whichClient = 0;

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                
                // prints out if the client is connected
                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                
                // prints out if the thread has been assigned to the client
                System.out.println("Assigning new thread for this client");

                // create a new thread object for handeling the client
                ClientHandler t =  new ClientHandler(s, dis, dos, whichClient);

                // Invoking the start() method
                t.start();
                
                // adding the client to the list
                clients.add(t);
                whichClient++; // increaseing the number for the next client
                
                // prints out the number of connected clients
                System.out.println("number of connected clients: " + clients.size());
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}
