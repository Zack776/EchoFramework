package echo;

import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;

    public ProxyHandler(Socket s) { super(s); }
    public ProxyHandler() { super(); }

    /*
    pass in the peer host IP address and the peer port number
     */
    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    protected String response(String msg) throws Exception {
        // forward msg to peer
        peer.send(msg);
        msg = peer.receive();
        // return peer's response
        return msg;
    }

    protected void shutDown() {
        super.shutDown();
        peer.send("quit"); // kill peer or it becomes a zombie!
    }
}
