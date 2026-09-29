package conexion;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * Clase encargada de la conexión Java - MySQL.
 * Aplica el patrón de diseño SINGLETON: solo existe UNA conexión activa
 * en toda la aplicación. Si ya está abierta, se reutiliza; si no existe
 * o se cerró, recién ahí se crea una nueva.
 *
 * Esta es la clase que Luis (capa DAO) usa para guardar,
 * buscar y actualizar pacientes en la base de datos.
 *
 * Autor: Mathew
 */
public class Conexion {
 
    // Datos de conexión a la base de datos
    private static final String URL =
        "jdbc:mysql://localhost:3306/bd_centrosaludganimedes?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = ""; // cambiar por la de tu MySQL local
 
    // Única instancia de la conexión que existirá en toda la aplicación
    private static Connection instancia = null;
 
    // Constructor privado: nadie desde fuera puede crear "new Conexion()"
    private Conexion() {
    }
 
    /**
     * Devuelve la única conexión activa (patrón Singleton).
     * Si no existe todavía o se cerró, crea una nueva; si ya está
     * abierta, entrega la misma que ya existía.
     */
    public static Connection obtenerConexion() throws SQLException {
        if (instancia == null || instancia.isClosed()) {
            instancia = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        }
        return instancia;
    }
 
    /**
     * Cierra la conexión activa (llamar al finalizar la aplicación).
     */
    public static void cerrarConexion() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
                instancia = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}