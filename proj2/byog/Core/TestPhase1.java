package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Test;


public class TestPhase1 {
    @Test
    public void testPlayWithInputString() {
        Game game = new Game();
        String seed = "L519788084SWASDWASD:Q";
        System.out.println(TETile.toString(game.playWithInputString(seed)));
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(TETile.toString(game.playWithInputString(seed)));
    }

    public static void main(String[] args) {
        TestPhase1 test = new TestPhase1();
        test.testPlayWithInputString();
    }
}
