package modelo;
 
import dao.AtencionDAO;
 
/**
 * Clase modelo Atencion, según el diagrama de clases del proyecto.
 * Atributos: IdAtencion, Fecha, Motivo.
 * Método: registrarAtencion.
 *
 * Corresponde a la tabla "atenciones" de la base de datos (creada por
 * Mathew). Relación: una historia clínica puede tener muchas atenciones.
 *
 * Autor: Daniel
 */
public class Atencion {
 
    private int idAtencion;
    private int idHistoria; // relaciona esta atención con una historia clínica
    private String fecha;
    private String motivo;
 
    // DAO que se usa para hablar con la base de datos
    private final AtencionDAO atencionDAO = new AtencionDAO();
 
    public Atencion() {
    }
 
    public Atencion(int idHistoria, String fecha, String motivo) {
        this.idHistoria = idHistoria;
        this.fecha = fecha;
        this.motivo = motivo;
    }
 
    /**
     * registrarAtencion(): por ahora deja el registro listo en el objeto.
     * Cuando exista una clase AtencionDAO (con su propio INSERT a la
     * tabla atenciones), este método debería llamarla para guardar la
     * atención en la base de datos, igual que hace Paciente con
     * PacienteDAO.
     */
    public String registrarAtencion() {
        return "Atencion registrada -> Historia #" + idHistoria
                + " | Fecha: " + fecha
                + " | Motivo: " + motivo;
    }
 
    /**
     * guardarEnBD(): guarda esta atencion en la tabla atenciones
     * (requiere que idHistoria corresponda a una historia ya registrada).
     * @return true si se guardo correctamente.
     */
    public boolean guardarEnBD() {
        return atencionDAO.registrarAtencion(this);
    }
 
    // ---------- Getters y setters ----------
 
    public int getIdAtencion() {
        return idAtencion;
    }
 
    public void setIdAtencion(int idAtencion) {
        this.idAtencion = idAtencion;
    }
 
    public int getIdHistoria() {
        return idHistoria;
    }
 
    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }
 
    public String getFecha() {
        return fecha;
    }
 
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
 
    public String getMotivo() {
        return motivo;
    }
 
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}