package servicio;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import modelo.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.PacienteDAO;

/** Pruebas JUnit 5 de las funciones de orden superior (filter, map, reduce). */
class ServicioPacientesTest {

    private ServicioPacientes servicio;
    private Paciente adulto;
    private Paciente menor;

    @BeforeEach
    void preparar() {
        servicio = new ServicioPacientes(new PacienteDAO()); // no abre conexión hasta usarse
        adulto = new Paciente("Ana", "Pérez", "12345678", "1990-01-01", "ana", "hash");
        menor  = new Paciente("Luis", "Gómez", "87654321", "2020-01-01", "luis", "hash");
    }

    @Test
    void filterDejaSoloMayoresDeEdad() {
        List<Paciente> r = servicio.mayoresDe(Arrays.asList(adulto, menor), 18);
        assertEquals(1, r.size());
        assertEquals("Ana", r.get(0).getNombre());
    }

    @Test
    void mapEnmascaraElDni() {
        List<String> r = servicio.resumenesProtegidos(Arrays.asList(adulto));
        assertTrue(r.get(0).contains("******78"));
        assertFalse(r.get(0).contains("12345678"));
    }

    @Test
    void reduceSumaLasEdadesValidas() {
        int suma = servicio.sumaDeEdades(Arrays.asList(adulto, menor));
        assertTrue(suma > 0);
    }

    @Test
    void enmascararDocumentoCortoONulo() {
        assertEquals("****", ServicioPacientes.enmascarar(null));
        assertEquals("****", ServicioPacientes.enmascarar("12"));
    }
}
