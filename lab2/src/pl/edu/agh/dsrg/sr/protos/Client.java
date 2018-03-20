package pl.edu.agh.dsrg.sr.protos;

import pl.edu.agh.dsrg.sr.protos.BankOperationProtos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by lab on 3/20/2018.
 */
public class Client {

    public static void main(String[] args) {
        final int port = 6789;
        try {
            InetAddress inetAddress = InetAddress.getByName("224.0.0.7");
            MulticastSocket multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(inetAddress);
            for (int i = 0; i < 2000; i++) {
                BankOperationProtos.BankOperation operation = BankOperationProtos.BankOperation.newBuilder()
                        .setValue(Math.random() / 100 + 1)
                        .setType(BankOperationProtos.BankOperation.OperationType.ADD)
                        .build();
                byte[] bytes = operation.toByteArray();
                multicastSocket.send(new DatagramPacket(bytes, bytes.length, inetAddress, port));
                Thread.sleep((long) (Math.random() * 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
