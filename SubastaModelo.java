import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

public class SubastaModelo extends UnicastRemoteObject implements SubastaServicio {
    
    Hashtable<String, String> usuarios;
    Hashtable<String, InformacionProducto> productos;
    Hashtable<String, InformacionOferta> ofertas;
    List<ClienteNotificacion> clientesRegistrados; // Lista de clientes registrados

    public SubastaModelo() throws RemoteException {
        usuarios = new Hashtable<>();
        productos = new Hashtable<>();
        ofertas = new Hashtable<>();
        clientesRegistrados = new ArrayList<>();
    }

    @Override
    public boolean registraUsuario(String nombre) throws RemoteException {
        if (!usuarios.containsKey(nombre)) {
            System.out.println("Agregando un nuevo usuario: " + nombre);
            usuarios.put(nombre, nombre);
            return true; // Usuario registrado con éxito
        }
        System.out.println("El usuario ya esta registrado: " + nombre);
        return false; // Usuario ya existe
    }

    @Override
    public boolean estaUsuarioRegistrado(String nombre) throws RemoteException {
        return usuarios.containsKey(nombre);
    }


    @Override
    public boolean agregaProductoALaVenta(String vendedor, String producto, float precioInicial) throws RemoteException {
        if (!productos.containsKey(producto)) {
            System.out.println("Agregando un nuevo producto: " + producto);
            productos.put(producto, new InformacionProducto(vendedor, producto, precioInicial));
            return true;
        }
        return false;
    }

    @Override
    public boolean agregaOferta(String comprador, String producto, float monto) throws RemoteException {
        if (productos.containsKey(producto)) {
            InformacionProducto infoProd = productos.get(producto);
            if (infoProd.actualizaPrecio(monto)) {
                InformacionOferta oferta = new InformacionOferta(comprador, producto, monto);
                ofertas.put(producto + comprador, oferta);
                notificarClientes(producto, monto, comprador, oferta.getFechaHoraOferta()); // Notificación con detalles completos
                System.out.println("Nueva oferta realizada por " + comprador + " en el producto " + producto +
                                " con monto: " + monto + " en fecha y hora: " + oferta.getFechaHoraOferta());
                return true;
            }
        }
        return false;
    }

    @Override
    public Vector<InformacionProducto> obtieneCatalogo() throws RemoteException {
        return new Vector<>(productos.values());
    }

    // Registrar un cliente para recibir notificaciones
    @Override
    public void registrarCliente(ClienteNotificacion cliente) throws RemoteException {
        clientesRegistrados.add(cliente);
    }

    // Eliminar un cliente de la lista de notificaciones
    @Override
    public void eliminarCliente(ClienteNotificacion cliente) throws RemoteException {
        clientesRegistrados.remove(cliente);
    }

    // Notificar a todos los clientes sobre el cambio de precio de un producto
    private void notificarClientes(String producto, float nuevoPrecio, String comprador, LocalDateTime fechaHoraOferta) {
        for (ClienteNotificacion cliente : clientesRegistrados) {
            try {
                cliente.actualizarOferta(producto, nuevoPrecio, comprador, fechaHoraOferta);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
