package com.unicauca.taller2.usuarios;

import com.unicauca.taller2.usuarios.services.AutenticacionService;
import com.unicauca.taller2.usuarios.services.UsuarioService;
import com.unicauca.taller2.usuarios.access.PasswordHasher;
import com.unicauca.taller2.usuarios.access.PasswordPolicy;
import com.unicauca.taller2.usuarios.access.UsuarioRepository;
import com.unicauca.taller2.usuarios.access.ConexionSQLite;
import com.unicauca.taller2.usuarios.access.UsuarioRepositorySQLite;
import com.unicauca.taller2.usuarios.access.Argon2PasswordHasher;
import com.unicauca.taller2.usuarios.access.PasswordPolicyDefault;
import com.unicauca.taller2.usuarios.presentation.LoginFrame;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación.
 */
public class Main {

    /**
     * Método principal que inicializa la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        ConexionSQLite conexion = new ConexionSQLite();
        UsuarioRepository repository = new UsuarioRepositorySQLite(conexion);

        PasswordHasher hasher = new Argon2PasswordHasher();
        PasswordPolicy policy = new PasswordPolicyDefault();

        UsuarioService usuarioService = new UsuarioService(repository, hasher, policy);
        AutenticacionService autenticacionService = new AutenticacionService(repository, hasher);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("No se pudo aplicar el Look & Feel del sistema: " + e.getMessage());
            }

            LoginFrame loginFrame = new LoginFrame(autenticacionService, usuarioService);
            loginFrame.setVisible(true);
        });
    }
}
