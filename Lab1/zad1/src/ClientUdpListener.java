import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class ClientUdpListener extends Thread {

    private DatagramSocket socket;

    public ClientUdpListener(DatagramSocket socket){
        this.socket = socket;

    }

    public void run(){

        try {
            while(true) {
                byte[] receiveBuffer = new byte[1024];
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("U:"+msg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
