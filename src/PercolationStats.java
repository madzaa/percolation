import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    final private double threshold[];
    final private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        int a, b;
        this.trials = trials;
        this.threshold = new double[trials];
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                a = StdRandom.uniform(1, n + 1);
                b = StdRandom.uniform(1, n + 1);
                percolation.open(a, b);
            }
            threshold[i] = ((double) percolation.numberOfOpenSites()) / (n * n);
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * (stddev() / Math.sqrt(trials)));
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * (stddev() / Math.sqrt(trials)));
    }
    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(4, 3);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval  = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}