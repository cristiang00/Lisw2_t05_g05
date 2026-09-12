package co.edu.unicauca.bancopreguntas.pipeline;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;

/**
 * Interfaz base para los filtros del pipeline de validación de preguntas.
 */
public interface QuestionFilter {
    /**
     * Procesa, valida o modifica la pregunta.
     * @param input Pregunta a procesar.
     * @return Pregunta procesada.
     * @throws IllegalArgumentException si la validación falla.
     */
    Question process(Question input);
}
