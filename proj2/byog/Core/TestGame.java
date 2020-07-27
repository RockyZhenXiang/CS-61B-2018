package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
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
    private static ArrayList<Room> addRandomRecRoom(TETile[][] world, int seed) {
        final Random RANDOM = new Random(seed);
        int worldWidth = world.length;
        int worldHeight = world[0].length;
        int buffer = 5;
        int roomMaxWid = 15;
        int roomMaxHi = 10;
        int numberOfRoom = RandomUtils.uniform(RANDOM, 5,10);
        ArrayList<Room> res = new ArrayList<>();

        RectangluarRoom[] recRooms = new RectangluarRoom[1000];
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
                res.add(recRooms[j]);
                existingRooms ++ ;
                j ++ ;
            }
        }
        return res;
    }

    /**
     * Connect rooms in the world
     * @param world
     * @param rooms
     */
    public static void connectAllRooms(TETile[][] world, ArrayList<Room> rooms) {
        ArrayList<Room> connectedRooms = new ArrayList<>();
        connectedRooms.add(rooms.get(0)); // adds the first room into connected rooms list
        Random rand = new Random();

        for (Room room: rooms) {
            if (connectedRooms.contains(room)) {
                continue;
            }
            int i = rand.nextInt(connectedRooms.size());
            Room connectedRoom = connectedRooms.get(i);
            Hallway hall = new Hallway(connectedRoom, room);
            Hallway.drawHallWay(world, hall);
            connectedRooms.add(room);
        }
    }

    /**
     * If there is a floor tile in the 8-point stencil and itself is not a floor tile, then this block should be a wall tile
     * @param world: TETile[][] world
     * @param x: x coordinate
     * @param y: y coordinate
     * @return: if this tile should be a wall
     */

    private static boolean shallChangeToWall(TETile[][] world, int x, int y) {
        if (world[x][y] == Tileset.FLOOR || world[x][y] == Tileset.WALL) {
            return false;
        }
        for(int i = x-1; i <= x + 1 ; i ++) {
            for(int j = y-1; j <= y + 1 ; j ++) {
                if (world[i][j] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add walls around all floor tiles in world
     * if there is a floor tile in the 8-point stencil and itself is not a floor tile, then this block should be a wall tile
     * @param world: TETile[][] world
     */
    public static void addWall(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;

        for (int i = 1; i < width - 1; i ++) {
            for (int j = 1; j < height - 1; j ++) {
                if (shallChangeToWall(world, i, j)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
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

        /*
        // add two rooms
        RectangluarRoom room1 = new RectangluarRoom(10, 10, 10, 10);
        RectangluarRoom room2 = new RectangluarRoom(30, 30, 10, 10);
        addRecRoom(world, room1);
        addRecRoom(world, room2);

        // add a hallway
        Hallway hal1 = new Hallway(room1, room2);
        Hallway.drawHallWay(world, hal1);
        */

        // add random rooms
        Random ran = new Random();
        int seed = ran.nextInt(10000);
        ArrayList<Room> res = addRandomRecRoom(world, seed);

        // connect all rooms
        connectAllRooms(world, res);

        // add walls around hallways
        addWall(world);

        /*
        // add a hallway
        Hallway hal1 = new Hallway(res.get(0), res.get(1));
        Hallway.drawHallWay(world, hal1);
        */


        // Draw the world

        ter.renderFrame(world);
    }
}
