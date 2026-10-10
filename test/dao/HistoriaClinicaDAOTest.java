package dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import conexion.Conexion;
import modelo.HistoriaClinica;
import modelo.Paciente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seguridad.SeguridadClave;

/** Pruebas de integración JUnit 5 (requieren MySQL encendido y la BD creada). */
class HistoriaClinicaDAOTest {

    private static final String DOC = "TEST-JUNIT-HC1";
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final HistoriaClinicaDAO historiaDAO = new HistoriaClinicaDAO();
    private int idPaciente;

    private void borrar() throws Exception { // ON DELETE CASCADE borra también la historia
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement("DELETE FROM pacientes WHERE documento = ?")) {
            ps.setString(1, DOC);
            ps.executeUpdate();
        }
    }

    @BeforeEach
    void antes() throws Exception {
        borrar();
        pacienteDAO.registrarPaciente(new Paciente("Prueba", "Historia", DOC, "1990-06-20",
                "usuario_junit_hc", SeguridadClave.hashear("Clave123")));
        idPaciente = pacienteDAO.buscarPorDocumento(DOC).getIdPaciente();
    }

    @AfterEach void despues() throws Exception { borrar(); }

    @Test
    void registraYConsultaLaHistoriaClinica() {
        HistoriaClinica h = new HistoriaClinica(idPaciente, "2026-10-10", "Ninguno", "Gripe", "Reposo");
        assertTrue(historiaDAO.registrarHistoria(h));
        HistoriaClinica leida = historiaDAO.buscarPorPaciente(idPaciente);
        assertNotNull(leida);
        assertEquals("Gripe", leida.getDiagnostico());
    }

    @Test
    void rechazaUnaSegundaHistoriaParaElMismoPaciente() {
        HistoriaClinica h = new HistoriaClinica(idPaciente, "2026-10-10", "Ninguno", "Gripe", "Reposo");
        assertTrue(historiaDAO.registrarHistoria(h));
        assertFalse(historiaDAO.registrarHistoria(h)); // id_paciente es UNIQUE
    }

    @Test
    void buscarHistoriaInexistenteDevuelveNull() {
        assertNull(historiaDAO.buscarPorPaciente(idPaciente));
    }
}
