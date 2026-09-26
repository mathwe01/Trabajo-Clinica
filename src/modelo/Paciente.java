package modelo;
 
import dao.PacienteDAO;
 
/**
 * Clase modelo Paciente, según el diagrama de clases del proyecto.
 * Atributos: IdPacientes, Nombre, Apellido, Documento, FechaDeNacimiento.
 * Métodos: RegistroDePaciente, InicioDeSesionDePaciente, ActualizacionDeDatos.
 *
 * Nota: se agregaron "usuario" y "contrasenaHash" porque son necesarios
 * para que exista InicioDeSesionDePaciente() y porque ya están en la
 * tabla "pacientes" de la base de datos creada por Mathew.
 *
 * Autor: Daniel
 */
public class Paciente {
 
    private int idPaciente;
    private String nombre;
    private String apellido;
    private String documento;
    private String fechaNacimiento; // formato "yyyy-MM-dd"
    private String usuario;
    private String contrasenaHash;
 
    // DAO que se usa para hablar con la base de datos (capa DAO de Luis)
    private final PacienteDAO pacienteDAO = new PacienteDAO();
 
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
 
    // ---------- Métodos del diagrama de clases ----------
 
    /**
     * RegistroDePaciente(): guarda este paciente en la base de datos.
     * @return true si se registró correctamente.
     */
    public boolean registrarPaciente() {
        return pacienteDAO.registrarPaciente(this);
    }
 
    /**
     * InicioDeSesionDePaciente(): valida el usuario y contraseña ingresados
     * contra los datos guardados en la base de datos.
     * NOTA: por ahora compara directo contra contrasenaHash; cuando se
     * implemente el cifrado real de contraseñas, aquí se debe comparar
     * el hash generado del texto ingresado contra el guardado, no el
     * texto plano.
     */
    public boolean iniciarSesion(String usuarioIngresado, String contrasenaIngresada) {
        Paciente encontrado = pacienteDAO.buscarPorDocumento(this.documento);
        if (encontrado == null) {
            return false;
        }
        return encontrado.getUsuario().equals(usuarioIngresado)
                && encontrado.getContrasenaHash().equals(contrasenaIngresada);
    }
 
    /**
     * ActualizacionDeDatos(): actualiza los datos de este paciente en la
     * base de datos (requiere que idPaciente ya esté seteado).
     */
    public boolean actualizarDatos() {
        return pacienteDAO.actualizarPaciente(this);
    }
 
    // ---------- Getters y setters ----------
 
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