package dao;
 
import conexion.Conexion;
import modelo.Paciente;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Capa DAO (Data Access Object) para la tabla "pacientes".
 * Aquí van las funciones para guardar, buscar y actualizar pacientes.
 *
 * Reglas que sigue esta clase:
 * - Usa Conexion.obtenerConexion() (la clase de Mathew) para conectarse.
 * - Usa PreparedStatement en vez de armar el SQL con "+" (esto evita
 *   inyección SQL, una vulnerabilidad de seguridad).
 * - Usa try-with-resources para que la conexión se cierre sola.
 * - Maneja las excepciones con try-catch en vez de dejar que el programa se caiga.
 *
 * Autor: Luis
 */
public class PacienteDAO {
 
    /**
     * Guarda (INSERT) un nuevo paciente en la base de datos.
     * @return true si se guardó correctamente, false si hubo un error.
     */
    public boolean registrarPaciente(Paciente p) {
        String sql = "INSERT INTO pacientes (nombre, apellido, documento, "
                + "fecha_nacimiento, usuario, contrasena_hash) VALUES (?, ?, ?, ?, ?, ?)";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getDocumento());
            ps.setString(4, p.getFechaNacimiento());
            ps.setString(5, p.getUsuario());
            ps.setString(6, p.getContrasenaHash());
 
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al registrar paciente: " + e.getMessage());
            return false;
        }
    }
 
    /**
     * Busca un paciente por su número de documento (DNI).
     * @return el Paciente encontrado, o null si no existe o hubo un error.
     */
    public Paciente buscarPorDocumento(String documento) {
        String sql = "SELECT * FROM pacientes WHERE documento = ?";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, documento);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPaciente(rs);
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al buscar paciente: " + e.getMessage());
        }
        return null; // no se encontró, o hubo error
    }
 
    /**
     * Actualiza los datos de un paciente ya existente (se identifica por su id).
     * @return true si se actualizó correctamente, false si hubo un error.
     */
    public boolean actualizarPaciente(Paciente p) {
        String sql = "UPDATE pacientes SET nombre = ?, apellido = ?, "
                + "fecha_nacimiento = ? WHERE id_paciente = ?";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getFechaNacimiento());
            ps.setInt(4, p.getIdPaciente());
 
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al actualizar paciente: " + e.getMessage());
            return false;
        }
    }
 
    /**
     * Devuelve la lista completa de pacientes registrados.
     * Útil para mostrarlos en la interfaz de Álvaro.
     */
    public List<Paciente> listarPacientes() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                lista.add(mapearPaciente(rs));
            }
 
        } catch (SQLException e) {
            System.out.println("Error al listar pacientes: " + e.getMessage());
        }
        return lista;
    }
 
    /**
     * Convierte una fila del ResultSet en un objeto Paciente.
     * Se separó en su propio método para no repetir este código en cada consulta
     * (principio de no repetir código / DRY).
     */
    private Paciente mapearPaciente(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setIdPaciente(rs.getInt("id_paciente"));
        p.setNombre(rs.getString("nombre"));
        p.setApellido(rs.getString("apellido"));
        p.setDocumento(rs.getString("documento"));
        p.setFechaNacimiento(rs.getString("fecha_nacimiento"));
        p.setUsuario(rs.getString("usuario"));
        p.setContrasenaHash(rs.getString("contrasena_hash"));
        return p;
    }
}