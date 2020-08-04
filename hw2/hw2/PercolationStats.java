package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    double [] perThresholds;
    int times;
    int len;
    /**
     * perform T independent experiments on an N-by-N grid
     * @param N size of the grid
     * @param T times of performance
     * @param pf Percolation object
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T < 0) {
            throw new IllegalArgumentException();
        }

        perThresholds = new double[T];
        times = T;
        len = N;
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

        return (mean() - 1.96 * stddev()) / Math.sqrt(times);
    }// low endpoint of 95% confidence interval

    public double confidenceHigh() {
        return (mean() + 1.96 * stddev()) / Math.sqrt(times);
    }                                // high endpoint of 95% confidence interval

    private static void main(String[] args) {
        int N = 1000;
        int T = 30;
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(N, T, pf);
        System.out.println(ps.mean());
    }
}
