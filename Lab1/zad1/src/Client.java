import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your name:");
        String name = br.readLine();

        System.out.println("JAVA CLIENT "+name);
        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket = null;
        DatagramSocket socketUdp = null;
        InetAddress group;
        byte[] sendBuffer;
        InetAddress address;
        String asciiArt = "______________________________________s$\n" +
                "_____________________________________$s\n" +
                "____________________________________s$s\n" +
                "________________________________ss$$$s\n" +
                "__________________________s$$$$$$$$s\n" +
                "__________$$$$$$$______s$$$$$$$$$s\n" +
                "__________$$$$$$$$___$$$$$$$$$$$$\n" +
                "__________$$$$$$$$$_$$$$$$$$$$$$$_s$\n" +
                "$________$$$$$$$$$$$$$$$$$$$sss$$$$$\n" +
                "$$_______$$$$$$$$$$$$$$$$$$$$$$s\n" +
                "s$$s_______s$$$$$$$$$$$$$$$$$$$$$$_____s\n" +
                "_$$$$___sss$$$$$$$$$$$$$$$$_s$$$_s$____$\n" +
                "__s$$$$$$$$$$$$$$$$$$$$$$$$__$_______$$$\n" +
                "______ssss$$$$$$$$$$$$$$$$$$s___s$$$$$$\n" +
                "_________$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
                "__________$$$$$$$$$$$$$$$$$$$$$\n" +
                "____________$$$$$__$$$$$$$$$$$$s\n" +
                "_____________$$$$$____$$$$$$$$$$$$$\n" +
                "_____________$$$$$$___$$$$$$__$$s\n" +
                "____________$$$$$$$$______$$$$__s$s\n" +
                "____________$$$$$$$$$$as_____s$$___$\n" +
                "___________$$$$$$$$$$$$s______$$s\n" +
                "_________s$$$s$$$$$$_$sss\n" +
                "_________s$$_$$$$$$_______________s$s_\n" +
                "_________s$___s$$$$s___________s$$$$$$$$\n" +
                "_________s_____$$$$$___$$$$$$$$$$$s\n" +
                "________________$$$$_s$$$$$$s\n" +
                "_________________$$$$$$$s\n" +
                "__________________$$$$\n" +
                "___________________$$$$\n" +
                "____________________$$$$\n" +
                "_____________________$$$\n" +
                "______________________$$\n" +
                "______________________$$s\n" +
                "_______________________$$\n" +
                "_______________________$$$\n" +
                "________________________$$\n" +
                "________________________$$";

        try {
            socket = new Socket(hostName, portNumber);
            socketUdp = new DatagramSocket();
            MulticastSocket s = new MulticastSocket(4446);
            group = InetAddress.getByName("230.0.0.0");
            MulticastListener ml = new MulticastListener(s, socketUdp.getLocalPort());
            ml.start();
            new ClientTcpListener(socket).start();
            new ClientUdpListener(socketUdp).start();


            while(true) {
                //System.out.print("Enter your message: \n> ");
                String msg = br.readLine();
                System.out.println("UDP enter 'U'\n" +
                        "Multicast enter 'M' \n" +
                        "UDP Art enter 'AU\n"+
                        " otherwise message will be sent with TCP protocol: ");
                String type = br.readLine();
                switch(type){
                    case "U":
                        address = InetAddress.getByName("localhost");
                        sendBuffer = (name+">"+msg).getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        socketUdp.send(sendPacket);
                        break;
                    case "M":
                        byte[] buf = (name+">"+msg).getBytes();
                        DatagramPacket sendPacketMulti = new DatagramPacket(buf, buf.length,
                                group, 4446);
                        socketUdp.send(sendPacketMulti);
                        break;
                    case "AU":
                        address = InetAddress.getByName("localhost");
                        sendBuffer = (name+">"+asciiArt).getBytes();
                        DatagramPacket sendPacketArt = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                        socketUdp.send(sendPacketArt);
                        break;

                    default:
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(name +">" +msg);
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((socket != null) || (socketUdp != null)){
                socket.close();
                socketUdp.close();
            }

        }

    }
}
