package servicio;
 
import dao.IPacienteDAO;
import modelo.Paciente;
 
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
 
/**
 * Capa de servicio: aplica funciones de orden superior (filter, map, reduce)
 * sobre la colección de pacientes del sistema (paradigma funcional).
 */
public class ServicioPacientes {
 
    private final IPacienteDAO pacienteDAO;
 
    public ServicioPacientes(IPacienteDAO pacienteDAO) { // inyección: facilita pruebas (DIP)
        this.pacienteDAO = pacienteDAO;
    }
 
    /** Edad en años a partir de "yyyy-MM-dd"; -1 si el formato es inválido. */
    static int calcularEdad(String fechaNacimiento) {
        try {
            return Period.between(LocalDate.parse(fechaNacimiento), LocalDate.now()).getYears();
        } catch (DateTimeParseException | NullPointerException e) {
            return -1;
        }
    }
 
    /** FILTER: pacientes que cumplen una condición cualquiera (función como parámetro). */
    public List<Paciente> filtrar(List<Paciente> pacientes, Predicate<Paciente> condicion) {
        return pacientes.stream().filter(condicion).collect(Collectors.toList());
    }
 
    /** FILTER con lambda: pacientes mayores de una edad. */
    public List<Paciente> mayoresDe(List<Paciente> pacientes, int edad) {
        return filtrar(pacientes, p -> calcularEdad(p.getFechaNacimiento()) >= edad);
    }
 
    /** MAP: transforma cada paciente en un texto, sin exponer datos sensibles. */
    public List<String> resumenesProtegidos(List<Paciente> pacientes) {
        Function<Paciente, String> aResumen = p ->
                p.getNombre() + " " + p.getApellido() + " - DNI " + enmascarar(p.getDocumento());
        return pacientes.stream().map(aResumen).collect(Collectors.toList());
    }
 
    /** REDUCE: suma de edades (base para calcular el promedio). */
    public int sumaDeEdades(List<Paciente> pacientes) {
        return pacientes.stream()
                .map(p -> calcularEdad(p.getFechaNacimiento()))
                .filter(edad -> edad >= 0)
                .reduce(0, Integer::sum);
    }
 
    /** Muestra solo los 2 últimos caracteres: ****5678 -> minimiza la exposición (Ley 29733). */
    static String enmascarar(String documento) {
        if (documento == null || documento.length() <= 2) {
            return "****";
        }
        return "*".repeat(documento.length() - 2) + documento.substring(documento.length() - 2);
    }
 
    /** Atajo que consulta la BD y devuelve resúmenes protegidos de pacientes mayores de edad. */
    public List<String> resumenAdultos() {
        return resumenesProtegidos(mayoresDe(pacienteDAO.listarPacientes(), 18));
    }
}