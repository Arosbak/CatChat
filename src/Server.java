// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class


public class Server
{
    static List<ClientHandler> clients = new ArrayList<ClientHandler>();

    static void broadcast(String msg) throws IOException {
        for (int i = clients.size(); --i >= 0; ) {
            ClientHandler st =  clients.get(i);
            st.dos.writeUTF(msg);
        }
    }
/*
    static String read() throws IOException {
        for (int i = clients.size(); --i >= 0; ) {
            ClientHandler st =  clients.get(i);
            String message = st.dis.readUTF();
            if(message != null){
                return message;
            }
        }
        return "poop";
    }
*/
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

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
                ClientHandler t =  new ClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

                clients.add(t);

                System.out.println("number of connected clients: " + clients.size());

                if(clients.size() > 1) {
                    Scanner in = new Scanner(System.in);
                    String g = in.nextLine();
                    broadcast(g);
                }
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;

    }

    @Override
    public void run() {
        String received;
        String toreturn;
        /*
        try {
            dos.writeUTF("Hello and welcome to server, type your message here");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        while (true) {
            try {
                dos.writeUTF(" ");
                // Ask user what he wants
                // receive the answer from client
                received = dis.readUTF();
                System.out.println("Message received: " + "'" + received + "'");

                if (received.equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                toreturn = ("I have received your message: " + received);

                dos.writeUTF(toreturn);

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

