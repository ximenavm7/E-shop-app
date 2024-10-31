import java.time.LocalDateTime;

public class InformacionOferta implements java.io.Serializable {

    String comprador;
    String producto;
    float monto;
    LocalDateTime fechaHoraOferta;

    public InformacionOferta( String c, String p, float m ) {

        comprador = c;
        producto = p;
        monto = m;
        fechaHoraOferta = LocalDateTime.now();
    }
    
    public LocalDateTime getFechaHoraOferta() {
    return fechaHoraOferta;
    }
}
