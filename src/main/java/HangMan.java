import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HangMan {
    public static void main(String[] args) throws IOException {
        HangMan myObj = new HangMan();

        Scanner keyboard = new Scanner(System.in);
        Scanner scanner = new Scanner(new URL("https://raw.githubusercontent.com/dwyl/english-words/master/words_alpha.txt").openStream());
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next());
        }
        String word;
        boolean flag = true;

        while (flag) {
            //Random word guess from Computer(Player One)
            Random rand = new Random();
            word = words.get(rand.nextInt(words.size()));

            List<Character> playerGuesses = new ArrayList<>();
            List<Character> wordList = new ArrayList<>();
            for (int i = 0; i < word.length(); i++) {
                wordList.add(word.charAt(i));
            }

            Integer wrongCount = 0;
            System.out.println("H A N G M A N");
            myObj.printHangedMan(wrongCount);
            while (true) {
                System.out.println("Please enter a letter:");
                String letterGuess = keyboard.next().toLowerCase();
                boolean checkDuplicate = myObj.getPlayerGuess(playerGuesses, letterGuess);

                if (myObj.printWordState(word, playerGuesses)) {
                    System.out.println("You win!");
                    break;
                }
                if (checkDuplicate) {
                    System.out.println("You already entered the letter. PLease try something else.");
                }
                else if (myObj.correctGuess(wordList, letterGuess)) {
                    System.out.println("That is a Correct guess...");
                }
                else {
                    System.out.println("Nop not the correct guess...Try again..");
                    wrongCount++;
                    myObj.printHangedMan(wrongCount);
                }
                if (myObj.wrongCount(wrongCount)) {
                    System.out.println("You lose!");
                    System.out.println("The word was: " + word);
                    break;
                }
            }
            flag = myObj.playAgain(keyboard);
        }
    }

    /**
     * This function checks if the user guessed the correct letter.
     * @param wordList
     * @param letterGuess
     * @return
     */
    public boolean correctGuess(List<Character> wordList, String letterGuess){
        return wordList.contains(letterGuess.charAt(0));
    }

    /**
     * This function checks if the user has exhausted his 6 wrong tries...
     * @param wrongCount
     * @return
     */
    public boolean wrongCount(Integer wrongCount){
        if(wrongCount >= 6){
            return true;
        }
        return false;
    }

    /**
     * This function checks if the user has entered the value earlier returning it as duplicate...
     * @param playerGuesses
     * @param letterGuess
     * @return
     */
    public boolean getPlayerGuess(List<Character> playerGuesses, String letterGuess) {
        boolean checkDuplicate = playerGuesses.contains(letterGuess.charAt(0));
        playerGuesses.add(letterGuess.charAt(0));
        StringBuilder strBuild = new StringBuilder();
        for(Character guess: playerGuesses){
            strBuild.append(guess);
        }
        System.out.println();
        System.out.println("Player guesses are: " + strBuild);
        return checkDuplicate;
    }

    /**
     *  If the player guessed the correct letter the letter is displayed otherwise (_) is displayed.
     * @param word
     * @param playerGuesses
     * @return
     */
    public boolean printWordState(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));
                correctCount++;
            } else {
                System.out.print("_");
            }
        }
        System.out.println();

        return (word.length() == correctCount);
    }

    /**
     * This function prints the HangMan figure..
     * @param wrongCount
     */
    public void printHangedMan(Integer wrongCount) {
        System.out.println(" -------");
        System.out.println(" |     |");
        if (wrongCount >= 1) {
            System.out.println(" O");
        }
        if (wrongCount >= 2) {
            System.out.print("\\ ");
            if (wrongCount >= 3) {
                System.out.println("/");
            } else {
                System.out.println("");
            }
        }
        if (wrongCount >= 4) {
            System.out.println(" |");
        }
        if (wrongCount >= 5) {
            System.out.print("/ ");
            if (wrongCount >= 6) {
                System.out.println("\\");
            } else {
                System.out.println("");
            }
        }
        System.out.println("");
        System.out.println("");
    }

    /**
     * This function prompts the user to ask if he wants to play Again...
     * @param keyboard
     * @return
     */
    public boolean playAgain(Scanner keyboard) {
        System.out.println("Do you wish to play Again?");
        System.out.println("Please enter y or n");
        String wish = keyboard.next();
        if (!(wish.equals("y") || wish.equals("n"))) {
            System.out.println("Please enter y or n");
            wish = keyboard.next();
        }
        if (wish.equals("n")) {
            return false;
        }
        System.out.println();
        return true;
    }

}
