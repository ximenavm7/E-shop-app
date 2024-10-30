import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class SubastaControlador implements ActionListener, ListSelectionListener {
    
    SubastaVista vista;
    SubastaServicio servicio;  // Interfaz remota
    Hashtable<String, String> listaConPrecios;

    public SubastaControlador(SubastaVista v, SubastaServicio s) {
        vista = v;
        servicio = s;
    }

    public void actionPerformed(ActionEvent evento) {
        String usuario;
        String producto;
        float monto;

        System.out.println("<<" + evento.getActionCommand() + ">>");

        try {
            if (evento.getActionCommand().equals("Salir")) {
                System.exit(1);
            } else if (evento.getActionCommand().equals("Conectar")) {
                usuario = vista.getUsuario();
                System.out.println("Registrarse como usuario: " + usuario);
                servicio.registraUsuario(usuario);
            } else if (evento.getActionCommand().equals("Poner a la venta")) {
                usuario = vista.getUsuario();
                producto = vista.getProducto();
                monto = vista.getPrecioInicial();
                System.out.println("Ofertando producto: " + producto);
                servicio.agregaProductoALaVenta(usuario, producto, monto);
            } else if (evento.getActionCommand().equals("Obtener lista")) {
                Vector<InformacionProducto> lista = servicio.obtieneCatalogo();
                listaConPrecios = new Hashtable<>();
                vista.reinicializaListaProductos();
                for (InformacionProducto info : lista) {
                    listaConPrecios.put(info.getNombreProducto(), String.valueOf(info.getPrecioActual()));
                    vista.agregaProducto(info.getNombreProducto());
                }
            } else if (evento.getActionCommand().equals("Ofrecer")) {
                producto = vista.getProductoSeleccionado();
                monto = vista.getMontoOfrecido();
                usuario = vista.getUsuario();
                servicio.agregaOferta(usuario, producto, monto);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            JList<?> lista = (JList<?>) e.getSource();
            String item = (String) lista.getSelectedValue();
            if (item != null) {
                System.out.println(item);
                String precio = listaConPrecios.get(item);
                vista.desplegarPrecio(precio);
            }
        }
    }
}