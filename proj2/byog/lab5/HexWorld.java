package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static boolean isInHexagon() {

        return true; 

    }

    /**
     * Add a Hexagon to a wold with given location, length, and tile type
     * @param world the world that the hexagon will be added into
     * @param x the x coordinate of the upper-left of the hexagon
     * @param y the y coordinate of the upper-left of the hexagon
     *               x
     *          y -> **
     *              ****
     *              ****
     *               **
     * @param size the length of the hexagon
     * @param t the type of the tile
     */
    public static void addHexagon(TETile[][] world, int x, int y, int size, TETile t) {

    }
}
