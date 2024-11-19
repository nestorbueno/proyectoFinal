/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author Derek
 */
import javax.swing.*;
import java.awt.*;
import Controlador.LoginController;

public class RecuperarContrasenaView extends JFrame {
    private final LoginController controller;
    private final JTextField usuarioField;
    private final JTextField preguntaSeguridadField;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 200;
    
    public RecuperarContrasenaView() {
        controller = new LoginController();
        usuarioField = new JTextField(20);
        preguntaSeguridadField = new JTextField(20);
        initComponents();
    }
    
    private void initComponents() {
        configurarVentana();
        JPanel mainPanel = crearPanelPrincipal();
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void configurarVentana() {
        setTitle("Recuperar Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        agregarCampos(panel, gbc);
        agregarBotones(panel, gbc);
        
        return panel;
    }
    
    private void agregarCampos(JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);
        
        gbc.gridx = 1;
        panel.add(usuarioField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Pregunta de Seguridad:"), gbc);
        
        gbc.gridx = 1;
        panel.add(preguntaSeguridadField, gbc);
    }
    
    private void agregarBotones(JPanel panel, GridBagConstraints gbc) {
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton recuperarButton = new JButton("Recuperar");
        JButton cancelarButton = new JButton("Cancelar");
        
        recuperarButton.addActionListener(e -> recuperar());
        cancelarButton.addActionListener(e -> dispose());
        
        botonesPanel.add(recuperarButton);
        botonesPanel.add(cancelarButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(botonesPanel, gbc);
    }
    
    private void recuperar() {
        String usuario = usuarioField.getText();
        String preguntaSeguridad = preguntaSeguridadField.getText();
        
        if (usuario.isEmpty() || preguntaSeguridad.isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return;
        }
        
        String contrasena = controller.recuperarContrasena(usuario, preguntaSeguridad);
        if (contrasena != null) {
            JOptionPane.showMessageDialog(this, "Su contraseña es: " + contrasena);
            dispose();
        } else {
            mostrarError("Usuario o pregunta de seguridad incorrectos");
        }
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}