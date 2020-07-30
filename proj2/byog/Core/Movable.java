package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public abstract class Movable implements MovableAPI, Serializable {
    protected TETile tile;

}
