import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteNotificacion extends Remote {
    void actualizarPrecio(String producto, float nuevoPrecio) throws RemoteException;
}