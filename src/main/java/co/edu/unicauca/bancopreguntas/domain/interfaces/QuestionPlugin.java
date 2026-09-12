package co.edu.unicauca.bancopreguntas.domain.interfaces;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest;

/**
 * Contrato común para todos los plugins generadores de preguntas.
 */
public interface QuestionPlugin {
    
    /**
     * @return El nombre del plugin.
     */
    String getName();
    
    /**
     * @param type Tipo de pregunta
     * @return true si el plugin soporta la generación de este tipo de pregunta.
     */
    boolean supports(String type);
    
    /**
     * Genera una pregunta a partir de los datos solicitados.
     * @param request Datos de entrada para la generación.
     * @return Objeto Question generado, o null si la validación falla.
     */
    Question generate(QuestionRequest request);
}
