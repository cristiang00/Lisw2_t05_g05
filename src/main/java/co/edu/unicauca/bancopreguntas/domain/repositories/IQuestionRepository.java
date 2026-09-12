package co.edu.unicauca.bancopreguntas.domain.repositories;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import java.util.List;

/**
 * Interfaz del repositorio de preguntas.
 * Define las operaciones básicas de acceso a datos que el dominio necesita,
 * aplicando el Principio de Inversión de Dependencias (DIP) de SOLID.
 */
public interface IQuestionRepository {
    /**
     * Obtiene todas las preguntas almacenadas en el repositorio.
     * @return Lista de todas las preguntas.
     */
    List<Question> findAll();

    /**
     * Busca una pregunta por su identificador único.
     * @param id Identificador de la pregunta.
     * @return La pregunta si existe, null en caso contrario.
     */
    Question findById(String id);

    /**
     * Guarda una nueva pregunta.
     * @param question La pregunta a guardar.
     */
    void save(Question question);

    /**
     * Actualiza la información de una pregunta existente.
     * @param question La pregunta con la información actualizada.
     */
    void update(Question question);
}
