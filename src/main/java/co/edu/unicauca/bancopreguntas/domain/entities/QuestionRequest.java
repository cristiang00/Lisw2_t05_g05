package co.edu.unicauca.bancopreguntas.domain.entities;

import java.util.List;

/**
 * Data Transfer Object que encapsula los datos necesarios para que un plugin
 * genere una pregunta.
 */
public class QuestionRequest {
    
    private String title;
    private String content;
    private String type;
    private List<String> options;
    private String correctAnswer;
    
    // Campo adicional que puede ser útil para plugins específicos (ej. URL multimedia)
    private String additionalData;

    public QuestionRequest() {
    }

    public QuestionRequest(String title, String content, String type, List<String> options, String correctAnswer) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
