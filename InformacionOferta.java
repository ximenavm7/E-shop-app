
public class InformacionOferta implements java.io.Serializable {

    String comprador;
    String producto;
    float monto;

    public InformacionOferta( String c, String p, float m ) {

        comprador = c;
        producto = p;
        monto = m;
   }
}
