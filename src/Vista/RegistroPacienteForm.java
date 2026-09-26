package vista;
 
import javax.swing.*;
 
/**
 * Ventana (formulario) para registrar un paciente.
 * Etapa 1: solo la ventana base, sin campos todavía.
 *
 * Autor: Álvaro
 */
public class RegistroPacienteForm extends JFrame {
 
    public RegistroPacienteForm() {
        configurarVentana();
    }
 
    private void configurarVentana() {
        setTitle("Registro de Paciente - Centro de Salud Ganímedes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null); // centra la ventana en la pantalla
        setResizable(false);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistroPacienteForm form = new RegistroPacienteForm();
            form.setVisible(true);
        });
    }
}