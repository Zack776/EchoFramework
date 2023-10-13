package echo;

import javax.imageio.IIOException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

/**
 * A Server object connects to clients via port number and IP Address
 */
public class Server {

    protected ServerSocket serverSocket; // listens to connections from clients; has an associated port #
    protected int myPort; // port # associated with the Server
    public static boolean DEBUG = true;
    protected Class<?> handlerType; // specific kind of Handler object for this server

    /**
     * two args constructor: the specific port # which clients need to connect to
     * the handlerType which specifies the kind of RequestHandler object we will make:
     * @param port
     * @param handlerType
     */
    public Server(int port, String handlerType) {
        try {
            myPort = port;
            // instantiate the serversocket from the given port number
            serverSocket = new ServerSocket(myPort);
            // configure the type of handler we are using based on Class; could be a basic RequestHandler, mathHandler, etc.
            this.handlerType = (Class.forName(handlerType));
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    // want server to constantly be running until its serversocket object is closed
    public void listen() {
        try{
            while(!serverSocket.isClosed()){
                // accept a connection:
                Socket socket = serverSocket.accept();
                System.out.println("new client is connected");
                // make a handler using the socket we created
                RequestHandler requestHandler = makeHandler(socket);
                // start handler in its own thread:
                Thread thread = new Thread(requestHandler);
                thread.start();
            }
        }
        catch(IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException ioe){
            closeServerSocket();
        }
    }

    /**
     * makeHandler returns a RequestHandler object based on the Server's handlerType attribute
     * It has one argument, Socket, as we make Handlers from the socket created in Server's listen from connections method
     * @param socket
     * @return
     */
    public RequestHandler makeHandler(Socket socket) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // handler = a new instance of handlerType
        //    use: try { handlerType.getDeclaredConstructor().newInstance() } catch ...
        // set handler's socket to s
        // return handler
        RequestHandler requestHandler = (RequestHandler) handlerType.getDeclaredConstructor().newInstance();
        requestHandler.setSocket(socket);
        return requestHandler;
    }


    // if an error occurs, we just want to shut down server socket
    // useful so we don't have too many catch blocks bloating up our startServer method
    public void closeServerSocket(){
        try {
            if(serverSocket != null){
                serverSocket.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        int port = 5555;
        //String service = "echo.RequestHandler";
        //String service = "echo.casino.CasinoHandler";
        String service = "echo.Math.MathHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        server.listen();
    }
}