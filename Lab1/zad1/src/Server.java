import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException {

        System.out.println("SERVER");
        int portNumber = 12345;
        ServerSocket serverSocket = null;
        DatagramSocket socket = null;

        try {

            serverSocket = new ServerSocket(portNumber);
            socket = new DatagramSocket(portNumber);
            List<ClientData> clientsList= new ArrayList<>();

            new ServerUdp(socket, clientsList).start();
            MulticastSocket multicastSocket = new MulticastSocket(4446);
            new MulticastListener(multicastSocket, -1).start();

            while (true) {

                Socket clientSocket = serverSocket.accept();

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                new ServerTcp(clientsList, out, in, clientSocket).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null || socket != null) {
                serverSocket.close();
                socket.close();
            }
        }
    }

}