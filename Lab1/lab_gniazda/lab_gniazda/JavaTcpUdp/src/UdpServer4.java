import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

//Ex4 Zaimplementować serwer (Java lub
//        Python) który rozpoznaje czy otrzymał
//        wiadomość od klienta Java czy od klienta
//        Python i wysyła im różne odpowiedzi (np.
//        ‘Pong Java’, ‘Pong Python’)
public class UdpServer4 {

    public static void main(String args[])
    {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;


        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer = null;

            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String msg = new String(receivePacket.getData());
                System.out.println("received msg: " + msg);
                String[] parts = msg.split(" ");

                switch (parts[0]) {
                    case "Java":
                        sendBuffer = "Pong Java Udp".getBytes();
                        break;
                    case "Python":
                        sendBuffer = "Pong Python Udp".getBytes();
                        break;
                    default:
                        sendBuffer = "Pong UDP".getBytes();
                        break;
                }

                InetAddress address = receivePacket.getAddress();
                int sendportNumber = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, sendportNumber);
                socket.send(sendPacket);
                System.out.println("Send!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
