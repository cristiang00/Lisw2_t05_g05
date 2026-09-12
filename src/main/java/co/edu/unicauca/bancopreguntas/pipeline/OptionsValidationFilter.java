package co.edu.unicauca.bancopreguntas.pipeline;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filtro 2: Valida que las opciones sean correctas.
 */
public class OptionsValidationFilter implements QuestionFilter {

    @Override
    public Question process(Question input) {
        System.out.println("Filtro 2: OptionsValidationFilter");

        List<String> options = input.getOptions();

        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("La pregunta debe tener un mínimo de 2 opciones");
        }

        Set<String> uniqueOptions = new HashSet<>();
        for (String option : options) {
            if (option == null || option.trim().isEmpty()) {
                throw new IllegalArgumentException("No se permiten opciones vacías");
            }
            if (!uniqueOptions.add(option.trim())) {
                throw new IllegalArgumentException("No se permiten opciones duplicadas: " + option);
            }
        }

        return input;
    }
}
