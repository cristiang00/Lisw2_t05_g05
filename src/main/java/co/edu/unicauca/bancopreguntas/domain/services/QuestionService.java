package co.edu.unicauca.bancopreguntas.domain.services;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.repositories.IQuestionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio de dominio que encapsula la lógica de negocio para las preguntas.
 */
public class QuestionService {
    
    private final IQuestionRepository repository;

    /**
     * Constructor que inyecta la dependencia del repositorio (DIP de SOLID).
     * @param repository Implementación del repositorio.
     */
    public QuestionService(IQuestionRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene la lista completa de preguntas.
     * @return Lista de preguntas.
     */
    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    /**
     * Obtiene una pregunta por su identificador.
     * @param id Identificador de la pregunta.
     * @return La pregunta encontrada.
     */
    public Question getQuestionById(String id) {
        return repository.findById(id);
    }

    /**
     * Cambia el estado de una pregunta y la actualiza en el repositorio.
     * @param questionId Identificador de la pregunta.
     * @param newState Nuevo estado a asignar.
     */
    public void changeState(String questionId, QuestionState newState) {
        Question question = repository.findById(questionId);
        if (question != null && newState != null) {
            question.setState(newState);
            repository.update(question);
        }
    }

    /**
     * Calcula las estadísticas actuales contando la cantidad de preguntas por cada estado.
     * @return Un mapa con el estado como clave y la cantidad como valor.
     */
    public Map<QuestionState, Long> getStatistics() {
        List<Question> allQuestions = repository.findAll();
        Map<QuestionState, Long> stats = new HashMap<>();
        
        // Inicializar el mapa con todos los estados en 0
        for (QuestionState state : QuestionState.values()) {
            stats.put(state, 0L);
        }
        
        // Contar las preguntas por estado
        for (Question q : allQuestions) {
            if (q.getState() != null) {
                stats.put(q.getState(), stats.get(q.getState()) + 1);
            }
        }
        
        return stats;
    }

    /**
     * Guarda una nueva pregunta en el repositorio.
     * @param question Pregunta a guardar.
     */
    public void addQuestion(Question question) {
        repository.save(question);
    }
}
