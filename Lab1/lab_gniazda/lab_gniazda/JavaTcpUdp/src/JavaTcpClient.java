import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class JavaTcpClient {

    public static void main(String[] args) throws IOException {

        System.out.println("JAVA TCP CLIENT");
        String hostName = "localhost";
        int portNumber = 9008;
        Socket socket = null;

        try {
            // create socket
            socket = new Socket(hostName, portNumber);

            // in & out streams
            System.out.println(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(out.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send msg, read response
            out.println("Ping Java Tcp");
            System.out.println(out.toString());
            String response = in.readLine();
            System.out.println("received response: " + response);
            TimeUnit.SECONDS.sleep(10);
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Ping Java Tcp22");

            System.out.println("Dupa");
            response = in.readLine();
            System.out.println("received response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                socket.close();
            }
        }
    }

}
