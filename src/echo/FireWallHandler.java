package echo;

import java.net.Socket;
import java.util.ArrayList;

public class FireWallHandler extends ProxyHandler {
    // instance field
    // a list of access "code" we are blocking for security reasons
    // maybe we don't want clients to be able to access certain properties unless we directly give permission
    // our life is static because it belongs to the Class and not an object of the class
    // this is brought over from safetable, so static may not be neccessary since clients
    // at this moment, are not allowed to add banned requests nor unban requests
    private static SafeList blockedRequestsList = new SafeList();
    private String request;

    // constructor:

    // no args:
    public FireWallHandler(){
        super();
        initBlockedRequests();
    }

    // pass in Socket obj
    public FireWallHandler(Socket s){
        super(s);
        initBlockedRequests();
    }

    // getters and setters

    synchronized SafeList getBlockedRequestsList() {
        return blockedRequestsList;
    }

    public synchronized String getRequest() {
        return request;
    }
    // initialize the current request of this FireWallHandler here
    public synchronized void setRequest(String request) {
        this.request = request;
    }

    // other methods:

    // pass in the initial blockedRequests
    // initially when we are using a MathHandler, we'll block the
    // subtract and divide operations
    public void initBlockedRequests(){
        getBlockedRequestsList().add("subtract");
        getBlockedRequestsList().add("divide");
    }

    /*
    Pass in the msg from the client here and check to see if the request is okayed by Firewall
     */
    public void validateRequest(String msg){
        String userInputArray[] = msg.split(" ");
        setRequest(userInputArray[0]); // some kind of operation: "add" , "multiply", etc.
    }

    // override response method

    @Override
    protected String response(String msg) throws Exception {
        // let's first get the operation or request from the user:
        validateRequest(msg);
        if(getBlockedRequestsList().contains(getRequest())){
            // forward to ProxyHandler's peer
            return getRequest().toString() +" is not a valid request, try again";
        }
        else
         return super.response(msg);
    }
}
