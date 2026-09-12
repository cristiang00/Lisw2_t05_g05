package com.unicauca.taller2.usuarios.presentation;

import com.unicauca.taller2.usuarios.services.AutenticacionException;
import com.unicauca.taller2.usuarios.services.AutenticacionService;
import com.unicauca.taller2.usuarios.services.UsuarioService;
import com.unicauca.taller2.usuarios.model.Rol;
import com.unicauca.taller2.usuarios.model.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de inicio de sesión.
 */
public class LoginFrame extends JFrame {

    private final AutenticacionService autenticacionService;
    private final UsuarioService usuarioService;

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    /**
     * Constructor de la ventana de inicio de sesión.
     *
     * @param autenticacionService servicio de autenticación
     * @param usuarioService servicio de usuarios
     */
    public LoginFrame(AutenticacionService autenticacionService, UsuarioService usuarioService) {
        this.autenticacionService = autenticacionService;
        this.usuarioService = usuarioService;
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Saber Pro - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(245, 245, 250));

        JLabel lblTitulo = new JLabel("Sistema de Gestión Saber Pro", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblUsuario, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setMargin(new Insets(6, 8, 6, 8));
        formPanel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setMargin(new Insets(6, 8, 6, 8));
        formPanel.add(txtPassword, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        JButton btnLogin = crearBotonPrimario("Ingresar");
        btnLogin.addActionListener(e -> realizarLogin());

        JButton btnRegistro = crearBotonSecundario("Registrarse");
        btnRegistro.addActionListener(e -> abrirRegistro());

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegistro);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnLogin);

        setContentPane(mainPanel);
        pack();
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(null);
    }

    private void realizarLogin() {
        String nombreUsuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (nombreUsuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese usuario y contraseña.",
                    "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuario = autenticacionService.autenticar(nombreUsuario, password);
            abrirMenuSegunRol(usuario);
            dispose();
        } catch (AutenticacionException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    private void abrirMenuSegunRol(Usuario usuario) {
        if (usuario.getRol() == Rol.ADMINISTRADOR) {
            new MenuAdministradorFrame(usuario, usuarioService, this).setVisible(true);
        } else {
            new MenuGenericoFrame(usuario, this).setVisible(true);
        }
    }

    private void abrirRegistro() {
        new RegistroFrame(usuarioService, this).setVisible(true);
    }

    /**
     * Vuelve a mostrar la ventana de inicio de sesión.
     */
    public void mostrarDeNuevo() {
        txtUsuario.setText("");
        txtPassword.setText("");
        setVisible(true);
    }

    static JButton crearBotonPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(13, 110, 253));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 38));
        return btn;
    }

    static JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(108, 117, 125));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 38));
        return btn;
    }
}
