package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Test;

public class TestPhase2 {
    @Test
    public void testPlayWithInputString() {
        Game game = new Game();
        String seed = "N123456SWWWAAAAAAASASD:Q";
        System.out.println(TETile.toString(game.playWithInputString(seed)));
        String seed2 = "L:Q";
        System.out.println(TETile.toString(game.playWithInputString(seed2)));
    }
}
