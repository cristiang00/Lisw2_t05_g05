package co.edu.unicauca.bancopreguntas.plugins;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.interfaces.QuestionPlugin;
import co.edu.unicauca.bancopreguntas.pipeline.ClassificationFilter;
import co.edu.unicauca.bancopreguntas.pipeline.ContentValidationFilter;
import co.edu.unicauca.bancopreguntas.pipeline.CorrectAnswerValidationFilter;
import co.edu.unicauca.bancopreguntas.pipeline.OptionsValidationFilter;
import co.edu.unicauca.bancopreguntas.pipeline.QuestionValidationPipeline;

import java.util.Arrays;
import java.util.UUID;

/**
 * Plugin generador de preguntas de selección múltiple.
 * Implementa el patrón Tuberías y Filtros para la validación.
 */
public class MultipleChoiceQuestionPlugin implements QuestionPlugin {

    private final QuestionValidationPipeline pipeline;

    public MultipleChoiceQuestionPlugin() {
        // Inicializar el pipeline de validación para selección múltiple
        pipeline = new QuestionValidationPipeline(Arrays.asList(
                new ContentValidationFilter(),
                new OptionsValidationFilter(),
                new ClassificationFilter(),
                new CorrectAnswerValidationFilter()
        ));
    }

    @Override
    public String getName() {
        return "multiple-choice";
    }

    @Override
    public boolean supports(String type) {
        return "MULTIPLE_CHOICE".equalsIgnoreCase(type);
    }

    @Override
    public Question generate(QuestionRequest request) {
        System.out.println("Generando pregunta de selección múltiple...");

        // Construir el objeto Question inicial
        Question question = new Question(
                "P-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(),
                request.getTitle(),
                request.getContent(),
                request.getOptions(),
                request.getCorrectAnswer(),
                QuestionState.BORRADOR,
                request.getType()
        );

        try {
            // Ejecutar el pipeline de validación
            Question validatedQuestion = pipeline.execute(question);
            System.out.println("Pregunta de selección múltiple generada y validada exitosamente.");
            return validatedQuestion;
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación en la generación de pregunta: " + e.getMessage());
            return null; // O relanzar la excepción dependiendo del manejo de errores deseado
        }
    }
}
