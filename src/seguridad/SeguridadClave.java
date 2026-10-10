package seguridad;
 
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
 
/**
 * Utilidad para proteger contraseñas (Ley N.° 29733: no guardar datos en texto plano).
 * Usa PBKDF2 con HMAC-SHA256 y una sal aleatoria por contraseña.
 * Solo usa clases del JDK (sin librerías externas).
 *
 * Formato guardado en la BD: pbkdf2$iteraciones$salBase64$hashBase64
 */
public final class SeguridadClave {
 
    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final int ITERACIONES = 65_536;
    private static final int LONGITUD_SAL = 16;   // bytes
    private static final int LONGITUD_HASH = 256; // bits
 
    // Constructor privado: es una clase de utilidad, no se instancia
    private SeguridadClave() { }
 
    /** Genera el hash con sal de una contraseña en texto plano. */
    public static String hashear(String contrasena) {
        byte[] sal = new byte[LONGITUD_SAL];
        new SecureRandom().nextBytes(sal);
        byte[] hash = derivar(contrasena, sal, ITERACIONES);
        return "pbkdf2$" + ITERACIONES + "$"
                + Base64.getEncoder().encodeToString(sal) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }
 
    /** Verifica una contraseña ingresada contra el hash guardado. */
    public static boolean verificar(String contrasenaIngresada, String hashGuardado) {
        if (contrasenaIngresada == null || hashGuardado == null) {
            return false;
        }
        String[] partes = hashGuardado.split("\\$");
        if (partes.length != 4 || !partes[0].equals("pbkdf2")) {
            return false; // formato inválido (por ejemplo, una clave antigua en texto plano)
        }
        try {
            int iteraciones = Integer.parseInt(partes[1]);
            byte[] sal = Base64.getDecoder().decode(partes[2]);
            byte[] esperado = Base64.getDecoder().decode(partes[3]);
            byte[] calculado = derivar(contrasenaIngresada, sal, iteraciones);
            return java.security.MessageDigest.isEqual(esperado, calculado); // tiempo constante
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
 
    private static byte[] derivar(String contrasena, byte[] sal, int iteraciones) {
        try {
            PBEKeySpec spec = new PBEKeySpec(contrasena.toCharArray(), sal, iteraciones, LONGITUD_HASH);
            return SecretKeyFactory.getInstance(ALGORITMO).generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("No se pudo calcular el hash de la contraseña", e);
        }
    }
}