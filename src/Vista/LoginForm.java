package vista;
 
import dao.PacienteDAO;
import modelo.Paciente;
import seguridad.SeguridadClave;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
 
/**
 * Ventana de inicio de sesión del Centro de Salud Ganímedes.
 * Verifica el usuario y la contraseña contra el hash con sal guardado en la
 * base de datos (Ley N.° 29733) y bloquea el acceso tras 3 intentos fallidos.
 *
 * Programación orientada a eventos: el botón "Ingresar" dispara la validación.
 */
public class LoginForm extends JFrame {
 
    private static final int MAX_INTENTOS = 3;
 
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JLabel lblMensaje;
 
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private int intentosFallidos = 0;
 
    public LoginForm() {
        configurarVentana();
        construirFormulario();
    }
 
    private void configurarVentana() {
        setTitle("Inicio de sesión - Centro de Salud Ganímedes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }
 
    private void construirFormulario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
 
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);
 
        btnIngresar = new JButton("Ingresar");
        btnIngresar.addActionListener(e -> iniciarSesion());
        // Enter en el campo de contraseña también ingresa
        txtContrasena.addActionListener(e -> iniciarSesion());
        panel.add(btnIngresar);
 
        JButton btnRegistrar = new JButton("Registrar paciente");
        btnRegistrar.addActionListener(e -> new RegistroPacienteForm().setVisible(true));
        panel.add(btnRegistrar);
 
        lblMensaje = new JLabel(" ");
        add(panel, BorderLayout.CENTER);
        add(lblMensaje, BorderLayout.SOUTH);
    }
 
    /**
     * Valida las credenciales. Si fallan {@value #MAX_INTENTOS} veces seguidas,
     * deshabilita el botón. El mensaje de error es genérico a propósito para no
     * revelar si el usuario existe.
     */
    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
 
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Ingrese usuario y contraseña.", true);
            return;
        }
 
        Paciente paciente = pacienteDAO.buscarPorUsuario(usuario);
        boolean credencialesValidas = paciente != null
                && SeguridadClave.verificar(contrasena, paciente.getContrasenaHash());
 
        if (credencialesValidas) {
            registrarAcceso(usuario);
            intentosFallidos = 0;
            mostrarMensaje("Acceso concedido. Bienvenido(a), " + paciente.getNombre() + ".", false);
            txtContrasena.setText("");
        } else {
            intentosFallidos++;
            txtContrasena.setText("");
            if (intentosFallidos >= MAX_INTENTOS) {
                btnIngresar.setEnabled(false);
                mostrarMensaje("Acceso bloqueado tras " + MAX_INTENTOS + " intentos fallidos.", true);
            } else {
                mostrarMensaje("Usuario o contraseña incorrectos. Intento "
                        + intentosFallidos + " de " + MAX_INTENTOS + ".", true);
            }
        }
    }
 
    /** Deja constancia del acceso (caso de uso: "el sistema registra el acceso"). */
    private void registrarAcceso(String usuario) {
        System.out.println("[ACCESO] Usuario '" + usuario + "' ingresó el " + LocalDateTime.now());
    }
 
    private void mostrarMensaje(String texto, boolean esError) {
        lblMensaje.setForeground(esError ? Color.RED : new Color(0, 100, 0));
        lblMensaje.setText("  " + texto);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}