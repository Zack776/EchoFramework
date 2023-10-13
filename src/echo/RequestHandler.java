
package echo;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler extends Correspondent implements Runnable {
    protected boolean active; // response can set to false to terminate thread
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }
    public RequestHandler() {
        super();
        active = true;
    }
    // override in a subclass:
    // this method "echos" the message back to the client
    protected String response(String msg) throws Exception {
        return "echo: " + msg;
    }
    // any housekeeping can be done by an override of this:
    protected void shutDown() {
        if (Server.DEBUG) System.out.println("handler shutting down");
        // close socket?
        close();
    }

    @Override
    protected void close() {
        super.close();
    }

    public void run() {
      while (sock.isConnected()){
          try{
              String ClientMessage = receive();
              if(ClientMessage.equals("quit")){
                  shutDown();
                  //close();
                  break;
              }
              send(response(ClientMessage));
          } catch (Exception e) {
              e.printStackTrace();
              shutDown();
              //close();
              break;
          }
      }
    }
}