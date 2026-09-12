package co.edu.unicauca.bancopreguntas.pipeline;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QuestionValidationPipelineTest {

    private QuestionValidationPipeline pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new QuestionValidationPipeline(Arrays.asList(
                new ContentValidationFilter(),
                new OptionsValidationFilter(),
                new ClassificationFilter(),
                new CorrectAnswerValidationFilter()
        ));
    }

    @Test
    void testValidQuestionPassesPipeline() {
        Question q = new Question(
                "T-1",
                "Pregunta Valida",
                "¿Qué patrón de diseño permite agregar funcionalidades dinámicamente?",
                Arrays.asList("A. Singleton", "B. Decorator", "C. Factory"),
                "B",
                QuestionState.BORRADOR,
                "MULTIPLE_CHOICE"
        );

        Question result = pipeline.execute(q);

        assertNotNull(result);
        assertEquals("Diseño de Software", result.getCompetency()); // Por la palabra "patrón"
        assertEquals("Medio", result.getDifficultyLevel()); // Longitud > 50
    }

    @Test
    void testEmptyContentFailsPipeline() {
        Question q = new Question(
                "T-2",
                "Pregunta Inválida",
                "",
                Arrays.asList("A", "B", "C"),
                "A",
                QuestionState.BORRADOR,
                "MULTIPLE_CHOICE"
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pipeline.execute(q));
        assertEquals("El contenido de la pregunta no puede estar vacío", exception.getMessage());
    }

    @Test
    void testMissingOptionsFailsPipeline() {
        Question q = new Question(
                "T-3",
                "Pregunta Sin Opciones",
                "¿Cuál es el capital de Colombia?",
                Arrays.asList(),
                "Bogotá",
                QuestionState.BORRADOR,
                "MULTIPLE_CHOICE"
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pipeline.execute(q));
        assertEquals("La pregunta debe tener un mínimo de 2 opciones", exception.getMessage());
    }

    @Test
    void testInvalidCorrectAnswerFailsPipeline() {
        Question q = new Question(
                "T-4",
                "Pregunta Respuesta Invalida",
                "¿Cuál es el capital de Colombia?",
                Arrays.asList("Lima", "Quito"),
                "Bogotá",
                QuestionState.BORRADOR,
                "MULTIPLE_CHOICE"
        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> pipeline.execute(q));
        assertTrue(exception.getMessage().contains("no coincide con ninguna de las opciones"));
    }
}
