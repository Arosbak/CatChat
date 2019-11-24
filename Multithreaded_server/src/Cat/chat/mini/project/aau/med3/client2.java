import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class client2
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            ImageIcon profile = new ImageIcon("cat2.png");
            String username = "Sad boi";

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket s = new Socket(ip, 5056);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following thread performs the exchange of
            // information between client and client handler

            GUI gui = new GUI(dis, dos, username, profile);
            gui.start();
            dos.writeUTF(username);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
