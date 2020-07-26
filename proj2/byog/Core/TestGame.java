package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import java.util.Random;

public class TestGame {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    static TERenderer ter = new TERenderer();

    /**
     * check if the room can fit in the world
     * @param world existing TETile[][] world
     * @param room room object
     * @return if the room can fit in the world
     */

    private static boolean inbound(TETile[][] world, RoomAPI room) {
        int world_width = world.length;
        int world_height = world[0].length;
        if (room.width() + room.xLocation() > world_width || room.yLocation() + room.height() > world_height) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the room overlapped with existing rooms
     * @param world existing TETile[][] world
     * @param room room object
     * @return if the room overlapped with existing room
     */
    private static boolean overlappedRoom(TETile[][] world, RoomAPI room) {

        for(int x = room.xLocation(); x < room.xLocation() + room.width(); x++) {
            for(int y = room.yLocation(); y < room.yLocation() + room.height(); y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * add one rectangular room into a world
     * @param world existing TETile[][] world
     * @param x: x coordinate of the lower-left corner
     * @param y: y coordinate of the lower-left corner
     * @param wid: width of the room
     * @param hi: height of the room
     */
    private static void addRecRoom(TETile[][] world, int x, int y, int wid, int hi) {
        RectangluarRoom room = new RectangluarRoom(x, y, wid, hi);
        if (inbound(world, room) == false || overlappedRoom(world, room) == false) {
            return;
        } else {
            int w = room.width();
            int h = room.height();
            for (int xIndex = x; xIndex < w + x; xIndex++) {
                for (int yIndex = y; yIndex < h + y; yIndex++) {
                    if (xIndex == x || yIndex == y || xIndex == w + x - 1 || yIndex == h + y - 1) {
                        world[xIndex][yIndex] = Tileset.WALL;
                    } else {
                        world[xIndex][yIndex] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    /**
     * add one rectangular room into a world
     * @param world: existing TETile[][] world
     * @param room: room object
     */
    private static void addRecRoom(TETile[][] world, RectangluarRoom room) {
        if (inbound(world, room) == false || overlappedRoom(world, room) == false) {
            return;
        } else {
            int w = room.width();
            int h = room.height();
            int x = room.xLocation;
            int y = room.yLocation;
            for (int xIndex = x; xIndex < w + x; xIndex++) {
                for (int yIndex = y; yIndex < h + y; yIndex++) {
                    if (xIndex == x || yIndex == y || xIndex == w + x - 1 || yIndex == h + y - 1) {
                        world[xIndex][yIndex] = Tileset.WALL;
                    } else {
                        world[xIndex][yIndex] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    /**
     * Add random number of random size of the RecRoom into the world
     * @param world: existing TETile[][] world
     * @param seed: seed used to create random object
     */
    private static void addRandomRecRoom(TETile[][] world, int seed) {
        final Random RANDOM = new Random(seed);
        int worldWidth = world.length;
        int worldHeight = world[0].length;
        int buffer = 5;
        int roomMaxWid = 15;
        int roomMaxHi = 10;

        int numberOfRoom = RandomUtils.uniform(RANDOM, 5,10);
        RectangluarRoom[] recRooms = new RectangluarRoom[100];
        for (int i = 0; i < recRooms.length; i++) {
            int x = RandomUtils.uniform(RANDOM, buffer, worldWidth - buffer);
            int y = RandomUtils.uniform(RANDOM, buffer, worldHeight - buffer);
            int wid = RandomUtils.uniform(RANDOM, 3, roomMaxWid);
            int hi = RandomUtils.uniform(RANDOM, 3, roomMaxHi);
            recRooms[i] = new RectangluarRoom(x, y, wid, hi);
        }
        int existingRooms = 0;
        int j = 0;
        while(existingRooms < numberOfRoom && j < recRooms.length ) {
            RectangluarRoom room = recRooms[j];
            if (!inbound(world, room) || !overlappedRoom(world, room)) {
                j ++ ;
                continue;
            } else {
                addRecRoom(world, recRooms[j]);
                existingRooms ++ ;
                j ++ ;
            }
        }
    }

    @Test
    public void testEqual () {
        ter.initialize(5, 5);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.PLAYER;
            }
        }

        System.out.println(world[2][2] == Tileset.PLAYER);
    }

    public static void main(String[] args) {
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // put a room
//        int x = 50;
//        int y = 30;
//        int wid = 30;
//        int hi = 20;
//
//        addRecRoom(world, x, y, wid, hi);
//        addRecRoom(world, x+5, y+5, wid, hi);

        // add random rooms
        Random ran = new Random();
        int seed = ran.nextInt(10000);
        addRandomRecRoom(world, seed);

        // Draw the world
        ter.renderFrame(world);
    }
}
