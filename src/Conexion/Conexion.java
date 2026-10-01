package conexion;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 * Clase encargada de la conexión Java - MySQL.
 * Esta es la clase que Luis (capa DAO) va a usar para guardar,
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
 
    /**
     * Abre y devuelve una nueva conexión a la base de datos.
     * Use "throws SQLException" para que quien llame a este método
     * decida cómo manejar el error (esto cuenta como manejo de excepciones
     * para el punto de Implementación del documento).
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
