package co.edu.unicauca.bancopreguntas.pipeline;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;

/**
 * Filtro 1: Valida el contenido de la pregunta (no vacío, longitud mínima, etc.)
 */
public class ContentValidationFilter implements QuestionFilter {

    @Override
    public Question process(Question input) {
        System.out.println("Filtro 1: ContentValidationFilter");
        
        if (input.getQuestionText() == null || input.getQuestionText().trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido de la pregunta no puede estar vacío");
        }

        if (input.getQuestionText().length() < 10) {
            throw new IllegalArgumentException("La pregunta debe tener una longitud mínima de 10 caracteres");
        }
        
        // Simulación de formato correcto (termina con signo de interrogación)
        // Opcional, pero se agrega como ejemplo de validación de formato
        if (!input.getQuestionText().trim().endsWith("?")) {
            System.out.println("Advertencia: La pregunta debería terminar con un signo de interrogación.");
        }

        return input;
    }
}
