package dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import conexion.Conexion;
import modelo.Atencion;
import modelo.HistoriaClinica;
import modelo.Paciente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seguridad.SeguridadClave;

/** Pruebas de integración JUnit 5 (requieren MySQL encendido y la BD creada). */
class AtencionDAOTest {

    private static final String DOC = "TEST-JUNIT-AT1";
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final HistoriaClinicaDAO historiaDAO = new HistoriaClinicaDAO();
    private final AtencionDAO atencionDAO = new AtencionDAO();
    private int idHistoria;

    private void borrar() throws Exception { // ON DELETE CASCADE borra historia y atenciones
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement("DELETE FROM pacientes WHERE documento = ?")) {
            ps.setString(1, DOC);
            ps.executeUpdate();
        }
    }

    @BeforeEach
    void antes() throws Exception {
        borrar();
        pacienteDAO.registrarPaciente(new Paciente("Prueba", "Atencion", DOC, "1985-02-11",
                "usuario_junit_at", SeguridadClave.hashear("Clave123")));
        int idPaciente = pacienteDAO.buscarPorDocumento(DOC).getIdPaciente();
        historiaDAO.registrarHistoria(new HistoriaClinica(idPaciente, "2026-10-10", "Ninguno", "Por definir", "Por definir"));
        idHistoria = historiaDAO.buscarPorPaciente(idPaciente).getIdHistoria();
    }

    @AfterEach void despues() throws Exception { borrar(); }

    @Test
    void registraYListaAtenciones() {
        assertTrue(atencionDAO.registrarAtencion(new Atencion(idHistoria, "2026-10-10", "Control")));
        assertTrue(atencionDAO.registrarAtencion(new Atencion(idHistoria, "2026-10-11", "Dolor de cabeza")));
        List<Atencion> lista = atencionDAO.listarPorHistoria(idHistoria);
        assertEquals(2, lista.size());
        assertEquals("Control", lista.get(0).getMotivo()); // ordenadas por fecha
    }

    @Test
    void rechazaAtencionDeUnaHistoriaInexistente() {
        assertFalse(atencionDAO.registrarAtencion(new Atencion(-1, "2026-10-10", "Control"))); // clave foránea
    }

    @Test
    void listaVaciaSiLaHistoriaNoTieneAtenciones() {
        assertTrue(atencionDAO.listarPorHistoria(idHistoria).isEmpty());
    }
}
