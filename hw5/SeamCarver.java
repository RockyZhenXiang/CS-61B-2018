
import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    public SeamCarver(Picture pic) {
        picture = new Picture(pic);
    }

    /**
     * @return current picture
     */
    public Picture picture() {
        return new Picture(picture);
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

    private double horEnergy(int x, int y) {
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

        return (rightRed - leftRed) * (rightRed - leftRed)
                 + (rightBlue - leftBlue) * (rightBlue - leftBlue)
                + (rightGreen - leftGreen) * (rightGreen - leftGreen);
    }

    private double verEnergy(int x, int y) {
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

        return (upRed - downRed) * (upRed - downRed)
                + (upBlue - downBlue) * (upBlue - downBlue)
                + (upGreen - downGreen) * (upGreen - downGreen);
    }

    /**
     * @return sequence of indices for horizontal seam that has
     * the min sum energy
     */
    public int[] findHorizontalSeam() {
        Picture originalPic = new Picture(picture);
        picture = picTranspose(originalPic);
        int[] res = findVerticalSeam();
        picture = originalPic;
        return res;
    }

    private Picture picTranspose(Picture pic) {
        Picture res = new Picture(pic.height(), pic.width());
        for (int i = 0; i < res.width(); i += 1) {
            for (int j = 0; j < res.height(); j += 1) {
                Color color = pic.get(j, i);
                res.set(i, j, color);
            }
        }

        return res;
    }

    /**
     * @return sequence of indices for vertical seam that has
     * the min energy
     */
    public int[] findVerticalSeam() {

        int[] res = new int[height()];

        double[][] sumEnergys = new double[height()][width()];

        if (width() == 1) {
            return res;
        }

        for (int i = 0; i < width(); i += 1) {
            sumEnergys[0][i] = energy(i, 0);
        }

        for (int row = 1; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                if (col == 0) {
                    sumEnergys[row][col] = energy(col, row)
                            + Math.min(sumEnergys[row - 1][col], sumEnergys[row - 1][col + 1]);
                } else if (col == width() - 1) {
                    sumEnergys[row][col] = energy(col, row)
                            + Math.min(sumEnergys[row - 1][col], sumEnergys[row - 1][col - 1]);
                } else {
                    sumEnergys[row][col] = energy(col, row)
                            + Math.min(sumEnergys[row - 1][col],
                            Math.min(sumEnergys[row - 1][col - 1], sumEnergys[row - 1][col + 1]));
                }
            }
        }

        res[res.length - 1] = minIndex(sumEnergys[sumEnergys.length - 1], 0);
        // backtracking
        for (int i = res.length - 2; i >= 0; i--) {
            if (res[i + 1] == 0) {
                double[] splice = Arrays.copyOfRange(sumEnergys[i], 0, 2);
                res[i] = minIndex(splice, 0);
            } else if (res[i + 1] == width() - 1) {
                double[] splice = Arrays.copyOfRange(sumEnergys[i], width() - 2, width());
                res[i] = minIndex(splice, width() - 2);
            } else {
                double[] splice = Arrays.copyOfRange(sumEnergys[i], res[i + 1] - 1, res[i + 1] + 2);
                res[i] = minIndex(splice, res[i + 1] - 1);
            }
        }

        return res;
    }

    private int minIndex(double[] arr, int first) {
        int id = 0;
        double che = Double.MAX_VALUE;
        for (int i = 0; i < arr.length; i += 1) {
            if (arr[i] < che) {
                id = i;
                che = arr[i];
            }
        }
        return id + first;
    }


    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, findHorizontalSeam());
    }
    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, findVerticalSeam());
    }    // remove vertical seam from picture

}
