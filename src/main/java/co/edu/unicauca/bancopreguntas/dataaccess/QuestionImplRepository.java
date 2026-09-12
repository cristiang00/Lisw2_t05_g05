package co.edu.unicauca.bancopreguntas.dataaccess;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.repositories.IQuestionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * Implementación del repositorio de preguntas en memoria.
 * Simula el acceso a una base de datos utilizando una estructura de datos Map.
 */
public class QuestionImplRepository implements IQuestionRepository {

    private final Map<String, Question> questionsDB;

    public QuestionImplRepository() {
        this.questionsDB = new HashMap<>();
        initializeData();
    }

    private void initializeData() {
        // Pregunta 1
        Question q1 = new Question(
            "P-001",
            "Pregunta sobre DDD",
            "¿Cuál es el objetivo principal de DDD?",
            Arrays.asList(
                "A. Diseñar bases de datos",
                "B. Modelar el dominio del negocio",
                "C. Eliminar UML",
                "D. Crear interfaces gráficas"
            ),
            "B",
            QuestionState.BORRADOR,
            "MULTIPLE_CHOICE"
        );

        // Pregunta 2
        Question q2 = new Question(
            "P-002",
            "Patrones de diseño Creacionales",
            "¿Cuál de los siguientes es un patrón creacional?",
            Arrays.asList(
                "A. Singleton",
                "B. Observer",
                "C. Strategy",
                "D. Decorator"
            ),
            "A",
            QuestionState.PENDIENTE_REVISION,
            "MULTIPLE_CHOICE"
        );

        // Pregunta 3
        Question q3 = new Question(
            "P-003",
            "Principios SOLID",
            "¿Qué significa la 'S' en SOLID?",
            Arrays.asList(
                "A. Simple Responsibility Principle",
                "B. Single Responsibility Principle",
                "C. Solid Responsibility Principle",
                "D. Standard Responsibility Principle"
            ),
            "B",
            QuestionState.BORRADOR,
            "MULTIPLE_CHOICE"
        );

        // Pregunta 4
        Question q4 = new Question(
            "P-004",
            "Arquitectura MVC",
            "En el patrón MVC, ¿qué capa gestiona las interacciones del usuario?",
            Arrays.asList(
                "A. Modelo",
                "B. Vista",
                "C. Controlador",
                "D. Base de datos"
            ),
            "C",
            QuestionState.ELIMINADA,
            "MULTIPLE_CHOICE"
        );

        // Pregunta 5
        Question q5 = new Question(
            "P-005",
            "Pruebas Unitarias",
            "¿Cuál es el propósito principal de una prueba unitaria?",
            Arrays.asList(
                "A. Probar la integración de múltiples componentes",
                "B. Verificar el rendimiento del sistema",
                "C. Validar que una unidad de código individual funciona correctamente",
                "D. Probar la interfaz gráfica de usuario"
            ),
            "C",
            QuestionState.PENDIENTE_REVISION,
            "MULTIPLE_CHOICE"
        );

        questionsDB.put(q1.getId(), q1);
        questionsDB.put(q2.getId(), q2);
        questionsDB.put(q3.getId(), q3);
        questionsDB.put(q4.getId(), q4);
        questionsDB.put(q5.getId(), q5);
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(questionsDB.values());
    }

    @Override
    public Question findById(String id) {
        return questionsDB.get(id);
    }

    @Override
    public void save(Question question) {
        if (question != null && question.getId() != null) {
            questionsDB.put(question.getId(), question);
        }
    }

    @Override
    public void update(Question question) {
        if (questionsDB.containsKey(question.getId())) {
            questionsDB.put(question.getId(), question);
        }
    }
}
