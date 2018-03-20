package pl.edu.agh.dsrg.sr.protos;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;

import java.net.DatagramPacket;

/**
 * Created by lab on 3/20/2018.
 */
public class SimpleChat {
    JChannel channel;

    private void start() throws Exception{
        System.setProperty("java.net.preferIPv4Stack" ,"true");
        channel = new JChannel(false);

        ProtocolStack stack=new ProtocolStack();
        channel.setProtocolStack(stack);
        stack.addProtocol(new UDP())
                .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new SEQUENCER()) // do synchronizacji wiadomosci
                .addProtocol(new FLUSH());
        stack.init();
        channel.connect("operation");
        for (int i = 0; i < 2000; i++) {
            BankOperationProtos.BankOperation operation = BankOperationProtos.BankOperation.newBuilder()
                    .setValue(Math.random() / 100 + 1)
                    .setType(BankOperationProtos.BankOperation.OperationType.MULTIPLY)
                    .build();
            byte[] bytes = operation.toByteArray();
            Message msg = new Message(null, null,bytes);

            channel.send(msg);
            Thread.sleep((long) (Math.random() * 0));
        }
        Thread.sleep(5000);
        channel.close();
    }

    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }
}
