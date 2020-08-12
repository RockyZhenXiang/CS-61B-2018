package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Player extends Movable{
    private int xLocation;
    private int yLocation;
    public int life;
    public int attack;
    public final TETile tile = Tileset.PLAYER;



    /**
     * Constructor
     * @param x: x coordinate
     * @param y: y coordinate
     */

    public Player(int x, int y) {
        xLocation = x;
        yLocation = y;
        life = 5;
        attack = 1;
    }

    public Player(int x, int y, int lf, int atk) {
        xLocation = x;
        yLocation = y;
        life = lf;
        attack = atk;
    }

    public Player(){
    }

    /**
     * change the location of a player
     * @param x: desired x location
     * @param y: desired y location
     */
    public void changeLocation(int x, int y) {
        xLocation = x;
        yLocation = y;
    }

    @Override
    public void move(World world, char keyStroke) {
        switch (keyStroke) {
            case 'W': case 'w':
                if (!world.worldFrame[xLocation][yLocation + 1].description().equals("wall")) {
                    world.worldFrame[xLocation][yLocation] = Tileset.FLOOR;
                    yLocation += 1;
                    world.worldFrame[xLocation][yLocation] = tile;
                }
                break;
            case 'A': case 'a':
                if (!world.worldFrame[xLocation - 1][yLocation].description().equals("wall")) {
                    world.worldFrame[xLocation][yLocation] = Tileset.FLOOR;
                    xLocation -= 1;
                    world.worldFrame[xLocation][yLocation] = tile;
                }
                break;
            case 'S': case 's':
                if (!world.worldFrame[xLocation][yLocation - 1].description().equals("wall")) {
                    world.worldFrame[xLocation][yLocation] = Tileset.FLOOR;
                    yLocation -= 1;
                    world.worldFrame[xLocation][yLocation] = tile;
                }
                break;
            case 'D': case 'd':
                if (!world.worldFrame[xLocation + 1][yLocation].description().equals("wall")) {
                    world.worldFrame[xLocation][yLocation] = Tileset.FLOOR;
                    xLocation += 1;
                    world.worldFrame[xLocation][yLocation] = tile;
                }
                break;
        }
    }
}
