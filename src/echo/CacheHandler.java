package echo;

import java.net.Socket;

public class CacheHandler extends ProxyHandler {
    protected Correspondent peer;
    protected static SafeTable safeTable = new SafeTable();

    // constructors
    public CacheHandler(){
        super();
    }

    public CacheHandler(Socket socket){
        super(socket);
    }


    protected String response(String msg) throws Exception {
        // get the msg stored in the safetable
        String result = safeTable.get(msg);
        if(result != null){
            System.out.println("returning " + msg);
        }
        // if we reach the else statement, we haven't seen this request before and have to add it to our table
        else {
            System.out.println("adding " + msg + " to table");
            result = super.response(msg);
            safeTable.put(msg,result);
        }
        return result;
    }


    // methods
}
