package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway {
    protected Room start;
    protected Room end;

    public Hallway(Room str, Room ed) {
        start = str;
        end = ed;
    }

    /**
     * Draw the hallway between the start and the end of the hallway in the world
     * @param world
     * @param way
     */

    public static void drawHallWay(TETile[][] world, Hallway way) {
        int startX = way.start.center()[0];
        int startY = way.start.center()[1];
        int endX = way.end.center()[0];
        int endY = way.end.center()[1];

        int xDiff = endX - startX;
        int yDiff = endY - startY;

        int xTemp = startX;
        int yTemp = startY;

        while (xTemp != endX) {
            world[xTemp][yTemp] = Tileset.FLOOR;
            if (xDiff < 0) {
                xTemp--;
            } else {
                xTemp++;
            }
        }

        while (yTemp != endY) {
            world[xTemp][yTemp] = Tileset.FLOOR;
            if (yDiff < 0) {
                yTemp--;
            } else {
                yTemp++;
            }
        }
    }

}
