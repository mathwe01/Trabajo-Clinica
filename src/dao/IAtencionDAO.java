package dao;

import modelo.Atencion;
import java.util.List;

/** Contrato de acceso a datos de atenciones (inversión de dependencias). */
public interface IAtencionDAO {
    boolean registrarAtencion(Atencion a);
    List<Atencion> listarPorHistoria(int idHistoria);
}
