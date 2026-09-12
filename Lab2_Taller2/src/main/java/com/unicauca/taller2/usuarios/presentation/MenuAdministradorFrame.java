package com.unicauca.taller2.usuarios.presentation;

import com.unicauca.taller2.usuarios.services.UsuarioService;
import com.unicauca.taller2.usuarios.model.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Menú principal para usuarios con rol ADMINISTRADOR.
 */
public class MenuAdministradorFrame extends JFrame {

    private final Usuario usuario;
    private final UsuarioService usuarioService;
    private final LoginFrame loginFrame;

    /**
     * Constructor del menú de administrador.
     *
     * @param usuario        usuario administrador autenticado
     * @param usuarioService servicio de usuarios
     * @param loginFrame     ventana de inicio de sesión
     */
    public MenuAdministradorFrame(Usuario usuario, UsuarioService usuarioService, LoginFrame loginFrame) {
        this.usuario = usuario;
        this.usuarioService = usuarioService;
        this.loginFrame = loginFrame;
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Saber Pro - Panel de Administración");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                cerrarSesion();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(new Color(245, 245, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombreCompleto());
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBienvenida.setForeground(new Color(33, 37, 41));

        JLabel lblRol = new JLabel("Rol: " + usuario.getRol().getDisplayName());
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblRol.setForeground(new Color(108, 117, 125));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(lblBienvenida);
        infoPanel.add(lblRol);

        JButton btnCerrarSesion = LoginFrame.crearBotonSecundario("Cerrar sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(130, 34));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        headerPanel.add(infoPanel, BorderLayout.WEST);
        headerPanel.add(btnCerrarSesion, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel opcionesPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        opcionesPanel.setOpaque(false);
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnGestionUsuarios = crearBotonMenu("👥  Gestión de Usuarios", true);
        btnGestionUsuarios.addActionListener(e -> abrirGestionUsuarios());
        opcionesPanel.add(btnGestionUsuarios);

        JButton btnBancoPreguntas = crearBotonMenu("📝  Banco de Preguntas (próximamente)", false);
        opcionesPanel.add(btnBancoPreguntas);

        JButton btnReportes = crearBotonMenu("📊  Reportes y Estadísticas (próximamente)", false);
        opcionesPanel.add(btnReportes);

        JButton btnConfiguracion = crearBotonMenu("⚙️  Configuración del Sistema (próximamente)", false);
        opcionesPanel.add(btnConfiguracion);

        mainPanel.add(opcionesPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        pack();
        setMinimumSize(new Dimension(550, 400));
        setLocationRelativeTo(null);
    }

    private void abrirGestionUsuarios() {
        JDialog dialog = new JDialog(this, "Gestión de Usuarios", true);
        dialog.setContentPane(new GestionUsuariosPanel(usuarioService));
        dialog.pack();
        dialog.setMinimumSize(new Dimension(700, 450));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        loginFrame.mostrarDeNuevo();
    }

    private JButton crearBotonMenu(String texto, boolean habilitado) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setPreferredSize(new Dimension(0, 50));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setOpaque(true);

        if (habilitado) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(33, 37, 41));
        } else {
            btn.setEnabled(false);
            btn.setBackground(new Color(233, 236, 239));
            btn.setForeground(new Color(173, 181, 189));
        }

        return btn;
    }
}
