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
    private boolean usuarioRegistrado = false;  // Variable para verificar si el usuario está registrado

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
                if (usuario == null || usuario.isEmpty()) {
                    System.out.println("Debe ingresar un nombre de usuario para conectarse.");
                    return;
                }
            
                // Verificar si el usuario ya está registrado en el sistema antes de intentar registrarlo
                if (usuarioRegistrado) {
                    System.out.println("Usuario ya esta conectado como: " + usuario);
                } else {
                    System.out.println("Intentando registrar al usuario: " + usuario);
                    usuarioRegistrado = servicio.registraUsuario(usuario);
                    if (!usuarioRegistrado) {
                        System.out.println("El usuario ya esta registrado en el sistema.");
                    } else {
                        System.out.println("Usuario registrado con exito: " + usuario);
                    }
                }
            } else if (evento.getActionCommand().equals("Poner a la venta")) {
                if (!usuarioRegistrado) {
                    System.out.println("Debe registrarse antes de agregar productos.");
                    return;
                }
                usuario = vista.getUsuario();
                producto = vista.getProducto();
                monto = vista.getPrecioInicial();

                // Validar nombre del producto y precio
                if (producto == null || producto.isEmpty()) {
                    System.out.println("Debe ingresar un nombre para el producto.");
                    return;
                }
                if (monto <= 0) {
                    System.out.println("Debe ingresar un precio valido para el producto.");
                    return;
                }

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
                if (!usuarioRegistrado) {
                    System.out.println("Debe registrarse antes de hacer una oferta.");
                    return;
                }
                
                producto = vista.getProductoSeleccionado();
                monto = vista.getMontoOfrecido();
                usuario = vista.getUsuario();

                // Validar producto seleccionado y monto de oferta
                if (producto == null || producto.isEmpty()) {
                    System.out.println("Debe seleccionar un producto para ofertar.");
                    return;
                }
                if (monto <= 0) {
                    System.out.println("Debe ingresar un monto valido para la oferta.");
                    return;
                }

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