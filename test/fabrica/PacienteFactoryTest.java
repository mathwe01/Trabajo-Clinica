package fabrica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import modelo.Paciente;
import seguridad.SeguridadClave;

/** Pruebas JUnit 5 del patrón Factory Method (PacienteFactory). */
class PacienteFactoryTest {

    @Test
    void limpiaEspaciosSobrantes() {
        Paciente p = PacienteFactory.crear("  Ana ", " Pérez", " 12345678 ",
                "2000-05-10", " ana ", "Clave123");
        assertEquals("Ana", p.getNombre());
        assertEquals("12345678", p.getDocumento());
    }

    @Test
    void laContrasenaNoQuedaEnTextoPlano() {
        Paciente p = PacienteFactory.crear("Ana", "Pérez", "12345678",
                "2000-05-10", "ana", "Clave123");
        assertNotEquals("Clave123", p.getContrasenaHash());
    }

    @Test
    void elHashVerificaConLaClaveCorrecta() {
        Paciente p = PacienteFactory.crear("Ana", "Pérez", "12345678",
                "2000-05-10", "ana", "Clave123");
        assertTrue(SeguridadClave.verificar("Clave123", p.getContrasenaHash()));
    }

    @Test
    void rechazaDatosObligatoriosVacios() {
        // AJUSTAR la excepción si exigir() lanza otra distinta
        assertThrows(IllegalArgumentException.class, () ->
                PacienteFactory.crear("", "Pérez", "12345678",
                        "2000-05-10", "ana", "Clave123"));
    }
}
