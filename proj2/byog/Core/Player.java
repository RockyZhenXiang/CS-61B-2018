package byog.Core;

public class Player implements Moveable{
    private int xLocation;
    private int yLocation;
    public  int life;
    public  int attack;

    public Player(){

    }

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
    public void move() {

    }
}
