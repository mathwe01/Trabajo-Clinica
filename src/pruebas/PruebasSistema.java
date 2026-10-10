package pruebas;
 
import conexion.Conexion;
import dao.AtencionDAO;
import dao.HistoriaClinicaDAO;
import dao.PacienteDAO;
import modelo.Atencion;
import modelo.HistoriaClinica;
import modelo.Paciente;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
 
/**
 * Pruebas automatizadas del sistema (se ejecutan con "Run File").
 * No necesitan librerias externas: cada prueba verifica un resultado
 * esperado y muestra PASO o FALLO en la consola.
 *
 * Requisitos: MySQL encendido y la base bd_centrosaludganimedes creada
 * con el script schema_basedatos.sql.
 *
 * Las pruebas crean un paciente de prueba y al terminar lo eliminan
 * (las tablas hijas se borran solas por ON DELETE CASCADE), asi que no
 * dejan datos basura en la base.
 */
public class PruebasSistema {
 
    private static int total = 0;
    private static int pasaron = 0;
 
    private static void verificar(String nombrePrueba, boolean condicion) {
        total++;
        if (condicion) {
            pasaron++;
            System.out.println("[PASO]  " + nombrePrueba);
        } else {
            System.out.println("[FALLO] " + nombrePrueba);
        }
    }
 
    public static void main(String[] args) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        HistoriaClinicaDAO historiaDAO = new HistoriaClinicaDAO();
        AtencionDAO atencionDAO = new AtencionDAO();
 
        // Datos unicos para no chocar con pacientes reales
        String sufijo = String.valueOf(System.currentTimeMillis() % 1000000000L);
        String documentoPrueba = "T" + sufijo;
        String usuarioPrueba = "test_" + sufijo;
 
        System.out.println("=== PRUEBAS AUTOMATIZADAS - Centro de Salud Ganimedes ===");
 
        try {
            // ---- Prueba 1: registrar y buscar un paciente ----
            Paciente p = new Paciente("PruebaNombre", "PruebaApellido", documentoPrueba,
                    "2000-01-15", usuarioPrueba, "clave123");
            boolean registrado = pacienteDAO.registrarPaciente(p);
            Paciente encontrado = pacienteDAO.buscarPorDocumento(documentoPrueba);
            verificar("1. Registrar un paciente y encontrarlo por documento",
                    registrado && encontrado != null
                    && "PruebaNombre".equals(encontrado.getNombre()));
 
            // ---- Prueba 2: un documento repetido debe ser rechazado ----
            Paciente duplicado = new Paciente("Otro", "Otro", documentoPrueba,
                    "1999-02-02", usuarioPrueba + "b", "clave456");
            verificar("2. Rechazar un paciente con documento repetido",
                    !pacienteDAO.registrarPaciente(duplicado));
 
            // ---- Prueba 3: registrar y consultar una historia clinica ----
            if (encontrado != null) {
                HistoriaClinica h = new HistoriaClinica(encontrado.getIdPaciente(),
                        "2026-09-26", "Sin antecedentes", "Gripe comun", "Reposo e hidratacion");
                boolean historiaGuardada = h.guardarEnBD();
                HistoriaClinica historiaLeida = historiaDAO.buscarPorPaciente(encontrado.getIdPaciente());
                verificar("3. Registrar una historia clinica y consultarla",
                        historiaGuardada && historiaLeida != null
                        && "Gripe comun".equals(historiaLeida.getDiagnostico()));
 
                // ---- Prueba 4: registrar y listar una atencion ----
                if (historiaLeida != null) {
                    Atencion a = new Atencion(historiaLeida.getIdHistoria(),
                            "2026-09-27", "Control de seguimiento");
                    boolean atencionGuardada = a.guardarEnBD();
                    List<Atencion> atenciones = atencionDAO.listarPorHistoria(historiaLeida.getIdHistoria());
                    verificar("4. Registrar una atencion y listarla",
                            atencionGuardada && atenciones.size() == 1
                            && "Control de seguimiento".equals(atenciones.get(0).getMotivo()));
                } else {
                    verificar("4. Registrar una atencion y listarla", false);
                }
            } else {
                verificar("3. Registrar una historia clinica y consultarla", false);
                verificar("4. Registrar una atencion y listarla", false);
            }
 
        } finally {
            limpiar(documentoPrueba);
        }
 
        System.out.println("=== RESULTADO: " + pasaron + " de " + total + " pruebas pasaron ===");
    }
 
    /**
     * Elimina el paciente de prueba. Sus historias y atenciones se borran
     * automaticamente por la regla ON DELETE CASCADE de la base de datos.
     */
    private static void limpiar(String documento) {
        String sql = "DELETE FROM pacientes WHERE documento = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, documento);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo limpiar el paciente de prueba: " + e.getMessage());
        }
    }
}