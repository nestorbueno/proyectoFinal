package Vista;

import javax.swing.*;
import java.awt.*;
import Controlador.LoginController;
import Util.CSVGenerator;

public class LoginView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final LoginController controller;
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private String tipoUsuario;

    public LoginView() {
        CSVGenerator.generarArchivosCSV(); // Generar archivos si no existen
        controller = new LoginController(); // Inicializar controlador
        seleccionarTipoUsuario(); // Seleccionar el tipo de usuario
    }

    private void seleccionarTipoUsuario() {
        String[] opciones = {"Alumno", "Secretaria"};
        tipoUsuario = (String) JOptionPane.showInputDialog(
            null,
            "Seleccione tipo de usuario:",
            "Tipo de Usuario",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (tipoUsuario == null) {
            System.exit(0); // Salir si no selecciona ningún tipo
        }

        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema Escolar - Login " + tipoUsuario);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel titleLabel = new JLabel("Inicio de Sesión - " + tipoUsuario);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(new JLabel(tipoUsuario.equals("Alumno") ? "Número de Cuenta:" : "Usuario:"), gbc);

        gbc.gridx = 1;
        usuarioField = new JTextField(20);
        mainPanel.add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        contrasenaField = new JPasswordField(20);
        mainPanel.add(contrasenaField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton loginButton = new JButton("Acceder");
        loginButton.addActionListener(e -> login());
        buttonPanel.add(loginButton);

        if (tipoUsuario.equals("Secretaria")) {
            JButton registroButton = new JButton("Registrar Nueva Secretaria");
            registroButton.addActionListener(e -> mostrarRegistroSecretaria());
            buttonPanel.add(registroButton);
        }

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setSize(400, 300);
    }

    private void login() {
        String usuario = usuarioField.getText();
        String contrasena = new String(contrasenaField.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        if (tipoUsuario.equals("Alumno")) {
            Alumno alumno = controller.buscarAlumnoPorNumeroCuenta(usuario);
            if (alumno != null && alumno.getContrasena().equals("pass" + usuario)) {
                JOptionPane.showMessageDialog(this,
                    String.format("Bienvenido al sistema\nTu número de inscripción es: %.4f",
                    alumno.getNumeroInscripcion()));
            } else {
                JOptionPane.showMessageDialog(this, "Número de cuenta o contraseña incorrectos.");
            }
        } else {
            if (controller.accederSecretaria(usuario, contrasena)) {
                JOptionPane.showMessageDialog(this, "Bienvenida, " + usuario);
                this.dispose();
                new SecretariaView(controller).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        }
    }

    private void mostrarRegistroSecretaria() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField nombreField = new JTextField();
        JTextField usuarioField = new JTextField();
        JPasswordField contrasenaField = new JPasswordField();

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Usuario:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(contrasenaField);

        int result = JOptionPane.showConfirmDialog(
            this, panel, "Registrar Nueva Secretaria",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String usuario = usuarioField.getText();
            String contrasena = new String(contrasenaField.getPassword());

            if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            if (controller.registrarSecretaria(nombre, usuario, contrasena)) {
                JOptionPane.showMessageDialog(this, "Secretaria registrada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error: El usuario ya existe.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginView().setVisible(true);
        });
    }
}
