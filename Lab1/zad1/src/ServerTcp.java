import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerTcp extends Thread {
    private List<ClientData> clientData;
    private PrintWriter printWriter;
    private BufferedReader in;
    private Socket socket;



    public ServerTcp(List<ClientData> cl, PrintWriter out, BufferedReader dr, Socket socket){
        this.clientData = cl;
        this.printWriter = out;
        this.in = dr;
        this.socket = socket;

    }
    private boolean checkIfClientInsert(List<ClientData> list, String name){
        for(ClientData clientData : list){
            if(clientData.getSocketTCP() != null && clientData.getSocketTCP().equals(this.socket))
                return true;
        }
        return false;
    }

    private void addIfNotExists(List<ClientData> list, String name){
        if(!checkIfClientInsert(list, name)){
            ClientData c = new ClientData(name, this.printWriter, socket);
            list.add(c);

        }

    }
    public void run() {

        try {
            while(true) {
                String msg = in.readLine();
                System.out.println("T:"+ msg);

                String[] parts = msg.split(">");
                String name = parts[0];
                addIfNotExists(this.clientData, name);

                for (ClientData c : clientData) {
                    if (c.getSocketTCP()!=this.socket && c.getPrintWriter() != null) {
                        c.getPrintWriter().println(msg);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
