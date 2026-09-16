package co.edu.unicauca.bancopreguntas.domain.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    // ==================== Tests de creación ====================

    @Test
    @DisplayName("Crear pregunta con todos los atributos válidos")
    void testQuestionCreation() {
        String id = "P-100";
        String name = "Test Name";
        String text = "Test Question?";
        List<String> options = Arrays.asList("A", "B", "C", "D");
        String correctAnswer = "A";
        QuestionState state = QuestionState.BORRADOR;

        Question question = new Question(id, name, text, options, correctAnswer, state, "MULTIPLE_CHOICE");

        assertEquals(id, question.getId());
        assertEquals(name, question.getName());
        assertEquals(text, question.getQuestionText());
        assertEquals(options, question.getOptions());
        assertEquals(correctAnswer, question.getCorrectAnswer());
        assertEquals(state, question.getState());
    }

    @Test
    @DisplayName("Crear pregunta con lista de opciones vacía")
    void testQuestionCreationWithEmptyOptions() {
        Question question = new Question("P-400", "Nombre", "Texto", Collections.emptyList(), "A", QuestionState.BORRADOR, "MULTIPLE_CHOICE");
        assertNotNull(question.getOptions());
        assertTrue(question.getOptions().isEmpty());
    }

    // ==================== Tests de setters ====================

    @Test
    @DisplayName("Modificar el nombre de una pregunta con setName()")
    void testSetName() {
        Question question = new Question("P-100", "Original", "Text", null, "A", QuestionState.BORRADOR, "MULTIPLE_CHOICE");
        question.setName("Nuevo Nombre");
        assertEquals("Nuevo Nombre", question.getName());
    }

    @Test
    @DisplayName("Modificar las opciones de una pregunta con setOptions()")
    void testSetOptions() {
        Question question = new Question("P-100", "Name", "Text", Arrays.asList("A", "B"), "A", QuestionState.BORRADOR, "MULTIPLE_CHOICE");
        List<String> newOptions = Arrays.asList("X", "Y", "Z");
        question.setOptions(newOptions);
        assertEquals(newOptions, question.getOptions());
        assertEquals(3, question.getOptions().size());
    }

    // ==================== Tests de cambio de estado ====================

    @Test
    @DisplayName("Cambiar estado de BORRADOR a PENDIENTE_REVISION")
    void testChangeStateBorradorToPendiente() {
        Question question = new Question("P-100", "Name", "Text", null, "A", QuestionState.BORRADOR, "MULTIPLE_CHOICE");
        question.setState(QuestionState.PENDIENTE_REVISION);
        assertEquals(QuestionState.PENDIENTE_REVISION, question.getState());
    }

    @Test
    @DisplayName("Cambiar estado de PENDIENTE_REVISION a ELIMINADA")
    void testChangeStatePendienteToEliminada() {
        Question question = new Question("P-100", "Name", "Text", null, "A", QuestionState.PENDIENTE_REVISION, "MULTIPLE_CHOICE");
        question.setState(QuestionState.ELIMINADA);
        assertEquals(QuestionState.ELIMINADA, question.getState());
    }

    @Test
    @DisplayName("Cambiar estado de ELIMINADA a BORRADOR")
    void testChangeStateEliminadaToBorrador() {
        Question question = new Question("P-100", "Name", "Text", null, "A", QuestionState.ELIMINADA, "MULTIPLE_CHOICE");
        question.setState(QuestionState.BORRADOR);
        assertEquals(QuestionState.BORRADOR, question.getState());
    }

    // ==================== Tests del enum QuestionState ====================

    @Test
    @DisplayName("Verificar los labels de todos los estados del enum")
    void testQuestionStateEnumLabels() {
        assertEquals("Borrador", QuestionState.BORRADOR.getLabel());
        assertEquals("Pendiente de revisión", QuestionState.PENDIENTE_REVISION.getLabel());
        assertEquals("Eliminada", QuestionState.ELIMINADA.getLabel());
    }

    @Test
    @DisplayName("Verificar que existen exactamente 3 estados en el enum")
    void testQuestionStateEnumCount() {
        assertEquals(3, QuestionState.values().length);
    }

    // ==================== Tests de toString ====================

    @Test
    @DisplayName("Verificar formato de toString(): 'id - name'")
    void testQuestionToString() {
        Question question = new Question("P-100", "Pregunta DDD", "Texto", null, "A", QuestionState.BORRADOR, "MULTIPLE_CHOICE");
        assertEquals("P-100 - Pregunta DDD", question.toString());
    }
}
