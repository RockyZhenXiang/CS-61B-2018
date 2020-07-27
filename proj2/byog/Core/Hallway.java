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
        int start_x = way.start.center()[0];
        int start_y = way.start.center()[1];
        int end_x = way.end.center()[0];
        int end_y = way.end.center()[1];

        int x_diff = end_x - start_x;
        int y_diff = end_y - start_y;

        int x_temp = start_x;
        int y_temp = start_y;

        while (x_temp != end_x) {
            world[x_temp][y_temp] = Tileset.FLOOR;
            if (x_diff < 0) {
                x_temp --;
            } else {
                x_temp ++;
            }
        }

        while (y_temp != end_y) {
            world[x_temp][y_temp] = Tileset.FLOOR;
            if (y_diff < 0) {
                y_temp --;
            } else {
                y_temp ++;
            }
        }
    }
}
