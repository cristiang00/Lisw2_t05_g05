package co.edu.unicauca.bancopreguntas.domain.entities;

import java.util.List;

/**
 * Entidad que representa una pregunta en el banco de preguntas.
 */
public class Question {
    private String id;
    private String name;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private QuestionState state;
    private String type;
    private String competency;
    private String difficultyLevel;
    private String category;

    public Question(String id, String name, String questionText, List<String> options, String correctAnswer, QuestionState state, String type) {
        this.id = id;
        this.name = name;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.state = state;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionState getState() {
        return state;
    }

    public void setState(QuestionState state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompetency() {
        return competency;
    }

    public void setCompetency(String competency) {
        this.competency = competency;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
