import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TheaterImpl 
    extends 
      java.rmi.server.UnicastRemoteObject 
    implements TheaterInterface { 
    
    private List<Customer> custArrayList = new ArrayList<>();           //list of customers/audience
    private final List<TheaterListener> theaterListenerOA = new ArrayList<>();  //Listener for seats of type OA
    private final List<TheaterListener> theaterListenerOB = new ArrayList<>();  //Listener for seats of type OB
    private final List<TheaterListener> theaterListenerOC = new ArrayList<>();  //Listener for seats of type OC
    private final List<TheaterListener> theaterListenerMB = new ArrayList<>();  //Listener for seats of type MB
    private final List<TheaterListener> theaterListenerSB = new ArrayList<>();  //Listener for seats of type SB
    Theater myTheater = new Theater();  //"create" the theater

 
    
    public TheaterImpl() 
        throws java.rmi.RemoteException { 
        super(); 
    } 
    
    @Override
    public String list() throws java.rmi.RemoteException {  //returns a list of available seats per type

        String out = null;
        out = "For the Orchestra-Zone A, (code OA) there are " + myTheater.getAvail("OA") + " seats with a price of " + myTheater.getPrice("OA") + " each.\n";
        out = out + "For the Orchestra-Zone B, (code OB) there are " + myTheater.getAvail("OB") + " seats with a price of " + myTheater.getPrice("OB") + " each.\n";
        out = out + "For the Orchestra-Zone C, (code OC) there are " + myTheater.getAvail("OC") + " seats with a price of " + myTheater.getPrice("OC") + " each.\n";
        out = out + "For the Main Balcony, (code MB) there are " + myTheater.getAvail("MB") + " seats with a price of " + myTheater.getPrice("MB") + " each.\n";
        out = out + "For the Side Balconies, (code SB) there are " + myTheater.getAvail("SB") + " seats with a price of " + myTheater.getPrice("SB") + " each.\n";
        
        return out;
    } 

    @Override
    public String book(String type, int number, String name) throws java.rmi.RemoteException { //returns result of booking
        
        String res = "ERROR BOOKING";
        int bill = 0;
        if(myTheater.book(type, number) ) {             //check if the seats are available
            bill = number * myTheater.getPrice(type);   //calculate bill
            myTheater.updateAvail(type, number);        //calculate new availability
            res = "Booking succesfull, your total bill is " +bill+" Euros.";    //construct result message
            Customer myCustomer = new Customer(name, type, number);             //create the new customer
            custArrayList.add(myCustomer);                                      //add the new customer in the audience
        } else if(myTheater.getAvail(type) > 0 ){   //if there are less available seats than requested
            res = "There are not enough seats. You could book "+myTheater.getAvail(type)+" instead.";
        }else { //if there are no available seats
            res = "There are no seats available. Would you like to get notified when more become available?[Y/N]";
        }
        
        return res; 
    } 

    @Override
    public String guests() throws java.rmi.RemoteException { //returns a list of the audience
        
        String out =null;
        int audience = custArrayList.size();

        out = "There are " + audience + " people for the show.\n";
        System.out.println("There are " + audience + " people for the show.");
       
        for(Customer d : custArrayList){
            out = out + d.getName() + " has " + d.getSeatCount() + " seats in " + d.getSeatType() + ".\n"; 
            System.out.println(d.getName() + " has " + d.getSeatCount() + " seats in " + d.getSeatType() + ".\n");
        }
        return out;
    } 
    
    @Override
    public String cancel(String type, int number, String name) throws java.rmi.RemoteException {    //cancel a number or all the seats of a a customer

        boolean exist = false;
        String ans = null;
        int pos = 0;
        for(Customer d : custArrayList){    //find if there is member of the audience with that name
            if(d.getName() != null && d.getName().equalsIgnoreCase(name)) {
                exist = true;
                pos = custArrayList.indexOf(d);
            }
        }
        if(!exist) {
            ans = "There is no Customer with that name.";
            return ans;
        }

        Customer myCustomer = custArrayList.get(pos);   
        if(myTheater.cancel(myCustomer, type, number)) {    //check if he has enough seats to cancel
            myTheater.updateAvail(type, -number);           // empty the seats for the theater
            myCustomer.setSeatCount(myCustomer.getSeatCount() - number);    //calculate new seat balance for the customer   
            notifyTheaterListeners(type);                   //notify whoever is waiting
            
        }
        ans = "Customer's remaining seats: " + myCustomer.getSeatCount() + ".";
        return ans;
    }

    @Override
    public String addTheaterListener(String seatType, TheaterListener theaterListener) {    //create a listener for the specific type of seat
        switch (seatType) {
            case "OA" : theaterListenerOA.add(theaterListener);
            case "OB" : theaterListenerOB.add(theaterListener);
            case "OC" : theaterListenerOC.add(theaterListener);
            case "MB" : theaterListenerMB.add(theaterListener);
            case "SB" : theaterListenerSB.add(theaterListener);
        }
        return "Added TheaterListener!";
    }

    @Override
    public String removeTheaterListener(String seatType, TheaterListener theaterListener) { //remove the listener for the specific type of seat
        switch (seatType) {
            case "OA" : theaterListenerOA.remove(theaterListener);
            case "OB" : theaterListenerOB.remove(theaterListener);
            case "OC" : theaterListenerOC.remove(theaterListener);
            case "MB" : theaterListenerMB.remove(theaterListener);
            case "SB" : theaterListenerSB.remove(theaterListener);
        }
        return "Removed TheaterListener!";
    }
    
        
    private void notifyTheaterListeners(String seatType) {  // print message for the customer
        switch (seatType) {
            case "OA":
                for (TheaterListener tListener : theaterListenerOA)
                    try {
                        tListener.AvailabilityChanged("New Orchestra-Zone A seats available!");
                    } catch (RemoteException x) { //if the customer leaves this  exception triggers and we remove the listener
                        theaterListenerOA.remove(tListener);
                    }
                break;
            case "OB":
                for (TheaterListener tListener : theaterListenerOB)
                    try {
                        tListener.AvailabilityChanged("New Orchestra-Zone B seats available!");
                    } catch (RemoteException x) {   //if the customer leaves this  exception triggers and we remove the listener
                        theaterListenerOB.remove(tListener);
                    }
                break;
            case "OC":
                for (TheaterListener tListener : theaterListenerOC)
                    try {
                        tListener.AvailabilityChanged("New Orchestra-Zone C seats available!");
                    } catch (RemoteException x) {   //if the customer leaves this  exception triggers and we remove the listener
                        theaterListenerOC.remove(tListener);
                    }
                break;
            case "MB":
                for (TheaterListener tListener : theaterListenerMB)
                    try {
                        tListener.AvailabilityChanged("New main Balcony seats available!");
                    } catch (RemoteException x) {   //if the customer leaves this  exception triggers and we remove the listener
                        theaterListenerMB.remove(tListener);
                    }
                break;
            case "SB":
                for (TheaterListener tListener : theaterListenerSB)
                    try {
                        tListener.AvailabilityChanged("New Side Balcony seats available!");
                    } catch (RemoteException x) {   //if the customer leaves this  exception triggers and we remove the listener
                        theaterListenerSB.remove(tListener);
                    }
                break;
        }
    }   
}