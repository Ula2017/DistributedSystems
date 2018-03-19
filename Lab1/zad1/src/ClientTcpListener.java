import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTcpListener extends Thread {

    private Socket socket;

    public ClientTcpListener(Socket socket){
        this.socket=socket;

    }

    public void run() {

        try {
            while(true){

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String response = in.readLine();
                    if(response!= null)
                        System.out.println("T:"+response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(socket!=null)
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

        }
    }

}
