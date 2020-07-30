package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class World implements Serializable {
    protected static int width;
    protected static int height;
    protected static long seed;
    public TETile[][] worldFrame;
    public Player user;

    /**
     * Constructor
     * @param se: seed to generate random world
     * @param wid: width of the world
     * @param hei: height of the world
     */

    public World(long se, int wid, int hei) {
        seed = se;
        width = wid;
        height = hei;
        worldFrame = createWorld(seed, width, height);
        Player player = new Player();
        placePlayer(worldFrame, player);
        user = player;
    }

    // use for testing
    public World() {
    }

    /**
     * check if the room can fit in the world
     * @param world existing TETile[][] world
     * @param room room object
     * @return if the room can fit in the world
     */

    private static boolean inbound(TETile[][] world, RoomAPI room) {
        return !(room.width() + room.xLocation() > width
                || room.yLocation() + room.height() > height);
    }

    /**
     * Check if the room overlapped with existing rooms
     * @param world existing TETile[][] world
     * @param room room object
     * @return if the room overlapped with existing room
     */
    private static boolean overlappedRoom(TETile[][] world, RoomAPI room) {

        for (int x = room.xLocation(); x < room.xLocation() + room.width(); x++) {
            for (int y = room.yLocation(); y < room.yLocation() + room.height(); y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * add one rectangular room into a world
     * @param world: existing TETile[][] world
     * @param room: room object
     */
    private static void addRecRoom(TETile[][] world, RectangluarRoom room) {
        if (!inbound(world, room) || !overlappedRoom(world, room)) {
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
     * @param world : existing TETile[][] world
     * @param seed : seed used to create random object
     */
    private static ArrayList<Room> addRandomRecRoom(TETile[][] world, long seed) {
        final Random random = new Random(seed);
        int worldWidth = world.length;
        int worldHeight = world[0].length;
        int buffer = 5;
        int roomMaxWid = 15;
        int roomMaxHi = 10;
        int numberOfRoom = RandomUtils.uniform(random, 5, 10);
        ArrayList<Room> res = new ArrayList<>();

        RectangluarRoom[] recRooms = new RectangluarRoom[1000];
        for (int i = 0; i < recRooms.length; i++) {
            int x = RandomUtils.uniform(random, buffer, worldWidth - buffer);
            int y = RandomUtils.uniform(random, buffer, worldHeight - buffer);
            int wid = RandomUtils.uniform(random, 3, roomMaxWid);
            int hi = RandomUtils.uniform(random, 3, roomMaxHi);
            recRooms[i] = new RectangluarRoom(x, y, wid, hi);
        }

        int existingRooms = 0;
        int j = 0;
        while (existingRooms < numberOfRoom && j < recRooms.length) {
            RectangluarRoom room = recRooms[j];
            if (!inbound(world, room) || !overlappedRoom(world, room)) {
                j++;
                continue;
            } else {
                addRecRoom(world, recRooms[j]);
                res.add(recRooms[j]);
                existingRooms++;
                j++;
            }
        }
        return res;
    }

    /**
     * Connect rooms in the world
     * @param world: TETile[][] world
     * @param rooms: Array of rooms to be connected
     */
    public static void connectAllRooms(TETile[][] world, ArrayList<Room> rooms, long seed) {
        ArrayList<Room> connectedRooms = new ArrayList<>();
        connectedRooms.add(rooms.get(0)); // adds the first room into connected rooms list
        Random rand = new Random(seed);

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
     * If there is a floor tile in the 8-point stencil and itself is not a floor tile,
     * then this block should be a wall tile
     * @param world: TETile[][] world
     * @param x: x coordinate
     * @param y: y coordinate
     * @return: if this tile should be a wall
     */

    private static boolean shallChangeToWall(TETile[][] world, int x, int y) {
        if (world[x][y] == Tileset.FLOOR || world[x][y] == Tileset.WALL) {
            return false;
        }
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (world[i][j] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add walls around all floor tiles in world
     * if there is a floor tile in the 8-point stencil and itself is not a floor tile,
     * then this block should be a wall tile
     * @param world: TETile[][] world
     */
    public static void addWall(TETile[][] world) {
        int worldWidth = world.length;
        int worldHeight = world[0].length;

        for (int i = 1; i < worldWidth - 1; i++) {
            for (int j = 1; j < worldHeight - 1; j++) {
                if (shallChangeToWall(world, i, j)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * place a player tile randomly and stores the location of player
     * @param world: TETile[][] world object
     * @return: the location of player tile
     */

    public void placePlayer(TETile[][] world, Player player) {
        user = player;
        Random rand = new Random(seed);
        while (true) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.PLAYER;
                player.changeLocation(x, y);
                break;
            }
        }
    }


    /**
     * Creates a world with rooms and hallways with input seeds
     * @param seed : used to create a randomized world
     */
    public TETile[][] createWorld(long seed, int worldWidth, int worldHeight) {

        // initialize tiles
        TETile[][] world = new TETile[worldWidth][worldHeight];
        for (int x = 0; x < worldWidth; x += 1) {
            for (int y = 0; y < worldHeight; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // add random rooms
        ArrayList<Room> res = addRandomRecRoom(world, seed);

        // connect all rooms
        connectAllRooms(world, res, seed);

        // add walls around hallways
        addWall(world);

        // set world
        worldFrame = world;

        return world;
    }
}
