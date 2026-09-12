package co.edu.unicauca.bancopreguntas.pipeline;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;

/**
 * Filtro 3: Asigna competencia, nivel de dificultad y categoría (Clasificación).
 */
public class ClassificationFilter implements QuestionFilter {

    @Override
    public Question process(Question input) {
        System.out.println("Filtro 3: ClassificationFilter");

        // Reglas simples de clasificación basadas en el texto
        String text = input.getQuestionText().toLowerCase();

        // 1. Determina la competencia
        if (text.contains("arquitectura") || text.contains("patrón") || text.contains("patrones")) {
            input.setCompetency("Diseño de Software");
        } else if (text.contains("base de datos") || text.contains("sql")) {
            input.setCompetency("Gestión de Datos");
        } else {
            input.setCompetency("Fundamentos de Ingeniería de Software");
        }

        // 2. Asigna nivel de dificultad basado en la longitud y opciones
        if (text.length() > 100 || (input.getOptions() != null && input.getOptions().size() > 4)) {
            input.setDifficultyLevel("Difícil");
        } else if (text.length() > 50) {
            input.setDifficultyLevel("Medio");
        } else {
            input.setDifficultyLevel("Fácil");
        }

        // 3. Establece categoría
        input.setCategory("Saber PRO Específicas");

        System.out.println("Clasificación asignada: " + input.getCompetency() + " - " + input.getDifficultyLevel());

        return input;
    }
}
