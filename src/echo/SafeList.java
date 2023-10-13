package echo;

import java.util.ArrayList;

// a threadSafe list
// we could probably also just make this class extend Arraylist, then just override the og methods
// our methods must have "Synchronized" key term so that they are thread safe
// all our handlers are running in a thread
public class SafeList {
    private ArrayList<String> blockedRequestsList;

    public SafeList(){
        blockedRequestsList = new ArrayList<>();
    }

    public ArrayList<String> getBlockedRequestsList() {
        return blockedRequestsList;
    }

    synchronized void add(String request){
        if(!getBlockedRequestsList().contains(request)){
            // then add the new request
            getBlockedRequestsList().add(request);
        }
        else {System.out.println("This request is already blocked");}

    }


    // unban requests
    synchronized void remove(String request){
        if(getBlockedRequestsList().contains(request)){
            getBlockedRequestsList().remove(request);
        }
        else { System.out.println("This request is not access blocked");}
    }

    public synchronized boolean contains(String request) {
        return blockedRequestsList.contains(request);
    }
}
