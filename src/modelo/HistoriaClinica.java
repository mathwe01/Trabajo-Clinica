package modelo;
 
/**
 * Clase modelo HistoriaClinica, según el diagrama de clases del proyecto.
 * Atributos: IdHistoria, fecha, antecedentes, diagnostico, tratamiento.
 * Método: consultarHistoria.
 *
 * Corresponde a la tabla "historias_clinicas" de la base de datos
 * (creada por Mathew). Relación: un paciente tiene una sola historia
 * clínica (idPaciente es único en esa tabla).
 *
 * Autor: Daniel
 */
public class HistoriaClinica {
 
    private int idHistoria;
    private int idPaciente; // relaciona esta historia con un paciente
    private String fecha;
    private String antecedentes;
    private String diagnostico;
    private String tratamiento;
 
    public HistoriaClinica() {
    }
 
    public HistoriaClinica(int idPaciente, String fecha, String antecedentes,
                            String diagnostico, String tratamiento) {
        this.idPaciente = idPaciente;
        this.fecha = fecha;
        this.antecedentes = antecedentes;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }
 
    /**
     * consultarHistoria(): devuelve un resumen legible de la historia
     * clínica. Cuando exista una clase HistoriaClinicaDAO (con sus
     * propias consultas SQL a la tabla historias_clinicas), este método
     * debería llamarla para traer los datos actualizados desde la base
     * de datos, en vez de solo mostrar lo que ya tiene el objeto en memoria.
     */
    public String consultarHistoria() {
        return "Historia Clinica #" + idHistoria
                + " | Fecha: " + fecha
                + " | Antecedentes: " + antecedentes
                + " | Diagnostico: " + diagnostico
                + " | Tratamiento: " + tratamiento;
    }
 
    // ---------- Getters y setters ----------
 
    public int getIdHistoria() {
        return idHistoria;
    }
 
    public void setIdHistoria(int idHistoria) {
        this.idHistoria = idHistoria;
    }
 
    public int getIdPaciente() {
        return idPaciente;
    }
 
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
 
    public String getFecha() {
        return fecha;
    }
 
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
 
    public String getAntecedentes() {
        return antecedentes;
    }
 
    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }
 
    public String getDiagnostico() {
        return diagnostico;
    }
 
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
 
    public String getTratamiento() {
        return tratamiento;
    }
 
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}