import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

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
    /*@Override
    public void actualizarPrecio(String producto, float nuevoPrecio) throws RemoteException {
        System.out.println("Actualizacion recibida para el producto: " + producto + " con nuevo precio: " + nuevoPrecio);
        vista.desplegarPrecio(String.valueOf(nuevoPrecio));
    }*/

    @Override
    public void actualizarOferta(String producto, float nuevoPrecio, String comprador, LocalDateTime fechaHoraOferta) throws RemoteException {
        String detallesOferta = String.format("Producto: %s\nNuevo Precio: %.2f\nComprador: %s\nFecha y Hora: %s",
                                            producto, nuevoPrecio, comprador, fechaHoraOferta);
        System.out.println("Actualización recibida: " + detallesOferta);  // Muestra en consola

        // Llama a la vista para mostrar los detalles de la oferta
        vista.desplegarDetallesOferta(detallesOferta);
    }


    public static void main(String[] args) {
        try {
            new SubastaCliente();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
