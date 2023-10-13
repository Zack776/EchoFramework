package echo;

import java.net.Socket;

public class SecurityHandler extends ProxyHandler {
    // table that stores username and passwords of clients
    protected static SafeTable users = new SafeTable();
    protected boolean loggedIn; // is the client logged in
    private String userName; // username
    private String password; // password
    private String option; // either "new" or "login"

    // constructors

    public SecurityHandler(){
        super();
        loggedIn = false; // initially, the client hasn't logged in - they haven't entered user or password
    }

    public SecurityHandler(Socket socket){
        super(socket);
        loggedIn = false;
    }

    // setters
    public void setUserName(String name){
        this.userName = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setOption(String option){
        this.option = option;
    }

    public void validateLogin(String msg){
        // should check for exceptions here
        // msg will be some string such as "new Zack107777 mypassword123"
        // create an operation, username, and passeword from this string
        String userInputArray[] = msg.split(" ");
        setOption(userInputArray[0]); // either "new" or "login"
        setUserName(userInputArray[1]);
        setPassword(userInputArray[2]);
    }

    @Override
    protected String response(String msg) throws Exception {
        validateLogin(msg);
        if (loggedIn) {
            return super.response(msg);
        }
        if (option.equalsIgnoreCase("new")) {
            System.out.println("Creating new account...");
            if (users.containsKey(userName)) {
                return "Username already exists";
            } else {
                users.put(userName, password);
                loggedIn = true;
                return "Account created successfully";

            }
        } else if (option.equalsIgnoreCase("login")) {
            System.out.println("Logging in...");
            String storedPassword = users.get(userName);
            if (storedPassword != null && storedPassword.equals(password)) {
                loggedIn = true;
                return "Logged in successfully";
            } else {
                return "Incorrect username or password";
            }
        } else {
            return "Please log in";
        }
    }

}
