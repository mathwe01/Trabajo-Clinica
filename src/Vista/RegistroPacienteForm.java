package vista;
 
import javax.swing.*;
import java.awt.*;
 
/**
 * Ventana (formulario) para registrar un paciente.
 * Etapa 2: se agregan los campos y botones (todavía sin conectar al DAO).
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
        panel.add(btnGuardar);
 
        JButton btnLimpiar = new JButton("Limpiar campos");
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panel.add(btnLimpiar);
 
        lblMensaje = new JLabel(" ");
        lblMensaje.setForeground(new Color(0, 100, 0));
 
        add(panel, BorderLayout.CENTER);
        add(lblMensaje, BorderLayout.SOUTH);
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