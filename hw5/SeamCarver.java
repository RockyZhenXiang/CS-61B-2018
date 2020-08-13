import java.awt.*;
import edu.princeton.cs.algs4.Picture;
public class SeamCarver {
    private Picture picture;
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    /**
     * @return current picture
     */
    public Picture picture() {
        return picture;
    }

    /**
     * @return width of current picture
     */
    public int width() {
        return picture.width();
    }

    /**
     *
     * @return height of current picture
     */
    public int height() {
        return picture.height();
    }

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @return energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        return horEnergy(x, y) + verEnergy(x, y);
    }

    private double horEnergy (int x, int y) {
        int maxW = width();
        int right = x + 1;
        int left = x - 1;
        if (x == 0) { // no left
            left = maxW - 1;
        }
        if (x == maxW - 1) { // no right
            right = 0;
        }

        int rightRed = picture.get(right, y).getRed();
        int rightBlue = picture.get(right, y).getBlue();
        int rightGreen = picture.get(right, y).getGreen();

        int leftRed = picture.get(left, y).getRed();
        int leftBlue = picture.get(left, y).getBlue();
        int leftGreen = picture.get(left, y).getGreen();

        return Math.pow(rightRed - leftRed, 2)
                 + Math.pow(rightBlue - leftBlue, 2)
                 + Math.pow(rightGreen - leftGreen, 2);
    }

    private double verEnergy (int x, int y) {
        int maxH = height();
        int up = y - 1;
        int down = y + 1;
        if (y == 0) { // no up
            up = maxH - 1;
        }
        if (y == maxH - 1) { // no down
            down = 0;
        }

        int upRed = picture.get(x, up).getRed();
        int upBlue = picture.get(x, up).getBlue();
        int upGreen = picture.get(x, up).getGreen();

        int downRed = picture.get(x, down).getRed();
        int downBlue = picture.get(x, down).getBlue();
        int downGreen = picture.get(x, down).getGreen();

        return Math.pow(upRed - downRed, 2)
                + Math.pow(upBlue - downBlue, 2)
                + Math.pow(upGreen - downGreen, 2);
    }


    public int[] findHorizontalSeam() {

        return new int[10];
    }           // sequence of indices for horizontal seam
    public int[] findVerticalSeam() {
        return new int[10];
    }           // sequence of indices for vertical seam
    public void removeHorizontalSeam(int[] seam) {

    }   // remove horizontal seam from picture
    public    void removeVerticalSeam(int[] seam) {

    }    // remove vertical seam from picture

}
