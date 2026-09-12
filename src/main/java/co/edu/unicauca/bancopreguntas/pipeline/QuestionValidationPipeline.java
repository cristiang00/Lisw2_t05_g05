package co.edu.unicauca.bancopreguntas.pipeline;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;

import java.util.List;

/**
 * Tubería que conecta y ejecuta secuencialmente los filtros de validación de preguntas.
 */
public class QuestionValidationPipeline {

    private final List<QuestionFilter> filters;

    public QuestionValidationPipeline(List<QuestionFilter> filters) {
        this.filters = filters;
    }

    /**
     * Ejecuta el pipeline sobre la pregunta de entrada.
     * @param input Pregunta inicial.
     * @return Pregunta procesada al final del pipeline.
     */
    public Question execute(Question input) {
        Question result = input;

        for (QuestionFilter filter : filters) {
            result = filter.process(result);
        }

        return result;
    }
}
