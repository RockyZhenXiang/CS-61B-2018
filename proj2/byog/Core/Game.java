package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;

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

        ArrayList<Integer> seedList = new ArrayList();
        for (char ch: input.toCharArray()) {
            if (Character.isDigit(ch)) {
                seedList.add(Character.getNumericValue(ch));
            }
        }

        if (seedList.size() == 0) {
            throw new RuntimeException("Input needs to contain at least one digit");
        }

        long seed = 0;
        for (int x: seedList) {
            seed = 10 * seed + x;
        }

        World world = new World(seed, WIDTH, HEIGHT);
        TETile[][] finalWorldFrame =
                world.createWorld(seed, WIDTH, HEIGHT);

        Player player = new Player();
        world.placePlayer(finalWorldFrame, player);
        return finalWorldFrame;
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

}
