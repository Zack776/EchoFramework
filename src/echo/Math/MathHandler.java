package echo.Math;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import echo.*;

/**
 * A handler that reads in requests from a client to do arithmetic operations on a set of numbers passed in
 */
public class MathHandler extends RequestHandler {
    private ArrayList<Double> numbers;

    // constructors

    public MathHandler(){
        super();
    }

    public MathHandler(Socket socket){
        super(socket);
    }

    // Arithmetic operations

    private Double addNumbers(){
        Double sum = 0.0;
        for(Double number: this.numbers){
            sum += number;
        }
        return sum;
    }

    private Double subtractNumbers(){
        Double difference = this.numbers.get(0);
        for(int i = 1; i < numbers.size(); i++){
            difference -= numbers.get(i);
        }
        return difference;
    }

    private Double multiplyNumbers(){
        Double product = 1.0;
        for(int i = 0; i < numbers.size(); i++){
            product *= numbers.get(i);
        }
        return product;
    }
    private Double divideNumbers() {
        Double dividend = this.numbers.get(0);
        for(int i = 1; i < numbers.size(); i++){
            dividend /= numbers.get(i);
        }
        return dividend;
    }

    private void clearList(){
        this.numbers.clear();
    }

    /*
    public void defineOperation(String ClientMessage){

        String[] message = ClientMessage.split(" ");
        String operation = message[0];
        ArrayList<Integer> numbers = new ArrayList<>(message.length - 1);
        for(int i = 1; i < message.length; i++){
            //System.out.println("at line 68 " + message[i]);
           numbers.add(i-1, Integer.parseInt(message[i]));
        }
        // assign this arraylist to be MathHandler's arraylist object
        setNumbers(numbers);
        setCurrentOperation(operation);
    }

     */

    public void setNumbers(ArrayList<Double> arrayList){
        this.numbers = arrayList;
    }

    //override response method


    @Override
    protected String response(String msg) {
        Double result = 0.0;
        String[] parts = msg.split(" ");
        if (parts.length < 2) {
            return "Invalid input. Please enter an operation and at least one number.";
        }
        String operation = parts[0];
        ArrayList<Double> userNums = new ArrayList<>();
        for(int i = 1; i < parts.length; i++){
            //System.out.println("at line 68 " + message[i]);
            userNums.add(i-1, Double.parseDouble(parts[i]));
        }
        // assign this arraylist to be MathHandler's arraylist object
        setNumbers(userNums);
        try {
            if (operation.equalsIgnoreCase("add")) {
                result = addNumbers();
            }
            else if (operation.equalsIgnoreCase("multiply")) {
                result = multiplyNumbers();
            }
            else if(operation.equalsIgnoreCase("subtract")){
                result = subtractNumbers();
            }
            else if(operation.equalsIgnoreCase("divide")){
                result =  divideNumbers();
            }
            else {
                return "Invalid operation. Please enter either 'add' or 'multiply'.";
            }
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter valid numbers.";
        }
        return Double.toString(result);
    }

}
