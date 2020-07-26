package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
import org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TestHexWorld {

    @Test
    public void testIsInHexagon() {
        int size = 4;
        int width = 3 * size - 2;
        int height = 2 * size;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                boolean res = HexWorld.isInHexagon(i, j, size);
                System.out.println("i = " + i + ", j = " + j + ", result = " + res);
                if (i == width - 1) {
                    System.out.println("");
                }
            }
        }
    }

    @Test
    public void testChange() {
        int [][] wor= new int[20][20];
        for (int[] a: wor) {
            System.out.println(Arrays.toString(a));
        }
        int X = 2;
        int Y = 3;
        int size = 4;
        System.out.println("");

        HexWorld.changeContent(wor, X, Y, size);
        for (int[] a: wor) {
            System.out.println(Arrays.toString(a));
        }
    }

    @Test
    public void testAddHex() {
        int size = 5;
        int X = 2;
        int Y = 3;

        TETile tile = HexWorld.randomTile();

        // Create a new world;
        final int WIDTH = 30;
        final int HEIGHT = 30;

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.PLAYER;
            }
        }

        // Put one hexagon into the world
        HexWorld.addHexagon(world, X, Y, size, tile);

        // Draw the world
        ter.renderFrame(world);
    }

    @Test
    public void testCalculateLocation() {
        int[][] res = HexWorld.calculateLocation(2);
        for (int[] a: res) {
            System.out.println(Arrays.toString(a));
        }
    }
}
