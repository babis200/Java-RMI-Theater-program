import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.InetAddress;

public class TheaterServer {
    public static void main(String argv[]) throws RemoteException {
       try {
           TheaterImpl lServer = new TheaterImpl();
           Registry reg = LocateRegistry.createRegistry(41926); //start rmiregistry at port 41926
           String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + ":41926/TheaterService";  
           Naming.rebind(url, lServer);  //create rmi server 
           System.out.println("Theater Server is ready for operations.");
      } catch (Exception e) {
        System.out.println("Trouble: " + e);
      }
    }
 }
 