package fabrica;
 
import modelo.Paciente;
import seguridad.SeguridadClave;
 
/**
 * Patrón de diseño FACTORY METHOD para crear objetos Paciente.
 *
 * Centraliza en un solo lugar la creación de un paciente: valida que los
 * datos obligatorios no estén vacíos, limpia los espacios sobrantes y
 * convierte la contraseña en hash con sal (clase SeguridadClave) antes de
 * entregar el objeto. Así, ningún formulario puede crear un Paciente con la
 * contraseña en texto plano (Ley N.° 29733) y la regla no se repite en cada
 * pantalla.
 *
 * El constructor es privado porque la clase solo ofrece métodos estáticos.
 */
public final class PacienteFactory {
 
    private PacienteFactory() {
    }
 
    /**
     * Crea un Paciente listo para guardarse en la base de datos.
     *
     * @param contrasena contraseña en texto plano escrita por el usuario;
     *                   el Paciente devuelto solo contiene su hash.
     * @return el Paciente con los datos limpios y la contraseña protegida.
     * @throws IllegalArgumentException si algún dato obligatorio está vacío.
     */
    public static Paciente crear(String nombre, String apellido, String documento,
                                 String fechaNacimiento, String usuario, String contrasena) {
        return new Paciente(
                exigir(nombre, "nombre"),
                exigir(apellido, "apellido"),
                exigir(documento, "documento"),
                exigir(fechaNacimiento, "fecha de nacimiento"),
                exigir(usuario, "usuario"),
                SeguridadClave.hashear(exigirClave(contrasena)));
    }
 
    /** Verifica que el dato no sea nulo ni vacío y devuelve el texto sin espacios sobrantes. */
    private static String exigir(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
        return valor.trim();
    }
 
    /** La contraseña se valida igual, pero no se recorta: se hashea tal como se recibió. */
    private static String exigirClave(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo contraseña es obligatorio.");
        }
        return contrasena;
    }
}