package dao;

import modelo.Paciente;
import java.util.List;

/**
 * Contrato de acceso a datos de pacientes (principio de inversión de
 * dependencias, la "D" de SOLID): las capas superiores dependen de esta
 * interfaz y no de la implementación con MySQL.
 */
public interface IPacienteDAO {
    boolean registrarPaciente(Paciente p);
    Paciente buscarPorDocumento(String documento);
    Paciente buscarPorUsuario(String usuario);
    boolean actualizarPaciente(Paciente p);
    List<Paciente> listarPacientes();
}
