import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

//Zaimplementować dwukierunkową komunikację przez UDP Java-Java
//        – Klient wysyła wiadomość i odczytuje
//        odpowiedź
//        – Serwer otrzymuje wiadomość i wysyła
//        odpowiedź
//        – Należy pobrać adres nadawcy z
//        otrzymanego datagramu


public class Ex1Server {
    public static void main(String args[])
    {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];

            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String msg = new String(receivePacket.getData());

                System.out.println("received msg: " + msg);

                //wysylam wiadomosc
                TimeUnit.SECONDS.sleep(2);
                InetAddress address = receivePacket.getAddress();
                int sendportNumber = receivePacket.getPort();
                byte[] sendBuffer = "Pong Java Udp ex 1 ".getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, sendportNumber);
                socket.send(sendPacket);
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
