package co.edu.unicauca.bancopreguntas.dataaccess;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.repositories.IQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionImplRepositoryTest {

    private IQuestionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new QuestionImplRepository();
    }

    // ==================== Tests de findAll ====================

    @Test
    @DisplayName("findAll() retorna las 5 preguntas precargadas")
    void testFindAllReturnsAllQuestions() {
        List<Question> questions = repository.findAll();
        assertNotNull(questions);
        assertEquals(5, questions.size());
    }

    @Test
    @DisplayName("findAll() retorna una nueva lista (no la referencia interna)")
    void testFindAllReturnsNewList() {
        List<Question> list1 = repository.findAll();
        List<Question> list2 = repository.findAll();
        assertNotSame(list1, list2, "findAll() debe retornar una nueva lista cada vez");
    }

    // ==================== Tests de findById ====================

    @Test
    @DisplayName("findById() con ID existente retorna la pregunta correcta")
    void testFindByIdExists() {
        Question q = repository.findById("P-001");
        assertNotNull(q);
        assertEquals("P-001", q.getId());
        assertEquals("Pregunta sobre DDD", q.getName());
    }

    @Test
    @DisplayName("findById() con ID inexistente retorna null")
    void testFindByIdNotExists() {
        assertNull(repository.findById("P-999"));
    }

    // ==================== Tests de update ====================

    @Test
    @DisplayName("update() modifica el estado de una pregunta existente")
    void testUpdateExistingQuestion() {
        Question q = repository.findById("P-001");
        q.setState(QuestionState.ELIMINADA);
        repository.update(q);

        Question updated = repository.findById("P-001");
        assertEquals(QuestionState.ELIMINADA, updated.getState());
    }

    @Test
    @DisplayName("update() con pregunta de ID inexistente no modifica nada")
    void testUpdateNonExistentQuestion() {
        Question fake = new Question("P-999", "Fake", "Fake?",
            Arrays.asList("A", "B"), "A", QuestionState.BORRADOR, "MULTIPLE_CHOICE");

        assertDoesNotThrow(() -> repository.update(fake));
        assertNull(repository.findById("P-999"));
        assertEquals(5, repository.findAll().size());
    }

    // ==================== Tests de datos iniciales ====================

    @Test
    @DisplayName("Los estados iniciales de las preguntas son correctos")
    void testInitialStates() {
        assertEquals(QuestionState.BORRADOR, repository.findById("P-001").getState());
        assertEquals(QuestionState.PENDIENTE_REVISION, repository.findById("P-002").getState());
        assertEquals(QuestionState.BORRADOR, repository.findById("P-003").getState());
        assertEquals(QuestionState.ELIMINADA, repository.findById("P-004").getState());
        assertEquals(QuestionState.PENDIENTE_REVISION, repository.findById("P-005").getState());
    }

    @Test
    @DisplayName("Cada pregunta precargada tiene 4 opciones")
    void testAllQuestionsHaveOptions() {
        List<Question> questions = repository.findAll();
        for (Question q : questions) {
            assertNotNull(q.getOptions(), "La pregunta " + q.getId() + " debe tener opciones");
            assertEquals(4, q.getOptions().size(), "La pregunta " + q.getId() + " debe tener 4 opciones");
        }
    }
}
