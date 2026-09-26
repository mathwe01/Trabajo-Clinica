package modelo;
 
/**
 * Clase modelo de Paciente, alineada a la tabla "pacientes" de la base de datos.

 */
public class Paciente {
 
    private int idPaciente;
    private String nombre;
    private String apellido;
    private String documento;
    private String fechaNacimiento; // formato "yyyy-MM-dd"
    private String usuario;
    private String contrasenaHash;
 
    public Paciente() {
    }
 
    public Paciente(String nombre, String apellido, String documento,
                     String fechaNacimiento, String usuario, String contrasenaHash) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.contrasenaHash = contrasenaHash;
    }
 
    public int getIdPaciente() {
        return idPaciente;
    }
 
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getApellido() {
        return apellido;
    }
 
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
 
    public String getDocumento() {
        return documento;
    }
 
    public void setDocumento(String documento) {
        this.documento = documento;
    }
 
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
 
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
 
    public String getUsuario() {
        return usuario;
    }
 
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
 
    public String getContrasenaHash() {
        return contrasenaHash;
    }
 
    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }
}