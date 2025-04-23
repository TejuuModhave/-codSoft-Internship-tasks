import java.util.*;
import java.util.concurrent.*;

// Question class to store quiz questions, options, and correct answer
class Question {
    String questionText;
    String[] options;
    int correctAnswer; // Index of the correct option

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

// Quiz class to manage the quiz process
class Quiz {
    private List<Question> questions;
    private int score;
    private Scanner scanner;

    public Quiz() {
        this.questions = new ArrayList<>();
        this.score = 0;
        this.scanner = new Scanner(System.in);
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question("What is the capital of France?",
                new String[] { "1. Berlin", "2. Madrid", "3. Paris", "4. Rome" }, 2));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[] { "1. Earth", "2. Mars", "3. Jupiter", "4. Saturn" }, 1));
        questions.add(new Question("What is 5 + 3?", new String[] { "1. 6", "2. 7", "3. 8", "4. 9" }, 2));
    }

    public void startQuiz() {
        for (Question q : questions) {
            askQuestion(q);
        }
        showResults();
    }

    private void askQuestion(Question question) {
        System.out.println("\n" + question.questionText);
        for (String option : question.options) {
            System.out.println(option);
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            System.out.print("Your answer (Enter option number): ");
            return scanner.nextInt() - 1;
        });

        try {
            int answer = future.get(10, TimeUnit.SECONDS);
            if (answer == question.correctAnswer) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + (question.correctAnswer + 1));
            }
        } catch (TimeoutException e) {
            System.out.println("\nTime's up! Moving to the next question.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        } finally {
            executor.shutdownNow();
        }
    }

    private void showResults() {
        System.out.println("\nQuiz Over! Your score: " + score + " out of " + questions.size());
    }
}

// Main class to run the quiz
public class QuizGame {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.startQuiz();
    }
}
