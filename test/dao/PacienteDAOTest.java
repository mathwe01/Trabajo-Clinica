package dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import conexion.Conexion;
import modelo.Paciente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seguridad.SeguridadClave;

/** Pruebas de integración JUnit 5 (requieren MySQL encendido y la BD creada). */
class PacienteDAOTest {

    private static final String DOC = "TEST-JUNIT-001";
    private final PacienteDAO dao = new PacienteDAO();

    private Paciente nuevo() {
        return new Paciente("Prueba", "Junit", DOC, "1995-03-15",
                "usuario_junit", SeguridadClave.hashear("Clave123"));
    }

    private void borrar() throws Exception {
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement("DELETE FROM pacientes WHERE documento = ?")) {
            ps.setString(1, DOC);
            ps.executeUpdate();
        }
    }

    @BeforeEach void antes() throws Exception { borrar(); }
    @AfterEach  void despues() throws Exception { borrar(); }

    @Test
    void registraYEncuentraPacientePorDocumento() {
        assertTrue(dao.registrarPaciente(nuevo()));
        Paciente p = dao.buscarPorDocumento(DOC);
        assertNotNull(p);
        assertEquals("Prueba", p.getNombre());
    }

    @Test
    void rechazaDocumentoRepetido() {
        assertTrue(dao.registrarPaciente(nuevo()));
        assertFalse(dao.registrarPaciente(nuevo())); // UNIQUE -> "Duplicate entry" esperado
    }

    @Test
    void buscarPorUsuarioDevuelveElPaciente() {
        dao.registrarPaciente(nuevo());
        assertNotNull(dao.buscarPorUsuario("usuario_junit"));
    }
}
