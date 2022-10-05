package Cat.chat.mini.project.aau.med3;

import javax.swing.*;
import java.io.*;
import java.net.*;

// Client class
public class client3
{
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static Socket s;
    private static GUI gui;
    private static String error;
    private static int time;
    static boolean isConnected;


    public static void main(String[] args) throws IOException
    {

        displayStatus clientStatus = new displayStatus("Client Status", true);
        isConnected = true;
        time = 0;
        try
        {
            ImageIcon profile = new ImageIcon("resources/cat3.png");
            String username = "Shocked boi";

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("192.168.0.205");

            // establish the connection with server port 5056
            s = new Socket(ip, 59298);

            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler

            gui = new GUI(dis, dos, username, profile);
            gui.start();
            dos.writeUTF(username); // for passing a username of the current client to the server


           // for checking if client is still connected to server
            while (true) {
                System.out.println("client has been running for: " + time + " seconds");

                if (!isConnected) {
                    System.out.print("disconnected");
                    throw new Exception("Server disconnected");
                }

                Thread.sleep(1000);
                time ++;
            }

        }

        catch(Exception e){  // if connection is failed client is closed

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            e.printStackTrace();
            error = sw.toString();

                try {
                    dis.close();
                    dos.close();
                    s.close();
                }
                catch (Exception m) {
                    m.printStackTrace();
                }

            clientStatus.run();
            clientStatus.updateLabel("Could not connect to server! \n Error: \n" + error);
        }
    }
}
