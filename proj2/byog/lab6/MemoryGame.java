package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);

    }

    public String generateRandomString(int n) {
        char[] resArray = new char[n];

        for(int i = 0; i < n; i ++) {
            int r = rand.nextInt(26);
            resArray[i] = CHARACTERS[r];
        }
        String res = new String((resArray));

        return res;
    }

    public void drawFrame(String s) {
        int x_center = width / 2;
        int y_center = height / 2;
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();

        // Draw UI
        Font smallFont = new Font("heading", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.line(0.0, height - 2, width, height - 2);
        StdDraw.text(3, height - 1, "Round" + String.valueOf(round));
        if (!playerTurn){
            StdDraw.text(x_center, height - 1, "Watch!");
        } else {
            StdDraw.text(x_center, height - 1, "Your turn!");
        }
        StdDraw.text(width - 6, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);

        // Draw text
        Font font = new Font(Font.DIALOG, Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(x_center, y_center, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (char ch: letters.toCharArray()) {
            drawFrame(String.valueOf(ch));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {

        StringBuilder stb = new StringBuilder();
        while (stb.length() < n) {
            while (StdDraw.hasNextKeyTyped()) {
                char ch = StdDraw.nextKeyTyped();
                stb.append(ch);
                String str = stb.toString();
                drawFrame(str);
            }
        }
        return stb.toString();
    }

    public void startGame() {
        // Set variables
        round = 0;
        gameOver = false;

        while (!gameOver) {
            round ++ ;
            playerTurn = false;
            drawFrame("Round" + String.valueOf(round));
            StdDraw.pause(1000);

            playerTurn = true;
            String answer = generateRandomString(round);
            flashSequence(answer);

            String input = solicitNCharsInput(round);
            StdDraw.pause(1000);

            if (!input.equals(answer)) {
                drawFrame("Game Over! You made it to round: " + String.valueOf(round));
                gameOver = true;
            }
        }

    }

}
