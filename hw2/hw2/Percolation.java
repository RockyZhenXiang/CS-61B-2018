package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {
    boolean[][] grid;
    int openedSite = 0;
    WeightedQuickUnionUF dj;
    /**
     * Constructor: create N-by-N grid, with all sites initially blocked
     * @param N length of the grid
     */
    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N][N];
        dj = new WeightedQuickUnionUF(N*N);
    }

    /**
     * hash a 2D coordinate into a 1D id
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     * @return hash code of [row, col]
     */
    private int xy2id(int row, int col) {
        int n = grid.length;
        return row * n + col;
    }

    /**
     * open the site (row, col) if it is not open already
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            if (openedSite != 0) {
                int [] openedNei = neighborOpen(row, col);
                for (int id: openedNei) {
                   if (id >= 0) {
                       dj.union(xy2id(row, col), id);
                   }
                }
            }
            openedSite += 1;
        }
    }


    /**
     * returns the id of the opened site surrounding [row, col]
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     * @return the id of the opened site surrounding [row, col]
     */
    private int[] neighborOpen(int row, int col) {
        int n = grid.length;
        int[][] check = new int[][]{new int[]{row, col + 1},
                                    new int[]{row, col - 1},
                                    new int[]{row + 1, col},
                                    new int[]{row - 1, col}};
        int[] res = new int[]{-1, -1 ,-1 ,-1};
        for (int i = 0; i < 4; i += 1) {
            if (isInBound(check[i][0], check[i][1])) {
                if (isOpen(check[i][0], check[i][1])) {
                    res[i] = xy2id(check[i][0], check[i][1]);
                }
            }
        }
        return res;
    }


    /**
     * is the site (row, col) open?
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     * @return true if the site is open
     */
    public boolean isOpen(int row, int col) {
        if (!isInBound(row,col)) {
            throw new IndexOutOfBoundsException(row + " " + col);
        }

        return grid[row][col];
    }

    /**
     * check if [row, col] is in the grid
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     * @return if [row, col] is in the grid
     */
    private boolean isInBound(int row, int col) {

        return row < grid.length && row >= 0 &&
                col < grid.length && col >= 0;
    }


    /**
     * is the site (row, col) full?
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     * @return true if the site is full
     */
    public boolean isFull(int row, int col) {

        return false;
    }

    /**
     * @return number of open sites
     */
    public int numberOfOpenSites() {

        return openedSite;
    }

    /**
     * @return if the system percolate?
     */
    public boolean percolates() {

        return false;
    }


    public static void main(String[] args) {
        Percolation per = new Percolation(5);
        per.open(2,3);
        per.open(2,4);
        per.open(5,2);

    }

}
