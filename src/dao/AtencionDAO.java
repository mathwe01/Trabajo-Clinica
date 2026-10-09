package dao;
 
import conexion.Conexion;
import modelo.Atencion;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Capa DAO (Data Access Object) para la tabla "atenciones".
 * Sigue el mismo estilo que PacienteDAO: PreparedStatement, try-with-resources
 * y try-catch.
 *
 * Relacion en la base de datos: 1 historia clinica -> muchas atenciones.
 */
public class AtencionDAO {
 
    /**
     * Guarda (INSERT) una nueva atencion.
     * La historia clinica (idHistoria) debe existir previamente.
     * @return true si se guardo correctamente, false si hubo un error.
     */
    public boolean registrarAtencion(Atencion a) {
        String sql = "INSERT INTO atenciones (id_historia, fecha, motivo) VALUES (?, ?, ?)";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, a.getIdHistoria());
            ps.setString(2, a.getFecha());
            ps.setString(3, a.getMotivo());
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al registrar atencion: " + e.getMessage());
            return false;
        }
    }
 
    /**
     * Devuelve todas las atenciones de una historia clinica
     * (la lista queda vacia si no hay ninguna o si hubo un error).
     */
    public List<Atencion> listarPorHistoria(int idHistoria) {
        List<Atencion> lista = new ArrayList<>();
        String sql = "SELECT * FROM atenciones WHERE id_historia = ? ORDER BY fecha";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, idHistoria);
 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Atencion a = new Atencion();
                    a.setIdAtencion(rs.getInt("id_atencion"));
                    a.setIdHistoria(rs.getInt("id_historia"));
                    a.setFecha(rs.getString("fecha"));
                    a.setMotivo(rs.getString("motivo"));
                    lista.add(a);
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al listar atenciones: " + e.getMessage());
        }
        return lista;
    }
}