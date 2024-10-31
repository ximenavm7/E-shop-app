import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface ClienteNotificacion extends Remote {
    void actualizarOferta(String producto, float nuevoPrecio, String comprador, LocalDateTime fechaHoraOferta) throws RemoteException;
}