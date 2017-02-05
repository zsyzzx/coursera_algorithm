package part1.week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by seven on 2017/1/22.
 */
public class PercolationStats {
    private int n;
    private int trials;
    private double tests[];
    private Percolation percolation;
    private double mean;
    private double stddev;
    private double confidenceLo, confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials <= 0");
        }
        this.n = n;
        this.trials = trials;
        tests = new double[trials];
        test_Percolation();

    }

    public static void main(String[] args) {
        int n, t;
        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.println("Mean                   =" + percolationStats.mean());
        System.out.println("Stddev                 =" + percolationStats.stddev());
        System.out.println("95% confidence interval=" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi());
    }

    private void test_Percolation() {
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                //如果已经连通，跳过
                if (percolation.isOpen(row, col)) {
                    continue;
                }
                percolation.open(row, col);
            }
            tests[i] = percolation.numberOfOpenSites() / ((double) n * n);

        }
        calculate();
    }

    private void calculate() {
        this.mean = StdStats.mean(tests);
        this.stddev = StdStats.stddev(tests);

        // TODO 加不加括号有什么区别
        confidenceHi = this.mean + 1.96 * stddev / Math.sqrt(this.n);
        confidenceLo = this.mean - 1.96 * stddev / Math.sqrt(this.n);
    }

    //    返回平均值
    public double mean() {
        return this.mean;
    }

    // 返回标准差
    public double stddev() {
        return this.stddev;
    }

    /**
     * 置信区间
     */
    public double confidenceLo() {
        return this.confidenceLo;

    }

    public double confidenceHi() {
        return this.confidenceHi;
    }

}
