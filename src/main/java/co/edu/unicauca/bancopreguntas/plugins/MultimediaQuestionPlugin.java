package co.edu.unicauca.bancopreguntas.plugins;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.interfaces.QuestionPlugin;

import java.util.UUID;

/**
 * Plugin generador de preguntas con recursos multimedia.
 */
public class MultimediaQuestionPlugin implements QuestionPlugin {

    @Override
    public String getName() {
        return "multimedia";
    }

    @Override
    public boolean supports(String type) {
        return "MULTIMEDIA".equalsIgnoreCase(type);
    }

    @Override
    public Question generate(QuestionRequest request) {
        System.out.println("Generando pregunta multimedia...");

        // Validaciones básicas propias de este plugin
        if (request.getAdditionalData() == null || request.getAdditionalData().isEmpty()) {
            System.err.println("Se requiere una URL o recurso multimedia en 'additionalData'.");
            return null;
        }

        // Construir el texto enriquecido
        String richContent = request.getContent() + "\n\n[Recurso: " + request.getAdditionalData() + "]";

        // Construir el objeto Question
        Question question = new Question(
                "MM-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(),
                request.getTitle(),
                richContent,
                request.getOptions(),
                request.getCorrectAnswer(),
                QuestionState.BORRADOR,
                request.getType()
        );
        
        // Asignaciones por defecto
        question.setCategory("Multimedia");
        question.setCompetency("Comprensión Audiovisual");
        question.setDifficultyLevel("Medio");

        return question;
    }
}
