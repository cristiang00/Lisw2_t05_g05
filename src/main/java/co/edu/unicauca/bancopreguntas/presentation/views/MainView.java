package co.edu.unicauca.bancopreguntas.presentation.views;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.presentation.controllers.QuestionController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Vista principal de la aplicación.
 * Permite seleccionar una pregunta y cambiar su estado.
 */
public class MainView extends JFrame {

    private final QuestionController controller;

    private JComboBox<Question> questionComboBox;
    private JTextField txtId;
    private JTextField txtName;
    private JTextArea txtQuestion;
    private JTextArea txtOptions;
    private JTextField txtCorrectAnswer;
    private JTextField txtCurrentState;
    private JComboBox<QuestionState> stateComboBox;
    private JButton btnLoad;
    private JButton btnUpdate;

    public MainView(QuestionController controller) {
        this.controller = controller;
        initComponents();
        loadInitialData();
    }

    private void initComponents() {
        setTitle("Banco de Preguntas Saber PRO");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 700);
        setLayout(new BorderLayout(10, 10));

        // Panel superior que contiene Selección y Generación
        JPanel pnlTop = new JPanel(new GridLayout(2, 1, 5, 5));
        
        // --- SECCIÓN: Generación de Preguntas ---
        JPanel pnlGeneration = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlGeneration.setBorder(BorderFactory.createTitledBorder("Generar Pregunta (Microkernel)"));
        
        JComboBox<String> cmbPluginType = new JComboBox<>(new String[]{"MULTIPLE_CHOICE", "CASE_BASED", "MULTIMEDIA"});
        JButton btnGenerate = new JButton("Generar nueva");
        
        pnlGeneration.add(new JLabel("Tipo de Plugin:"));
        pnlGeneration.add(cmbPluginType);
        pnlGeneration.add(btnGenerate);
        
        pnlTop.add(pnlGeneration);

        // --- SECCIÓN: Selección de Pregunta ---
        JPanel pnlSelection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSelection.setBorder(BorderFactory.createTitledBorder("Seleccionar Pregunta Existente"));
        
        questionComboBox = new JComboBox<>();
        btnLoad = new JButton("Cargar pregunta");
        
        pnlSelection.add(new JLabel("Pregunta:"));
        pnlSelection.add(questionComboBox);
        pnlSelection.add(btnLoad);

        pnlTop.add(pnlSelection);
        add(pnlTop, BorderLayout.NORTH);

        // Panel central: Formulario
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Formulario de Pregunta"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        pnlForm.add(new JLabel("Id:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtId = new JTextField(20);
        txtId.setEditable(false);
        pnlForm.add(txtId, gbc);

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        pnlForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtName = new JTextField(20);
        txtName.setEditable(false);
        pnlForm.add(txtName, gbc);

        // Fila 2
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlForm.add(new JLabel("Pregunta:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 0.4;
        txtQuestion = new JTextArea(3, 20);
        txtQuestion.setEditable(false);
        txtQuestion.setLineWrap(true);
        JScrollPane spQuestion = new JScrollPane(txtQuestion);
        spQuestion.setMinimumSize(new Dimension(200, 60));
        spQuestion.setPreferredSize(new Dimension(250, 70));
        pnlForm.add(spQuestion, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 3
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlForm.add(new JLabel("Opciones:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 0.6;
        txtOptions = new JTextArea(4, 20);
        txtOptions.setEditable(false);
        JScrollPane spOptions = new JScrollPane(txtOptions);
        spOptions.setMinimumSize(new Dimension(200, 80));
        spOptions.setPreferredSize(new Dimension(250, 90));
        pnlForm.add(spOptions, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 4
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        pnlForm.add(new JLabel("Respuesta Correcta:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCorrectAnswer = new JTextField(20);
        txtCorrectAnswer.setEditable(false);
        pnlForm.add(txtCorrectAnswer, gbc);

        // Fila 5
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        pnlForm.add(new JLabel("Estado actual:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtCurrentState = new JTextField(20);
        txtCurrentState.setEditable(false);
        pnlForm.add(txtCurrentState, gbc);

        // Fila 6
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0;
        pnlForm.add(new JLabel("Nuevo estado:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        stateComboBox = new JComboBox<>(QuestionState.values());
        stateComboBox.setEnabled(false);
        pnlForm.add(stateComboBox, gbc);

        add(pnlForm, BorderLayout.CENTER);

        // Panel inferior: Botón actualizar
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnUpdate = new JButton("Actualizar estado");
        btnUpdate.setEnabled(false);
        pnlBottom.add(btnUpdate);

        add(pnlBottom, BorderLayout.SOUTH);

        // Eventos
        btnLoad.addActionListener(e -> loadQuestionDetails());
        btnUpdate.addActionListener(e -> updateQuestionState());
        btnGenerate.addActionListener(e -> handleGenerateQuestion((String) cmbPluginType.getSelectedItem()));
    }

    private void handleGenerateQuestion(String pluginType) {
        try {
            // Simulamos datos capturados desde una ventana de diálogo o inputs
            String title = JOptionPane.showInputDialog(this, "Ingrese el título de la pregunta:");
            if (title == null || title.trim().isEmpty()) return;

            String content = JOptionPane.showInputDialog(this, "Ingrese el contenido de la pregunta:");
            if (content == null || content.trim().isEmpty()) return;

            String optionsStr = JOptionPane.showInputDialog(this, "Ingrese opciones separadas por coma (A,B,C,D):", "A,B,C,D");
            java.util.List<String> options = (optionsStr != null) ? java.util.Arrays.asList(optionsStr.split(",")) : new java.util.ArrayList<>();

            String correctAnswer = JOptionPane.showInputDialog(this, "Ingrese la respuesta correcta:");
            
            String additionalData = "";
            if ("MULTIMEDIA".equals(pluginType)) {
                additionalData = JOptionPane.showInputDialog(this, "Ingrese URL del recurso multimedia:");
            }

            co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest request = 
                new co.edu.unicauca.bancopreguntas.domain.entities.QuestionRequest(
                    title, content, pluginType, options, correctAnswer
                );
            request.setAdditionalData(additionalData);

            Question generated = controller.generateQuestion(request);

            if (generated != null) {
                JOptionPane.showMessageDialog(this, "Pregunta generada con éxito:\n" + generated.getId(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Recargar el combo box para mostrar la nueva
                questionComboBox.removeAllItems();
                loadInitialData();
                questionComboBox.setSelectedItem(generated);
                loadQuestionDetails();
            } else {
                JOptionPane.showMessageDialog(this, "Fallo al generar la pregunta. Revise las validaciones.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadInitialData() {
        List<Question> questions = controller.loadQuestions();
        for (Question q : questions) {
            questionComboBox.addItem(q);
        }
    }

    private void loadQuestionDetails() {
        Question selected = (Question) questionComboBox.getSelectedItem();
        if (selected != null) {
            // Obtenemos la última versión de la pregunta desde el controlador
            Question q = controller.getQuestion(selected.getId());
            
            txtId.setText(q.getId());
            txtName.setText(q.getName());
            txtQuestion.setText(q.getQuestionText());
            
            StringBuilder opts = new StringBuilder();
            for (String opt : q.getOptions()) {
                opts.append(opt).append("\n");
            }
            txtOptions.setText(opts.toString());
            
            txtCorrectAnswer.setText(q.getCorrectAnswer());
            txtCurrentState.setText(q.getState().getLabel());
            
            stateComboBox.setSelectedItem(q.getState());
            
            stateComboBox.setEnabled(true);
            btnUpdate.setEnabled(true);
        }
    }

    private void updateQuestionState() {
        Question selected = (Question) questionComboBox.getSelectedItem();
        QuestionState newState = (QuestionState) stateComboBox.getSelectedItem();
        
        if (selected != null && newState != null) {
            controller.changeQuestionState(selected.getId(), newState);
            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Recargar detalles para reflejar el cambio en la vista principal
            loadQuestionDetails();
        }
    }
}
