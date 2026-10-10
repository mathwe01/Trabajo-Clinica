package dao;
 
import conexion.Conexion;
import modelo.HistoriaClinica;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 * Capa DAO (Data Access Object) para la tabla "historias_clinicas".
 * Sigue el mismo estilo que PacienteDAO: PreparedStatement para evitar
 * inyeccion SQL, try-with-resources para cerrar la conexion y try-catch
 * para no dejar que el programa se caiga ante un error de base de datos.
 *
 * Relacion en la base de datos: 1 paciente -> 1 historia clinica
 * (id_paciente es UNIQUE en la tabla).
 */
public class HistoriaClinicaDAO implements IHistoriaClinicaDAO {
 
    /**
     * Guarda (INSERT) una nueva historia clinica.
     * El paciente (idPaciente) debe existir previamente en la tabla pacientes.
     * @return true si se guardo correctamente, false si hubo un error
     *         (por ejemplo, si ese paciente ya tiene una historia clinica).
     */
    @Override
    public boolean registrarHistoria(HistoriaClinica h) {
        String sql = "INSERT INTO historias_clinicas (id_paciente, fecha, "
                + "antecedentes, diagnostico, tratamiento) VALUES (?, ?, ?, ?, ?)";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, h.getIdPaciente());
            ps.setString(2, h.getFecha());
            ps.setString(3, h.getAntecedentes());
            ps.setString(4, h.getDiagnostico());
            ps.setString(5, h.getTratamiento());
 
            return ps.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Error al registrar historia clinica: " + e.getMessage());
            return false;
        }
    }
 
    /**
     * Busca la historia clinica de un paciente.
     * @return la HistoriaClinica encontrada, o null si no existe o hubo un error.
     */
    @Override
    public HistoriaClinica buscarPorPaciente(int idPaciente) {
        String sql = "SELECT * FROM historias_clinicas WHERE id_paciente = ?";
 
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, idPaciente);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HistoriaClinica h = new HistoriaClinica();
                    h.setIdHistoria(rs.getInt("id_historia"));
                    h.setIdPaciente(rs.getInt("id_paciente"));
                    h.setFecha(rs.getString("fecha"));
                    h.setAntecedentes(rs.getString("antecedentes"));
                    h.setDiagnostico(rs.getString("diagnostico"));
                    h.setTratamiento(rs.getString("tratamiento"));
                    return h;
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al buscar historia clinica: " + e.getMessage());
        }
        return null; // no se encontro, o hubo error
    }
}