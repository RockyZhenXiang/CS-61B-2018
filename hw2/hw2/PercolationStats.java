package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double [] perThresholds;
    private int times;
    /**
     * perform T independent experiments on an N-by-N grid
     * @param N size of the grid
     * @param T times of performance
     * @param pf Percolation object
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        perThresholds = new double[T];
        times = T;
        for (int i = 0; i < T; i += 1) {
            Percolation pl = pf.make(N);

            while (!pl.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);

                if (!pl.isOpen(row, col)) {
                    pl.open(row, col);
                }
            }
            perThresholds[i] = (double) pl.numberOfOpenSites() / (N * N);
        }

    }
    public double mean() {

        return StdStats.mean(perThresholds);
    }                            // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(perThresholds);
    }                                       // sample standard deviation of percolation threshold
    public double confidenceLow() {

        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }// low endpoint of 95% confidence interval

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }                                // high endpoint of 95% confidence interval

    private static void main(String[] args) {
        int N = 20;
        int T = 10;
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(N, T, pf);
        System.out.println("Low = " + ps.confidenceLow());
        System.out.println("Mean = " + ps.mean());
        System.out.println("Stddev = " + ps.stddev());
        System.out.println("T = " + ps.times);
    }
}
