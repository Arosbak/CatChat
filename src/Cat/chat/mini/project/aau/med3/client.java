package Cat.chat.mini.project.aau.med3;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class client
{
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static Socket sock;
    static boolean isConnected;

    public static void main(String[] args)
    {
        displayStatus clientStatus = new displayStatus("Client Status", true);
        isConnected = true;
        int time = 0;

        try
        {
            ImageIcon profileIcon = new ImageIcon("resources/cat3.png");
            String username = "Shocked boi";

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("192.168.50.26");

            // Establish the connection with server port 5056
            sock = new Socket(ip, 59298);

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
