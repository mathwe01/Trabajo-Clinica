package dao;

import modelo.HistoriaClinica;

/** Contrato de acceso a datos de historias clínicas (inversión de dependencias). */
public interface IHistoriaClinicaDAO {
    boolean registrarHistoria(HistoriaClinica h);
    HistoriaClinica buscarPorPaciente(int idPaciente);
}
