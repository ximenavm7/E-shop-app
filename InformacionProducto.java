import java.time.LocalDateTime;

public class InformacionProducto implements java.io.Serializable {

    String vendedor;
    String producto;
    float precioInicial;
    float precioActual;
    LocalDateTime fechaHoraPublicacion;

    public InformacionProducto( String v, String p, float pi ) {
        vendedor = v;
        producto = p;
        precioInicial = pi;
        precioActual = pi;
        fechaHoraPublicacion = LocalDateTime.now();
    }

    public boolean actualizaPrecio( float monto ) {

        if( monto > precioActual ) {
            precioActual = monto;
            return true;
        } else
            return false;
    }

    public String getNombreProducto() {
        return producto;
    }

    public float getPrecioActual() {
        return precioActual;
    }

    public LocalDateTime getFechaHoraPublicacion() {
        return fechaHoraPublicacion;
    }
}
