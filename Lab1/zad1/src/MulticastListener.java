import java.net.*;
import java.net.DatagramPacket;

public class MulticastListener extends Thread{

    private MulticastSocket socket = null;
    private int port;

    public MulticastListener(MulticastSocket multicastListener, int port){
        this.socket = multicastListener;
        this.port = port;
    }

    public void run(){

        try {
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            //socket.setLoopbackMode(true); work but not on one computer
            byte[] recBuffer = new byte[1000];

            while(true) {

                DatagramPacket receivePacket = new DatagramPacket(recBuffer, recBuffer.length);
                socket.receive(receivePacket);
                String[] parts = receivePacket.getSocketAddress().toString().split(":");
                int senderport = Integer.parseInt(parts[1]);

                if(senderport != this.port) {
                    String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("M:" + msg);
                }
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
