package co.edu.unicauca.bancopreguntas.plugins;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.interfaces.QuestionPlugin;

import java.util.UUID;

/**
 * Plugin generador de preguntas basadas en casos.
 */
public class CaseBasedQuestionPlugin implements QuestionPlugin {

    @Override
    public String getName() {
        return "case-based";
    }

    @Override
    public boolean supports(String type) {
        return "CASE_BASED".equalsIgnoreCase(type);
    }

    @Override
    public Question generate(QuestionRequest request) {
        System.out.println("Generando pregunta basada en caso...");

        // Validaciones básicas propias de este plugin
        if (request.getContent() == null || request.getContent().length() < 50) {
            System.err.println("Un caso de estudio debe tener al menos 50 caracteres.");
            return null;
        }

        // Construir el objeto Question
        Question question = new Question(
                "CB-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(),
                request.getTitle(),
                request.getContent(),
                request.getOptions(),
                request.getCorrectAnswer(),
                QuestionState.BORRADOR,
                request.getType()
        );
        
        // Asignaciones por defecto
        question.setCategory("Casos de Estudio");
        question.setCompetency("Análisis y Resolución");
        question.setDifficultyLevel("Difícil");

        return question;
    }
}
