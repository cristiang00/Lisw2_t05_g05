package co.edu.unicauca.bancopreguntas.plugins;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MultipleChoiceQuestionPluginTest {

    private MultipleChoiceQuestionPlugin plugin;

    @BeforeEach
    void setUp() {
        plugin = new MultipleChoiceQuestionPlugin();
    }

    @Test
    void testGenerateValidQuestion() {
        QuestionRequest request = new QuestionRequest(
                "Patrones",
                "¿Qué patrón de diseño permite restringir la instanciación de una clase a un solo objeto?",
                "MULTIPLE_CHOICE",
                Arrays.asList("A. Factory Method", "B. Singleton", "C. Builder", "D. Prototype"),
                "B"
        );

        Question generated = plugin.generate(request);

        assertNotNull(generated);
        assertNotNull(generated.getId());
        assertTrue(generated.getId().startsWith("P-"));
        assertEquals("Diseño de Software", generated.getCompetency());
    }

    @Test
    void testGenerateInvalidQuestionReturnsNull() {
        QuestionRequest request = new QuestionRequest(
                "Invalida",
                "Corta?", // Muy corto, falla ContentValidationFilter (< 10 caracteres)
                "MULTIPLE_CHOICE",
                Arrays.asList("A", "B"),
                "A"
        );

        Question generated = plugin.generate(request);

        // Como el plugin captura la IllegalArgumentException y retorna null:
        assertNull(generated);
    }
}
