import java.util.Random;
import java.util.Scanner;

public class WordGame {

    private static int totalPoints,
            currentLevelPoint;
    private static int currentLevel = 1;
    private static boolean isGameEnded = false;
    private static String currentGameBoard = "";
    
    private static Random random = new Random();
    private static Scanner input = new Scanner(System.in);
    
    private static String[][] currentQuestion = {
            {"", ""}
    };

    private static String[][] fourLetters = {
            {"Something owed, such as money, goods, or services?", "debt"},
            {"An activity providing entertainment or amusement a pastime?", "game"},
            {"A programming language that produces software for multiple platforms?", "java"}
    };

    private static String[][] fiveLetters = {
            {"An international prize given each year for achievements in literature, physics, chemistry, medicine, economics, and world peace?", "nobel"},
            {"Of, relating to, or located in a city?", "urban"},
            {"The absence of war or other hostilities?", "peace"}
    };

    private static String[][] sixLetters = {
            {"A usually portable device containing a photosensitive surface that records images through a lens?", "camera"},
            {"A person whom one knows, likes, and trusts?", "friend"},
            {"a high-level programming language designed to be easy to read and simple to implement?", "python"}
    };

    private static void startRound() {

        pickRandomQuestion();
        prepareForNewLevel();

        while (!isGameEnded) {
	        System.out.println("Enter 1 for get hint, 2 for make a guess: ");
	        String givenInput = input.next();
	        
	        if (givenInput.charAt(0) == '1') {
	        	if (countDashes() > 1) {
	        		giveHint();
	        	} else {
	        		giveHint();
	        		changeLevel();
	        	}
	        } else if (givenInput.charAt(0) == '2') {
	        	System.out.println("Please enter your answer: ");
	        	givenInput = input.next();
	        	if (checkAnswer(givenInput.toLowerCase())) {
	                totalPoints = totalPoints + currentLevelPoint;
	                changeLevel();
	            } else {
	                totalPoints = totalPoints - currentLevelPoint;
	                System.out.println(totalPoints);
	                changeLevel();
	            }
	        } else {
	        	System.out.println("Invalid input!");
	        	continue;
	        }
        }
    }

    private static void pickRandomQuestion() {

        int pickedOne = random.nextInt(3);

        if (currentLevel == 1) {
            currentQuestion[0][0] = fourLetters[pickedOne][0];
            currentQuestion[0][1] = fourLetters[pickedOne][1];
        } else if (currentLevel == 2) {
            currentQuestion[0][0] = fiveLetters[pickedOne][0];
            currentQuestion[0][1] = fiveLetters[pickedOne][1];
        } else if (currentLevel == 3) {
            currentQuestion[0][0] = sixLetters[pickedOne][0];
            currentQuestion[0][1] = sixLetters[pickedOne][1];
        }
    }

    private static void prepareForNewLevel() {
        currentLevelPoint = currentQuestion[0][1].length() * 100;
        currentGameBoard = "";

        for (int i = 0; i < currentQuestion[0][1].length(); i++) {
            currentGameBoard = currentGameBoard + "-";
        }
        printGameBoard();
    }

    private static void changeGameBoard(int index) {
        currentGameBoard = currentGameBoard.substring(0, index) + currentQuestion[0][1].charAt(index) + currentGameBoard.substring(index + 1);
        printGameBoard();
    }
    
    private static void printGameBoard() {
    	System.out.println();
        System.out.println(currentQuestion[0][0]);
        System.out.print(currentGameBoard);
        System.out.println();
    }

    private static boolean checkAnswer(String input) {
    	
        if (input.equals(currentQuestion[0][1])) {
            return true;
        } else {
            return false;
        }
    }

    private static void giveHint() {

		currentLevelPoint = currentLevelPoint - 100;
        int index = random.nextInt(currentQuestion[0][1].length());

        while (currentGameBoard.charAt(index) != '-') {
            index = random.nextInt(currentQuestion[0][1].length());
        }
        changeGameBoard(index);
    }
    
    private static int countDashes() {
    	
    	int dashCount = 0;
    	
    	for (int i = 0; i < currentGameBoard.length(); i++) {
    		if (currentGameBoard.charAt(i) == '-') {
    			dashCount++;
    		}
    	}
    	return dashCount;
    }

    private static void changeLevel() {
    	
    	System.out.println("Total points: " + totalPoints);
    	
    	if (currentLevel == 3) {
    		endGame();
    	} else {
            currentLevel++;
            startRound();
    	}
    }

    private static void endGame() {
        isGameEnded = true;
        System.out.println("Game ended. Total Points: " + totalPoints);
    }

    public static void main(String[] args) {
        startRound();
    }
}
