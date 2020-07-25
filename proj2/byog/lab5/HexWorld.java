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

    protected static boolean isInHexagon(int column, int row, int size) {
        if (row >= size) {
          row = 2 * size - 1 - row;
        }
        return (column >= size - row - 1 && 2 * size + row - 1 > column);
    }

    protected static void changeContent(int[][] world, int x, int y, int size ) {
        int width = 3 * size - 2;
        int height = 2 * size;

        for (int yIndex = y; yIndex - y < height; yIndex ++ ) {
            for (int xIndex = x; xIndex - x < width; xIndex ++) {
                int column = xIndex - x;
                int row = yIndex - y;
                if (isInHexagon(column, row, size)) {
                    world[yIndex][xIndex] = 1;
                }
            }
        }
    }


    /**
     * Add a Hexagon to a wold with given location, length, and tile type
     * @param world the world that the hexagon will be added into
     * @param x the x coordinate of the upper-left of the hexagon
     * @param y the y coordinate of the upper-left of the hexagon
     *               x
     *          y ->  **
     *               ****
     *               ****
     *                **
     * @param size the length of the hexagon
     * @param t the type of the tile
     */
    public static void addHexagon(TETile[][] world, int x, int y, int size, TETile t) {
        int width = 3 * size - 2;
        int height = 2 * size;

        for (int yIndex = y; yIndex - y < height; yIndex ++ ) {
            for (int xIndex = x; xIndex - x <width; xIndex ++) {

            }

        }

    }
}
