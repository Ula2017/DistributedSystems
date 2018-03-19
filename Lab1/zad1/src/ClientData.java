import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ClientData {

    private int portUdp = -1;
    private String name;
    private DatagramSocket socket;
    private InetAddress address;
    private PrintWriter printWriter = null;
    private Socket socketTCP = null;

    public ClientData(int port, String name, DatagramSocket ds, InetAddress addresss){
        this.portUdp=port;
        this.name = name;
        this.socket = ds;
        this.address = addresss;
    }

    public ClientData(String name, PrintWriter printWriter, Socket socket){
        this.name = name;
        this.printWriter = printWriter;
        this.socketTCP = socket;

    }

    public int getPortUdp() {
        return portUdp;
    }

    public InetAddress getAddress() {
        return address;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPortUdp(int portUdp){
        this.portUdp=portUdp;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public Socket getSocketTCP(){
        return this.socketTCP;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
}
