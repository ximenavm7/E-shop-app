import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class SubastaServidor {
    public static void main(String[] args) {
        try {
            // Inicia el registro en el puerto 1099
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry iniciado en el puerto 1099...");

            // Crea una instancia de SubastaModelo y la registra
            SubastaServicio servicio = new SubastaModelo();
            Naming.rebind("rmi://localhost/SubastaServicio", servicio);

            System.out.println("Servidor de subastas registrado y listo.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
