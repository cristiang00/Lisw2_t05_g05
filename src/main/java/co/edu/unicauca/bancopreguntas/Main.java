package co.edu.unicauca.bancopreguntas;

import co.edu.unicauca.bancopreguntas.dataaccess.QuestionImplRepository;
import co.edu.unicauca.bancopreguntas.domain.repositories.IQuestionRepository;
import co.edu.unicauca.bancopreguntas.domain.services.QuestionService;
import co.edu.unicauca.bancopreguntas.presentation.controllers.QuestionController;
import co.edu.unicauca.bancopreguntas.presentation.model.QuestionObservable;
import co.edu.unicauca.bancopreguntas.presentation.views.MainView;
import co.edu.unicauca.bancopreguntas.presentation.views.PieChartView;
import co.edu.unicauca.bancopreguntas.presentation.views.StatisticsView;

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
import co.edu.unicauca.bancopreguntas.domain.services.QuestionPluginManager;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal que inicializa la aplicación.
 * Ensambla las capas, configura el patrón MVC y el patrón Observer.
 */
public class Main {
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Configurar el look and feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Inicializar servicios de login
            ConexionSQLite conexion = new ConexionSQLite();
            UsuarioRepository usuarioRepository = new UsuarioRepositorySQLite(conexion);
            PasswordHasher hasher = new Argon2PasswordHasher();
            PasswordPolicy policy = new PasswordPolicyDefault();
            UsuarioService usuarioService = new UsuarioService(usuarioRepository, hasher, policy);
            AutenticacionService autenticacionService = new AutenticacionService(usuarioRepository, hasher);

            // Crear y mostrar LoginFrame pasando un callback para cuando el login sea exitoso
            LoginFrame loginFrame = new LoginFrame(autenticacionService, usuarioService, () -> {
                iniciarAplicacionPreguntas();
            });
            loginFrame.setVisible(true);
        });
    }

    private static void iniciarAplicacionPreguntas() {
        
        // 0. Inicializar el Plugin Manager
        try {
            String basePath = getBaseFilePath();
            QuestionPluginManager.init(basePath);
        } catch (Exception ex) {
            Logger.getLogger("Main").log(Level.SEVERE, "Error al inicializar plugins", ex);
        }

        // 1. Capa de Acceso a Datos
        IQuestionRepository repository = new QuestionImplRepository();

        // 2. Capa de Dominio
        QuestionService questionService = new QuestionService(repository);

        // 3. Capa de Presentación - Modelo Observable
        QuestionObservable questionObservable = new QuestionObservable(questionService);

        // 4. Capa de Presentación - Controlador
        QuestionController controller = new QuestionController(questionService, questionObservable);

        // 5. Capa de Presentación - Vistas
        MainView mainView = new MainView(controller);
        StatisticsView statisticsView = new StatisticsView(questionObservable);
        PieChartView pieChartView = new PieChartView(questionObservable);

        // 6. Configurar la ventana principal para mostrar todas las vistas
        // MainView ya tiene un BorderLayout, así que solo agregamos al lado EAST
        
        // Reorganizamos la ventana principal para alojar los observadores a los lados
        javax.swing.JPanel observersPanel = new javax.swing.JPanel();
        observersPanel.setLayout(new java.awt.GridLayout(2, 1, 10, 10));
        observersPanel.add(statisticsView);
        observersPanel.add(pieChartView);
        
        mainView.add(observersPanel, java.awt.BorderLayout.EAST);
        
        // Ajustar tamaño para que quepan todos los componentes
        mainView.pack();
        mainView.setLocationRelativeTo(null); // Centrar en pantalla
        mainView.setVisible(true);
    }

    /**
     * Obtiene la ruta base donde está corriendo la aplicación.
     */
    private static String getBaseFilePath() {
        try {
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = URLDecoder.decode(path, "UTF-8");
            File pathFile = new File(path);
            if (pathFile.isFile()) {
                path = pathFile.getParent();
                if (!path.endsWith(File.separator)) {
                    path += File.separator;
                }
            }
            return path;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error en ruta base", ex);
            return null;
        }
    }
}
