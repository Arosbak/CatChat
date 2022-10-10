package Cat.chat.mini.project.aau.med3;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * <h1>Client</h1>
 * A simple chat client-side application that connects to a server application over a local network.
 * The client app allows to send messages and receive messages from other clients connected to the same server application.
 * The user needs to specify their username and the IP address of the server before running the app.
 * <p>
 * Usage: ./client.jar <username> <server IP address> or ./client.jar
 *
 * @author  Arijus Grotuzas
 */
public class client
{
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static Socket sock;
    static boolean isConnected;
    final static int portNumber = 59298;

    public static void main(String[] args)
    {
        displayStatus clientStatus = new displayStatus("Client Status", true);
        isConnected = true;
        int time = 0;

        String username;
        String serverIP;
        String profileIconPath = "resources/cat3.png";

        if(args.length > 0){
            username = args[0];
            serverIP = args[1];
        }
        else{
            username = "Shocked boi";
            serverIP = "192.168.50.26";
        }

        try
        {
            ImageIcon profileIcon = new ImageIcon(profileIconPath);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName(serverIP);

            // Establish the connection with server port 5056
            sock = new Socket(ip, portNumber);

            // Obtaining input and out streams
            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());

            gui graphicalInterface = new gui(dis, dos, username, profileIcon);
            graphicalInterface.start();

            // For passing a username of the current client to the server
            dos.writeUTF(username);

            // For checking if client is still connected to server
            while (true) {
                System.out.println("Client has been running for: " + time + " seconds");

                if (!isConnected) {
                    System.out.print("Disconnected...");
                    break;
                }
                Thread.sleep(1000);
                time++;
            }

        }

        // If connection is failed client is closed
        catch(Exception e){

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            e.printStackTrace();
            String error = sw.toString();

                try {
                    dis.close();
                    dos.close();
                    sock.close();
                }
                catch (Exception m) {
                    m.printStackTrace();
                }

            clientStatus.run();
            clientStatus.updateLabel("Could not connect to server! \n Error: \n" + error);
        }
    }
}
