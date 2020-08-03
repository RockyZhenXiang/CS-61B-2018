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
        grid = new boolean[N][N];
        dj = new WeightedQuickUnionUF(N*N);

    }

    /**
     * hash a 2D coordinate into a 1D id
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     * @return hash code of [row, col]
     */
    private int xy2iD(int row, int col) {
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

        }
    }



    /**
     * is the site (row, col) open?
     * @param row y coordinate of the tile
     * @param col x coordinate of the tile
     * @return true if the site is open
     */
    public boolean isOpen(int row, int col) {

        return false;
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


        return 0;
    }

    /**
     * @return if the system percolate?
     */
    public boolean percolates() {

        return false;
    }


    public static void main(String[] args) {
        Percolation per = new Percolation(5);

    }

}
