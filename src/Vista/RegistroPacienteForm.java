package vista;
 
import dao.PacienteDAO;
import modelo.Paciente;
 
import javax.swing.*;
import java.awt.*;
 
/**
 * Ventana (formulario) para registrar un paciente.
 * Tiene los campos del paciente y un botón "Guardar" que llama al
 * PacienteDAO (capa de Luis) para guardar el paciente en la base de datos.
 *
 * Autor: Álvaro
 */
public class RegistroPacienteForm extends JFrame {
 
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JTextField txtFechaNacimiento; // formato yyyy-MM-dd
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnGuardar;
    private JLabel lblMensaje;
 
    private final PacienteDAO pacienteDAO = new PacienteDAO();
 
    public RegistroPacienteForm() {
        configurarVentana();
        construirFormulario();
    }
 
    private void configurarVentana() {
        setTitle("Registro de Paciente - Centro de Salud Ganímedes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null); // centra la ventana en la pantalla
        setResizable(false);
    }
 
    private void construirFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
 
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtDocumento = new JTextField();
        txtFechaNacimiento = new JTextField();
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
 
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
 
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
 
        panel.add(new JLabel("Documento (DNI):"));
        panel.add(txtDocumento);
 
        panel.add(new JLabel("Fecha nacimiento (yyyy-MM-dd):"));
        panel.add(txtFechaNacimiento);
 
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
 
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);
 
        btnGuardar = new JButton("Guardar paciente");
        btnGuardar.addActionListener(e -> guardarPaciente());
        panel.add(btnGuardar);
 
        JButton btnLimpiar = new JButton("Limpiar campos");
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panel.add(btnLimpiar);
 
        lblMensaje = new JLabel(" ");
        lblMensaje.setForeground(new Color(0, 100, 0));
 
        add(panel, BorderLayout.CENTER);
        add(lblMensaje, BorderLayout.SOUTH);
    }
 
    /**
     * Se ejecuta cuando el usuario hace clic en "Guardar paciente".
     * Valida los campos, arma un objeto Paciente y llama al DAO que hizo Luis
     * para guardarlo en la base de datos.
     */
    private void guardarPaciente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String documento = txtDocumento.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
 
        // Validación simple: ningún campo puede quedar vacío
        if (nombre.isEmpty() || apellido.isEmpty() || documento.isEmpty()
                || fechaNacimiento.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.", true);
            return;
        }
 
        // NOTA: aquí se guarda la contraseña tal cual por ahora. Cuando se
        // implemente el cifrado real, este valor debe pasarse hasheado
        // antes de mandarlo al DAO, no en texto plano.
        Paciente paciente = new Paciente(nombre, apellido, documento,
                fechaNacimiento, usuario, contrasena);
 
        boolean guardado = pacienteDAO.registrarPaciente(paciente);
 
        if (guardado) {
            mostrarMensaje("Paciente registrado correctamente.", false);
            limpiarCampos();
        } else {
            mostrarMensaje("No se pudo registrar el paciente. Verifica que el documento no esté repetido.", true);
        }
    }
 
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDocumento.setText("");
        txtFechaNacimiento.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
    }
 
    private void mostrarMensaje(String texto, boolean esError) {
        lblMensaje.setForeground(esError ? Color.RED : new Color(0, 100, 0));
        lblMensaje.setText("  " + texto);
    }
 
    public static void main(String[] args) {
        // Ejecuta la ventana. Requiere que MySQL esté corriendo y que
        // la base de datos bd_centrosaludganimedes ya exista.
        SwingUtilities.invokeLater(() -> {
            RegistroPacienteForm form = new RegistroPacienteForm();
            form.setVisible(true);
        });
    }
}