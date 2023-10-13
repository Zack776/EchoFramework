package echo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ProxyServer extends Server {

    String peerHost;
    int peerPort;

    public ProxyServer(int myPort, String service, int peerPort, String peerHost) {
        super(myPort,service);
        this.peerHost = peerHost;
        this.peerPort = peerPort;
    }

    @Override
    public void listen() {
        try{
            while(!serverSocket.isClosed()){
                // accept a connection:
                Socket socket = serverSocket.accept();
                System.out.println("new client is connected");
                // make a handler using the socket we created
                RequestHandler requestHandler = super.makeHandler(socket);
                ((ProxyHandler)requestHandler).initPeer(peerHost, peerPort);
                // start handler in its own thread:
                Thread thread = new Thread(requestHandler);
                thread.start();
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ioe){
            closeServerSocket();
        }
    }

    // making handler
   // public RequestHandler makeHandler(Socket socket) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
     //   return super.makeHandler(socket);
   // }

    public static void main(String[] args) {
        //int port = 5555;
        //int peerPort = 6666;
        int port = 6666;
        int peerPort = 5555;
        String peerHost = "localhost";
        //String service = "echo.ProxyHandler";
        //String service = "echo.CacheHandler";
        //String service = "echo.SecurityHandler";
        String service = "echo.FireWallHandler";

        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            peerPort = Integer.parseInt(args[1]);
        }
        if (3 <= args.length) {
            port = Integer.parseInt(args[2]);
        }
        if (4 <= args.length) {
            peerHost = args[3];
        }
        Server server = new ProxyServer(port, service, peerPort, peerHost);
        server.listen();
    }
}



