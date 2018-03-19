import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

//Ex2 Zaimplementować komunikację przez UDP pomiędzy językami Java i Python
//        – JavaUdpServer + PythonUdpClient
//        – Należy przesłać wiadomość tekstową:
//        ‘żółta gęś’
//        (uwaga na kodowanie)


//Ex3 Zaimplementować przesył wartości liczbowej w przypadku
//      JavaUdpServer +PythonUdpClient
//        – Symulujemy komunikację z platformą o
//         innej kolejności bajtów: klient Python ma
//        wysłać następujący ciąg bajtów:
//        msg_bytes = (300).to_bytes(4, byteorder='little')
//        – Server Javy ma wypisać otrzymaną liczbę
//        oraz odesłać liczbę zwiększoną o jeden
//        Zadanie 3 wskazówki
//        – Zamiana bajty –> int –> bajty w Javie:
//        int nb = ByteBuffer.wrap(buff).getInt();
//        buff = ByteBuffer.allocate(4).putInt(nb).array();
//        – Zamiana bajty –> int w Pythonie:
//        int.from_bytes(buff, byteorder='little')

public class JavaUdpServer {

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

                //ex 2____________________________
//                String msg = new String(receivePacket.getData());
//                System.out.println("received msg: " + msg);
//                byte[] sendBuffer = "Pong Java Udp".getBytes();
                //ex 2 end___________________

                //zadanie 3 ________________________
                int i =  ByteBuffer.wrap(receivePacket.getData()).getInt();
                int msg = (i&0xff)<<24 | (i&0xff00)<<8 | (i&0xff0000)>>8 | (i>>24)&0xff;

                byte[] sendBuffer = ByteBuffer.allocate(4).putInt(msg+1).array();
                System.out.println("received msg: " + msg);
                //System.out.print(ByteBuffer.wrap(sendBuffer).getInt());
                //ex 3 end _________________

                //wysylam wiadomosc
                TimeUnit.SECONDS.sleep(2);
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
