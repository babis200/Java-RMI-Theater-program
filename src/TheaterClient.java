import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.lang.Integer;

public class TheaterClient extends UnicastRemoteObject implements ITheaterListener {
    protected TheaterClient() throws RemoteException {
    }

    public static void main(String[] args) {
        String error = "Wrong number of arguments!";
        try {
            String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + ":41926/TheaterService";   //connect to service
            Remote remoteObject = Naming.lookup(url);
            TheaterInterface remoteServer = (TheaterInterface) remoteObject;

            System.out.println("Welcome to the Theater booking app");
            if (args.length <= 1) { // there must be at least 2 arguements
                printOptions(); //print usage 
            } else {
                switch (args[0]) { 
                    case "list":
                        if (args.length == 2) { 
                            System.out.print(remoteServer.list());    
                            
                        } else {
                            System.err.println(error);
                            System.exit(1);
                        }
                        break;
                    case "book":
                        if (args.length == 5) {
                            String ans = null;

                            ans = remoteServer.book(args[2], Integer.valueOf(args[3]), args[4]);    //call book 
                            System.out.println(ans);
                            if (ans.contains("available")) {    //if there are no seats available ask to be notified
                                Scanner myObj = new Scanner(System.in);
                                String ansScan = myObj.nextLine();
                                if (ansScan.equalsIgnoreCase("Y")) {    
                                    TheaterClient mTheaterListener = new TheaterClient();                                     
                                    System.out.println(remoteServer.addTheaterListener(args[2], mTheaterListener));           
                                }
                                myObj.close();
                            }
                        } else {
                            System.err.println(error);
                            System.exit(1);
                        }
                        break;
                    case "guests":
                        if (args.length == 2) {
                            System.out.print(remoteServer.guests());
                            
                        } else {
                            System.err.println(error);
                            System.exit(1);
                        }
                        break;
                    case "cancel":
                        if (args.length == 5) {
                            
                            System.out.print(remoteServer.cancel(args[2], Integer.valueOf(args[3]), args[4]));  //call cancel
                           
                        } else {
                            System.err.println(error);
                            System.exit(1);
                        }
                        break;
                    default:
                        printOptions();
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());    //cant initialize  
            e.printStackTrace();
        }
    }

    @Override
    public void AvailabilityChanged(String msg) throws RemoteException {    //just prints that there are seats
        System.out.println(msg);
        
    }

    public static void printOptions() { //usage 
        System.out.println("~Menu~\n" +
                "1. Display all available seats: TheaterClient list <hostname>\n" +
                "2. Book <type> type of <number> seats in name <name>: TheaterClientbook <hostname>  <type>  <number> <name>\n" +
                "3. List of attendance: TheaterClient guests <hostname>\n" +
                "4. Cancel <type> type of <number> seats in name <name>: cancel <hostname> <type> <number> <name>\n");
    }

}