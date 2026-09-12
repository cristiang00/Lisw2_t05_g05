package co.edu.unicauca.bancopreguntas.domain.services;

import co.edu.unicauca.bancopreguntas.domain.interfaces.QuestionPlugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fábrica que utiliza reflexión para crear dinámicamente los plugins
 * de generación de preguntas, basado en un archivo de configuración.
 */
public class QuestionPluginManager {

    private static final String FILE_NAME = "plugins.properties";
    private static QuestionPluginManager instance;

    private Properties pluginProperties;

    private QuestionPluginManager() {
        pluginProperties = new Properties();
    }

    public static QuestionPluginManager getInstance() {
        return instance;
    }

    public static void init(String basePath) throws Exception {
        instance = new QuestionPluginManager();
        instance.loadProperties(basePath);
        if (instance.pluginProperties.isEmpty()) {
            throw new Exception("Could not initialize plugins");
        }
    }

    public QuestionPlugin getPlugin(String type) {
        String propertyName = "plugin." + type;
        if (!pluginProperties.containsKey(propertyName)) {
            return null;
        }

        QuestionPlugin plugin = null;
        String pluginClassName = pluginProperties.getProperty(propertyName);

        try {
            Class<?> pluginClass = Class.forName(pluginClassName);
            if (pluginClass != null) {
                Object pluginObject = pluginClass.getDeclaredConstructor().newInstance();
                if (pluginObject instanceof QuestionPlugin) {
                    plugin = (QuestionPlugin) pluginObject;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            Logger.getLogger("QuestionPluginManager").log(Level.SEVERE, "Error al instanciar el plugin", ex);
        }

        return plugin;
    }

    private void loadProperties(String basePath) {
        String filePath = basePath + FILE_NAME;
        try (FileInputStream stream = new FileInputStream(filePath)) {
            pluginProperties.load(stream);
        } catch (IOException ex) {
            // Intentar cargar como recurso del classpath si falla la ruta absoluta
            try {
                java.io.InputStream is = getClass().getClassLoader().getResourceAsStream(FILE_NAME);
                if (is != null) {
                    pluginProperties.load(is);
                    return;
                }
            } catch (Exception ignored) { }
            
            Logger.getLogger("QuestionPluginManager").log(Level.SEVERE, "Error al cargar las propiedades", ex);
        }
    }
}
