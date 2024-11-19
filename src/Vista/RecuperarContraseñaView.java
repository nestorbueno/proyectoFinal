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
    private LoginController controller;
    private JTextField usuarioField;
    private JTextField preguntaSeguridadField;
    
    public RecuperarContrasenaView() {
        controller = new LoginController();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Recuperar Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setSize(400, 150);
        
        add(new JLabel("Usuario:"));
        usuarioField = new JTextField();
        add(usuarioField);
        
        add(new JLabel("Pregunta de Seguridad:"));
        preguntaSeguridadField = new JTextField();
        add(preguntaSeguridadField);
        
        JButton recuperarButton = new JButton("Recuperar");
        recuperarButton.addActionListener(e -> recuperar());
        add(recuperarButton);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> dispose());
        add(cancelarButton);
        
        setLocationRelativeTo(null);
    }
    
    private void recuperar() {
        String usuario = usuarioField.getText();
        String preguntaSeguridad = preguntaSeguridadField.getText();
        
        if (usuario.isEmpty() || preguntaSeguridad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }
        
        String contrasena = controller.recuperarContrasena(usuario, preguntaSeguridad);
        if (contrasena != null) {
            JOptionPane.showMessageDialog(this, "Su contraseña es: " + contrasena);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o pregunta de seguridad incorrectos");
        }
    }
}
