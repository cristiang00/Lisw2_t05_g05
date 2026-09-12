package co.edu.unicauca.bancopreguntas.pipeline;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;

import java.util.List;

/**
 * Filtro 4: Valida que la respuesta correcta sea válida.
 */
public class CorrectAnswerValidationFilter implements QuestionFilter {

    @Override
    public Question process(Question input) {
        System.out.println("Filtro 4: CorrectAnswerValidationFilter");

        String correctAnswer = input.getCorrectAnswer();
        if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe existir una respuesta correcta");
        }

        List<String> options = input.getOptions();
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("No hay opciones para validar la respuesta correcta");
        }

        // Validar si correctAnswer está explícitamente en la lista
        boolean exists = false;
        for (String option : options) {
            if (option.trim().equalsIgnoreCase(correctAnswer.trim()) || 
                option.trim().startsWith(correctAnswer.trim() + ".") || 
                option.trim().startsWith(correctAnswer.trim() + ")")) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            throw new IllegalArgumentException("La respuesta correcta (" + correctAnswer + ") no coincide con ninguna de las opciones dadas");
        }

        return input;
    }
}
