import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;

public class SubastaVista {

    JFrame principal;
    JTextField usuario;
    JTextField producto;
    JTextField precioInicial;
    JTextField monto;
    DefaultComboBoxModel<String> productos;
    JLabel precioActual;
    JList<String> lista;
    JButton conectar;
    JButton iniciarSesion;
    JButton salir;
    JButton ponerALaVenta;
    JButton obtenerLista;
    JButton ofrecer;
    JTextArea detallesOferta;

    public SubastaVista() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        Container panel;

        principal = new JFrame("Cliente Subasta");
        panel = principal.getContentPane();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        usuario = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre del usuario"), gbc);
        gbc.gridx = 1;
        panel.add(usuario, gbc);

        conectar = new JButton("Conectar");
        iniciarSesion = new JButton("Iniciar Sesion");
        salir = new JButton("Salir");

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(conectar, gbc);
        gbc.gridx = 1;
        panel.add(iniciarSesion, gbc);
        gbc.gridx = 2;
        panel.add(salir, gbc);

        producto = new JTextField();
        precioInicial = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Producto a ofrecer"), gbc);
        gbc.gridx = 1;
        panel.add(producto, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Precio inicial"), gbc);
        gbc.gridx = 1;
        panel.add(precioInicial, gbc);

        ponerALaVenta = new JButton("Poner a la venta");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(ponerALaVenta, gbc);
        gbc.gridx = 1;
        panel.add(new JLabel()); // Espaciador

        productos = new DefaultComboBoxModel<>();
        lista = new JList<>(productos);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listaScroller = new JScrollPane(lista);
        listaScroller.setPreferredSize(new Dimension(250, 80)); // Tamaño preferido de la lista

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Tomar dos columnas
        panel.add(listaScroller, gbc);

        obtenerLista = new JButton("Obtener lista");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1; // Restablecer a una columna
        panel.add(obtenerLista, gbc);

        precioActual = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Precio actual"), gbc);
        gbc.gridx = 1;
        panel.add(precioActual, gbc);

        monto = new JTextField();
        ofrecer = new JButton("Ofrecer");
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(ofrecer, gbc);
        gbc.gridx = 1;
        panel.add(monto, gbc);

        principal.setSize(400, 400);
        principal.setVisible(true);
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        detallesOferta = new JTextArea(5, 20);
        detallesOferta.setEditable(false);  // Solo lectura
        panel.add(new JLabel("Detalles de la ultima oferta"));
        panel.add(detallesOferta);
    }

    public void asignarActionListener(ActionListener controlador) {
        conectar.addActionListener(controlador);
        iniciarSesion.addActionListener(controlador);
        salir.addActionListener(controlador);
        ponerALaVenta.addActionListener(controlador);
        obtenerLista.addActionListener(controlador);
        ofrecer.addActionListener(controlador);
    }

    public void asignarListSelectionListener(ListSelectionListener controlador) {
        lista.addListSelectionListener(controlador);
    }

    public String getUsuario() {
        return usuario.getText();
    }

    public String getProducto() {
        return producto.getText();
    }

    public float getPrecioInicial() {
        float resultado = 0.0f;
        try {
            resultado = Float.parseFloat(precioInicial.getText());
        } catch (Exception e) {
            System.out.println("Hay problemas con el precio inicial");
        }
        return resultado;
    }

    public void reinicializaListaProductos() {
        productos.removeAllElements();
    }

    public void agregaProducto(String prod) {
        productos.addElement(prod);
    }

    public void desplegarPrecio(String precio) {
        precioActual.setText(precio);
    }

    public float getMontoOfrecido() {
        float resultado = 0.0f;
        try {
            resultado = Float.parseFloat(monto.getText());
        } catch (Exception e) {
            System.out.println("Hay problemas con el monto ofrecido");
        }
        return resultado;
    }

    public String getProductoSeleccionado() {
        return lista.getSelectedValue();
    }

    // Método para desplegar los detalles de la oferta
    public void desplegarDetallesOferta(String detalles) {
        detallesOferta.setText(detalles);
    }
}