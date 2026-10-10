package seguridad;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/** Pruebas JUnit 5 de SeguridadClave (hash PBKDF2 con sal). Ley N.° 29733. */
class SeguridadClaveTest {

    @Test
    void laClaveNoQuedaEnTextoPlano() {
        String hash = SeguridadClave.hashear("MiClave123");
        assertNotEquals("MiClave123", hash);
        assertFalse(hash.contains("MiClave123"));
        assertTrue(hash.startsWith("pbkdf2$"));
    }

    @Test
    void aceptaClaveCorrectaYRechazaIncorrecta() {
        String hash = SeguridadClave.hashear("MiClave123");
        assertTrue(SeguridadClave.verificar("MiClave123", hash));
        assertFalse(SeguridadClave.verificar("otraClave", hash));
    }

    @Test
    void dosHashesDeLaMismaClaveSonDistintos() {
        String h1 = SeguridadClave.hashear("MiClave123");
        String h2 = SeguridadClave.hashear("MiClave123");
        assertNotEquals(h1, h2); // la sal aleatoria los diferencia
    }

    @Test
    void rechazaHashConFormatoInvalido() {
        assertFalse(SeguridadClave.verificar("abc", "clave_en_texto_plano"));
    }
}
