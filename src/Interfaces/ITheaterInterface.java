import java.rmi.RemoteException;

public interface TheaterInterface extends java.rmi.Remote { 
    public String list() 
        throws RemoteException; 
 
    public String book(String type, int number, String name) 
        throws RemoteException; 
 
    public String guests() 
        throws RemoteException; 
 
    public String cancel(String type, int number, String name) 
        throws RemoteException; 
    
    String addTheaterListener(String seatType, TheaterListener addTheaterListener) throws RemoteException;

    String removeTheaterListener(String seatType, TheaterListener removeTheaterListener) throws RemoteException;

} 
