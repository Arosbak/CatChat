package Cat.chat.mini.project.aau.med3;

import Cat.chat.mini.project.aau.med3.ClientHandler;

import java.io.*;
import java.util.*;
import java.net.*;



public class Server
{
    static List<ClientHandler> clients = new ArrayList<ClientHandler>();

    static void broadcast(String msg, int whichClient) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            if(i != whichClient) {
                ClientHandler st = clients.get(i);
                st.dos.writeUTF(msg);
            }
        }
    }

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

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                ClientHandler t =  new ClientHandler(s, dis, dos, whichClient);

                // Invoking the start() method
                t.start();

                clients.add(t);
                whichClient++;

                System.out.println("number of connected clients: " + clients.size());
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}
