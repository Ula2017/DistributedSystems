import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.List;

public class ServerUdp extends Thread{

    private DatagramSocket socket;
    private List<ClientData> clientData;

    public ServerUdp(DatagramSocket s, List<ClientData> cl){
        this.socket = s;
        this.clientData = cl;

    }

    private boolean checkIfClientInsert(List<ClientData> list, DatagramPacket receivePacket){

        for(ClientData clientData : list){
            if(clientData.getPortUdp() == receivePacket.getPort() && receivePacket.getAddress() == clientData.getAddress()){
                return true;
            }
        }
        return false;
    }

    private void addIfNotExists(List<ClientData> list, String name, DatagramPacket receivePacket){
        if(!checkIfClientInsert(list, receivePacket)){
            ClientData c = new ClientData(receivePacket.getPort(),name, this.socket, receivePacket.getAddress());
            list.add(c);
        }
    }

    public void run() {
        try{
            byte[] receiveBuffer = new byte[1024];
            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String msg = new String(receivePacket.getData());
                System.out.println("U:"+msg);

                String[] parts = msg.split(">");
                String name = parts[0];

                addIfNotExists(clientData, name, receivePacket);

                byte[] sendBuffer = msg.getBytes();

                for(ClientData c: clientData){
                    if(c.getPortUdp() != receivePacket.getPort()) {
                        System.out.println(c.getName());
                        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, c.getAddress(), c.getPortUdp());
                        c.getSocket().send(sendPacket);
                    }
                }
            }

        }  catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket!=null){
                socket.close();
            }
        }
    }
}
