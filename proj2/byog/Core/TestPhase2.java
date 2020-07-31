package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Assert;
import org.junit.Test;

public class TestPhase2 {
    @Test
    public void testPlayWithInputString() {
        Game game = new Game();
        TETile[][] world1 = game.playWithInputString("N999SDDDWWWDDD");
        TETile[][] world2 = game.playWithInputString("N999SDDD:Q");
        world2 = game.playWithInputString("LWWWDDD");
        TETile[][] world3 = game.playWithInputString("N999SDDD:Q");
        world3 = game.playWithInputString("LWWW:Q");
        world3 = game.playWithInputString("LDDD:Q");
        TETile[][] world4 = game.playWithInputString("N999SDDD:Q");
        world4 = game.playWithInputString("L:Q");
        world4 = game.playWithInputString("L:Q");
        world4 = game.playWithInputString("LWWWDDD");
        Assert.assertEquals(world1, world2);
        Assert.assertEquals(world1, world3);
        Assert.assertEquals(world1, world4);
    }
}
