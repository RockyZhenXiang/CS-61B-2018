package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;
    TERenderer ter = new TERenderer();
    public boolean endGame = false;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        StartingMenu stMenu = new StartingMenu(WIDTH, HEIGHT);
        stMenu.readKeyBoard();
        long seed = stMenu.getSeed();

        World world = new World(seed, WIDTH, HEIGHT);
        TETile[][] finalWorldFrame =
                world.createWorld(seed, WIDTH, HEIGHT);

        Player player = new Player();
        world.placePlayer(finalWorldFrame, player);

        ter.renderFrame(finalWorldFrame);

        statGameWithKeyboard(world, player);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        String command;
        boolean saveOrNot;
        char[] inputArray = input.toCharArray();
        long seed;
        TETile[][] finalWorldFrame;

        if (inputArray[inputArray.length - 1] == 'Q') {
            saveOrNot  = true;
        }

        if (inputArray[0] == 'N') {
            seed = readSeedFromInput(inputArray);

            if (seed == 0) {
                throw new RuntimeException("Input needs to contain at least one digit");
            }

            World world = new World(seed, WIDTH, HEIGHT);
            finalWorldFrame = world.createWorld(seed, WIDTH, HEIGHT);

            Player player = new Player();
            world.placePlayer(finalWorldFrame, player);

            command = readCommand(inputArray);

        } else { // inputArray[0] == 'L'
            World world = loadGame();
            command = readCommand(inputArray);

            finalWorldFrame = world.worldFrame;
        }

        return finalWorldFrame;
    }

    /**
     * reads a array of chars that only have a series of int, return the series of int
     * @param inputArray: input array
     * @return: seed contains in the array
     */

    private long readSeedFromInput (char[] inputArray) {
        boolean lastDigit = false;
        long seed = 0;
        int i = 1;
        char ch = inputArray[i];
        while (!lastDigit) {
            seed = seed * 10 + Character.getNumericValue(ch);
            i += 1;
            ch = inputArray[i];
            if (ch == 'S') {
                lastDigit = true;
            }
        }

        return seed;
    }

    /**
     * reads a array of chars that
     * @param inputArray: input array
     * @return: seed contains in the array
     */
    private String readCommand(char[] inputArray) {
        boolean firstCommand = false;
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (!firstCommand && i < inputArray.length) { // if the next one is a English letter, then it is a command
            char ch = inputArray[i + 1];
            if (Character.isLetter(ch)) {
                firstCommand = true;
            }
            i += 1;
        }
        // Now i + 1 is at the index of the first command
        i += 1;
        char lastTwo = inputArray[inputArray.length - 2];
        while ((lastTwo == ':' && i < inputArray.length - 2) ||
                lastTwo != ':' && i < inputArray.length) {
            char ch = inputArray[i];
            sb.append(ch);
            i += 1;
        }
        return sb.toString();
    }


    public void statGameWithKeyboard(World world, Player player) {
        while(!endGame){
            while(StdDraw.hasNextKeyTyped()){
                char typed = StdDraw.nextKeyTyped();
                if (typed == 'W' || typed == 'w' ||
                    typed == 'A' || typed == 'a' ||
                    typed == 'S' || typed == 's' ||
                    typed == 'D' || typed == 'd') {
                    player.move(world, typed);
                    ter.renderFrame(world.worldFrame);
                }
            }
        }
    }

    public World loadGame() {
        // TODO: implement loading function

        return new World();
    }

    public void saveGame() {
        // TODO: implement saving function
    }

}
