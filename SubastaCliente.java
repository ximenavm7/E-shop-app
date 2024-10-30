import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SubastaCliente extends UnicastRemoteObject implements ClienteNotificacion {
    private SubastaVista vista;
    private SubastaControlador controlador;
    private SubastaServicio servicio;

    public SubastaCliente() throws RemoteException {
        try {
            // Conectar al servidor RMI
            servicio = (SubastaServicio) Naming.lookup("rmi://localhost/SubastaServicio");

            // Inicializar la vista y el controlador
            vista = new SubastaVista();
            controlador = new SubastaControlador(vista, servicio);

            // Asignar el controlador a la vista
            vista.asignarActionListener(controlador);
            vista.asignarListSelectionListener(controlador);

            // Registrar el cliente para recibir notificaciones
            servicio.registrarCliente(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Implementación del método de callback
    @Override
    public void actualizarPrecio(String producto, float nuevoPrecio) throws RemoteException {
        System.out.println("Actualizacion recibida para el producto: " + producto + " con nuevo precio: " + nuevoPrecio);
        vista.desplegarPrecio(String.valueOf(nuevoPrecio));
    }

    public static void main(String[] args) {
        try {
            new SubastaCliente();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
