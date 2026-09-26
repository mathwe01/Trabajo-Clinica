import dao.PacienteDAO;
import modelo.Paciente;
import java.util.List;
 
/**
 * Clase de prueba para verificar que el PacienteDAO funciona.
 */
public class PruebaDAO {
 
    public static void main(String[] args) {
        PacienteDAO dao = new PacienteDAO();
 
        // 1. Registrar un paciente nuevo
        Paciente nuevo = new Paciente("Ana", "Torres Vega", "76543210",
                "2000-03-15", "atorres", "HASH_DE_PRUEBA");
        boolean guardado = dao.registrarPaciente(nuevo);
        System.out.println("¿Se registró el paciente? " + guardado);
 
        // 2. Buscar el paciente por documento
        Paciente encontrado = dao.buscarPorDocumento("76543210");
        if (encontrado != null) {
            System.out.println("Paciente encontrado: " + encontrado.getNombre()
                    + " " + encontrado.getApellido());
 
            // 3. Actualizar el nombre y actualizar en la base de datos
            encontrado.setNombre("Ana María");
            boolean actualizado = dao.actualizarPaciente(encontrado);
            System.out.println("¿Se actualizó el paciente? " + actualizado);
        } else {
            System.out.println("No se encontró el paciente.");
        }
 
        // 4. Listar todos los pacientes registrados
        List<Paciente> todos = dao.listarPacientes();
        System.out.println("Total de pacientes en la base de datos: " + todos.size());
    }
}