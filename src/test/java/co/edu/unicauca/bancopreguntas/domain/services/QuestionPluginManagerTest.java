package co.edu.unicauca.bancopreguntas.domain.services;

import co.edu.unicauca.bancopreguntas.domain.interfaces.QuestionPlugin;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class QuestionPluginManagerTest {

    @Test
    void testInitAndLoadPlugins() {
        try {
            // Inicializar con la ruta base de recursos de prueba o usar el classpath
            String basePath = new File("src/main/resources/").getAbsolutePath() + File.separator;
            QuestionPluginManager.init(basePath);

            QuestionPluginManager manager = QuestionPluginManager.getInstance();
            assertNotNull(manager);

            QuestionPlugin multipleChoicePlugin = manager.getPlugin("MULTIPLE_CHOICE");
            assertNotNull(multipleChoicePlugin);
            assertEquals("multiple-choice", multipleChoicePlugin.getName());

            QuestionPlugin caseBasedPlugin = manager.getPlugin("CASE_BASED");
            assertNotNull(caseBasedPlugin);
            assertEquals("case-based", caseBasedPlugin.getName());

            QuestionPlugin unknownPlugin = manager.getPlugin("UNKNOWN_TYPE");
            assertNull(unknownPlugin);

        } catch (Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }
}
