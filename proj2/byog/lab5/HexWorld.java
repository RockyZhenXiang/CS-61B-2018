package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final Random RANDOM = new Random(2873123);
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
                    world[yIndex][xIndex] = 8;
                }
            }
        }
    }

    /** Picks a RANDOM tile
     */
    protected static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.PLAYER;
            case 1: return Tileset.WALL;
            case 2: return Tileset.FLOWER;
            case 3: return Tileset.LOCKED_DOOR;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }


    /**
     * Add a Hexagon to a wold with given location, length, and tile type
     * @param world the world that the hexagon will be added into
     * @param x the x coordinate of the lower-left of the hexagon
     * @param y the y coordinate of the lower-left of the hexagon
     *
     *                **
     *               ****
     *               ****
     *         y ->   **
     *               x
     * @param size the length of the hexagon
     * @param t the type of the tile
     */
    public static void addHexagon(TETile[][] world, int x, int y, int size, TETile t) {
        int width = 3 * size - 2;
        int height = 2 * size;

        for (int yIndex = y; yIndex - y < height; yIndex ++ ) {
            for (int xIndex = x; xIndex - x < width; xIndex ++) {
                int column = xIndex - x;
                int row = yIndex - y;
                if (isInHexagon(column, row, size)) {
                    world[xIndex][yIndex] = t;
                }
            }
        }
    }

    /**
     * find all index of the lower-left corner of the hexagons
     * @param size: size of a single hexagon
     * @return: 2d int array, each element of array is [x, y] coordinate of a single hexagon
     */
    public static int[][] calculateLocation(int size) {
        int[][] res = new int[19][];

        res[0] = new int[]{2*(2*size - 1), 0};
        res[1] = new int[]{(2*size - 1), size};
        res[2] = new int[]{3*(2*size - 1), size};
        res[3] = new int[]{0, 2*size};
        res[4] = new int[]{2*(2*size - 1), 2*size};
        res[5] = new int[]{4*(2*size - 1), 2*size};
        res[6] = new int[]{1*(2*size - 1), 3*size};
        res[7] = new int[]{3*(2*size - 1), 3*size};
        res[8] = new int[]{0*(2*size - 1), 4*size};
        res[9] = new int[]{2*(2*size - 1), 4*size};
        res[10] = new int[]{4*(2*size - 1), 4*size};
        res[11] = new int[]{1*(2*size - 1), 5*size};
        res[12] = new int[]{3*(2*size - 1), 5*size};
        res[13] = new int[]{0*(2*size - 1), 6*size};
        res[14] = new int[]{2*(2*size - 1), 6*size};
        res[15] = new int[]{4*(2*size - 1), 6*size};
        res[16] = new int[]{1*(2*size - 1), 7*size};
        res[17] = new int[]{3*(2*size - 1), 7*size};
        res[18] = new int[]{2*(2*size - 1), 8*size};

        return res;
    }

    public static void main(String[] args) {
        int size = 5;

        // Create a new world;
        final int WIDTH = 11*size - 6;
        final int HEIGHT = 10*size;

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int[][] locations = calculateLocation(size);

        for (int i = 0; i < locations.length; i ++) {
            int[] location = locations[i];
            TETile tile = HexWorld.randomTile();
            addHexagon(world, location[0], location[1], size, tile);
        }
        // Draw the world
        ter.renderFrame(world);

    }
}
