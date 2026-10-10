package pruebas;
 
import fabrica.PacienteFactory;
import modelo.Paciente;
import seguridad.SeguridadClave;
 
/**
 * Pruebas automatizadas del patrón Factory (PacienteFactory).
 * No necesitan base de datos: solo verifican la creación del objeto.
 * Se ejecutan con "Run File" y muestran PASO o FALLO en la consola.
 */
public class PruebasFactory {
 
    private static int total = 0;
    private static int pasaron = 0;
 
    private static void verificar(String nombre, boolean condicion) {
        total++;
        if (condicion) {
            pasaron++;
            System.out.println("[PASO]  " + nombre);
        } else {
            System.out.println("[FALLO] " + nombre);
        }
    }
 
    public static void main(String[] args) {
        System.out.println("=== PRUEBAS DEL PATRON FACTORY ===");
 
        Paciente p = PacienteFactory.crear("  Ana ", "Torres", " 70000001 ", "1990-05-12", "atorres", "ClaveSegura1");
 
        verificar("F1. La fabrica limpia los espacios sobrantes de los datos",
                "Ana".equals(p.getNombre()) && "70000001".equals(p.getDocumento()));
 
        verificar("F2. La contrasena no queda en texto plano",
                !"ClaveSegura1".equals(p.getContrasenaHash()) && p.getContrasenaHash().startsWith("pbkdf2$"));
 
        verificar("F3. El hash creado por la fabrica se verifica con la clave correcta y rechaza la incorrecta",
                SeguridadClave.verificar("ClaveSegura1", p.getContrasenaHash())
                && !SeguridadClave.verificar("otra", p.getContrasenaHash()));
 
        boolean lanzoError = false;
        try {
            PacienteFactory.crear("", "Torres", "70000002", "1990-05-12", "usuario", "clave");
        } catch (IllegalArgumentException e) {
            lanzoError = true;
        }
        verificar("F4. La fabrica rechaza un dato obligatorio vacio", lanzoError);
 
        System.out.println("=== RESULTADO: " + pasaron + " de " + total + " pruebas pasaron ===");
    }
}