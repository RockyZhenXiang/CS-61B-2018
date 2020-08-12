package byog.Core;

public class RectangluarRoom extends Room {
    public RectangluarRoom(int x, int y, int wid, int hi) {
        xLocation = x;
        yLocation = y;
        centerLocation = new int[]{ x + wid / 2, y + hi / 2};
        width = wid;
        height = hi;
    }

    @Override
    public int xLocation() {
        return xLocation;
    }

    @Override
    public int yLocation() {
        return yLocation;
    }

    @Override
    public int[] center() {
        return centerLocation;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }
}
