package co.edu.unicauca.bancopreguntas.domain.services;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest;
import co.edu.unicauca.bancopreguntas.domain.interfaces.QuestionPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Núcleo principal de la arquitectura Microkernel.
 * Almacena el banco de preguntas, y ejecuta los plugins para la generación.
 */
public class QuestionMicrokernel {
    
    // Almacén del banco de preguntas en memoria
    private Map<String, Question> questions;

    public QuestionMicrokernel() {
        this.questions = new HashMap<>();
    }

    /**
     * Agrega una pregunta al núcleo.
     * @param question Pregunta a agregar.
     */
    public void addQuestion(Question question) {
        if (question != null && question.getId() != null) {
            this.questions.put(question.getId(), question);
        }
    }

    /**
     * @return Lista con todas las preguntas almacenadas.
     */
    public List<Question> getAllQuestions() {
        return questions.values().stream().collect(Collectors.toList());
    }

    /**
     * Utiliza el Plugin Manager para obtener el plugin adecuado según el tipo,
     * y le solicita generar la pregunta. Si es exitosa, se puede agregar al almacén.
     * 
     * @param request Datos de la pregunta a generar
     * @return La pregunta generada y validada
     * @throws Exception Si no existe plugin o falla la generación
     */
    public Question generateQuestion(QuestionRequest request) throws Exception {
        
        QuestionPluginManager manager = QuestionPluginManager.getInstance();
        QuestionPlugin plugin = manager.getPlugin(request.getType());
        
        if (plugin == null) {
            throw new Exception("No hay un plugin disponible para el tipo: " + request.getType());
        }
        
        if (!plugin.supports(request.getType())) {
            throw new Exception("El plugin " + plugin.getName() + " no soporta el tipo: " + request.getType());
        }
        
        // Ejecutar el plugin
        Question generatedQuestion = plugin.generate(request);
        
        if (generatedQuestion == null) {
            throw new Exception("La generación de la pregunta falló. Es posible que no haya pasado las validaciones del pipeline.");
        }
        
        // Almacenar en el núcleo
        addQuestion(generatedQuestion);
        
        return generatedQuestion;
    }
}
