package vista;
 
import dao.PacienteDAO;
import modelo.Paciente;
 
import javax.swing.*;
import java.awt.*;
 
/**
 * Ventana (formulario) para registrar un paciente.
 * Etapa 3: se conecta el botón "Guardar" con el PacienteDAO (capa de Luis)
 * para guardar el paciente en la base de datos real.
 *
 * Autor: Álvaro
 */
public class RegistroPacienteForm extends JFrame {
 
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JTextField txtFechaNacimiento;
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
        setLocationRelativeTo(null);
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
     * Toma los datos del formulario, arma un Paciente y lo guarda
     * llamando al DAO de Luis.
     */
    private void guardarPaciente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String documento = txtDocumento.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
 
        Paciente paciente = new Paciente(nombre, apellido, documento,
                fechaNacimiento, usuario, contrasena);
 
        boolean guardado = pacienteDAO.registrarPaciente(paciente);
 
        if (guardado) {
            lblMensaje.setForeground(new Color(0, 100, 0));
            lblMensaje.setText("  Paciente registrado correctamente.");
            limpiarCampos();
        } else {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("  No se pudo registrar el paciente.");
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
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistroPacienteForm form = new RegistroPacienteForm();
            form.setVisible(true);
        });
    }
}