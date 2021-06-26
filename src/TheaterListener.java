import java.rmi.Remote;
import java.rmi.RemoteException;

// This interface's method is implemented by the client application used by the server 
// application to notify the client when the availability of a certain seat type is changed.

public interface TheaterListener extends Remote
{
    void AvailabilityChanged(String msg) throws RemoteException;
}