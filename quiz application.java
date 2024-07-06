package gui.java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class QuizApplication extends JFrame {

    private JPanel usernamePanel, passwordPanel, levelPanel, questionPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> levelComboBox;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel timerLabel;
    private Timer quizTimer;
    private int timeLeft = 60; // 60 seconds

    public QuizApplication() {
        setTitle("Quiz Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        setUsernamePanel();
        setPasswordPanel();
        setLevelPanel();
        setQuestionPanel();
        setTimerPanel();

        // Add panels to frame
        add(usernamePanel, BorderLayout.NORTH);
        add(passwordPanel, BorderLayout.CENTER);
        add(levelPanel, BorderLayout.SOUTH);

        pack(); // Adjust frame size based on components
        setLocationRelativeTo(null); // Center frame on screen
        setVisible(true);
    }

    private void setUsernamePanel() {
        usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        usernamePanel.add(usernameField);
    }

    private void setPasswordPanel() {
        passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        passwordPanel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        passwordPanel.add(loginButton);
    }

    private void setLevelPanel() {
        levelPanel = new JPanel();
        levelPanel.setVisible(false); // Hide initially
        levelPanel.add(new JLabel("Select Level:"));
        levelComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        JButton levelButton = new JButton("Start Quiz");
        levelButton.addActionListener(new LevelButtonListener());
        levelPanel.add(levelComboBox);
        levelPanel.add(levelButton);
    }

    private void setQuestionPanel() {
        questionPanel = new JPanel();
        questionPanel.setVisible(false); // Hide initially
    }

    private void setTimerPanel() {
        JPanel timerPanel = new JPanel();
        timerLabel = new JLabel("Time Left: " + timeLeft + " sec");
        timerPanel.add(timerLabel);
        add(timerPanel, BorderLayout.WEST);
    }

    private class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("password")) {
                usernamePanel.setVisible(false);
                passwordPanel.setVisible(false);
                levelPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials");
            }
        }
    }

    private class LevelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedLevel = (String) levelComboBox.getSelectedItem();
            questions = new ArrayList<>();

            // Add sample questions (replace with your actual questions)
            questions.add(new Question("javafx is a GUI based application",
                    "True", "False", "none of these", "True", "True", selectedLevel));
            questions.add(new Question("How many primitive data types are there in Java?",
                    "8", "6", "7", "9", "7", selectedLevel));
            questions.add(new Question("char takes only one character",
                    "yes", "no", "both", "maybe", "yes", selectedLevel));
            questions.add(new Question("C is a procedural language",
                    "yes", "no", "yeah, of course", "maybe", "yes", selectedLevel));
            questions.add(new Question("Is Java hard",
                    "Yes", "no", "maybe", "definitely yes", "no", selectedLevel));
            questions.add(new Question("Which is best: JavaFX or Swing",
                    "both", "Swing", "JavaFX", "none of these", "both", selectedLevel));
            questions.add(new Question("Who is the inventor of Java?",
                    "James Gosling", "Richard Stallman", "Guido van Rossum", "Vinushan", "James Gosling", selectedLevel));
            questions.add(new Question("Java is developed by",
                    "Oracle", "Sun", "both", "none", "both", selectedLevel));
            questions.add(new Question("Java is case-sensitive",
                    "yes", "no", "partially", "totally", "yes", selectedLevel));
            questions.add(new Question("What is a numerical data type",
                    "int", "String", "Boolean", "char", "int", selectedLevel));
            questions.add(new Question("Who invented Python?",
                    "Guido van Rossum", "Kailash", "Richard Stallman", "Vikash", "Guido van Rossum", selectedLevel));
            questions.add(new Question("Java is platform independent. (True/False)",
                    "True", "False", "none", "both", "True", selectedLevel));

            // Start timer
            startTimer();

            // Show first question panel
            currentQuestionIndex = 0;
            displayQuestion();
        }
    }

    private void startTimer() {
        quizTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time Left: " + timeLeft + " sec");

                if (timeLeft == 0) {
                    quizTimer.stop();
                    JOptionPane.showMessageDialog(null, "Time's up! Quiz completed. Your score is: " + score);
                    resetQuiz();
                }
            }
        });
        quizTimer.start();
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionPanel.removeAll();
            questionPanel.setLayout(new GridLayout(6, 1));
            questionPanel.add(new JLabel("Question " + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestion()));
            questionPanel.add(new JRadioButton(currentQuestion.getOptionA()));
            questionPanel.add(new JRadioButton(currentQuestion.getOptionB()));
            questionPanel.add(new JRadioButton(currentQuestion.getOptionC()));
            questionPanel.add(new JRadioButton(currentQuestion.getOptionD()));

            JButton answerButton = new JButton("Answer");
            answerButton.addActionListener(new AnswerButtonListener());
            questionPanel.add(answerButton);

            add(questionPanel, BorderLayout.CENTER);
            validate(); // Refresh frame
            questionPanel.setVisible(true);
        } else {
            // End of quiz, show score or summary
            quizTimer.stop();
            JOptionPane.showMessageDialog(null, "Quiz completed! Your score is: " + score);
            resetQuiz();
        }
    }

    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        timeLeft = 60;
        timerLabel.setText("Time Left: " + timeLeft + " sec");
        usernamePanel.setVisible(true);
        passwordPanel.setVisible(true);
        levelPanel.setVisible(false);
        questionPanel.setVisible(false);
    }

    private class AnswerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            String selectedAnswer = getSelectedAnswer();

            if (selectedAnswer != null) {
                if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                    score += 5; // Increment score for correct answer
                    JOptionPane.showMessageDialog(null, "Correct!");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect! The correct answer is: " + currentQuestion.getCorrectAnswer());
                }

                // Move to next question
                currentQuestionIndex++;
                displayQuestion();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an answer!");
            }
        }
    }

    private String getSelectedAnswer() {
        for (Component c : questionPanel.getComponents()) {
            if (c instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) c;
                if (radioButton.isSelected()) {
                    return radioButton.getText();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Ensure to run on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuizApplication();
            }
        });
    }

    // Question class definition
    private class Question {
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;
        private String level;

        public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer, String level) {
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
            this.level = level;
        }

        public String getQuestion() {
            return question;
        }

        public String getOptionA() {
            return optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        @SuppressWarnings("unused")
		public String getLevel() {
            return level;
        }
    }
}