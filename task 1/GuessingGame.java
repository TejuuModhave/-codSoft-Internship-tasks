import java.util.Scanner;

public class GuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int min = 1;
        int max = 100;
        int maxAttempts = 10;
        int round = 0;
        int score = 0;
        String playAgain;

        do {
            round++;
            int targetNumber = (int)(Math.random() * (max - min + 1)) + min;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("Round " + round + ": Guess the number between " + min + " and " + max);

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                attempts++;

                if (guess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    guessedCorrectly = true;
                    score += maxAttempts - attempts + 1;  // Higher score for fewer attempts
                } else if (guess < targetNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("You've used all " + maxAttempts + " attempts. The correct number was " + targetNumber);
            }

            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.next();

        } while (playAgain.equalsIgnoreCase("yes"));

        System.out.println("Game over! Your score: " + score);
        scanner.close();
    }
}
