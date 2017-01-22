package part1.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by seven on 2017/1/22.
 */
public class Percolation {
    private int n;
	private int numberofopenSites;
    private boolean[][] openState;
    private WeightedQuickUnionUF wuf;

    /**
     * input n
     * create n-by-n grid, with all sites blocked
     * n<=0 抛出异常
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("The N" + n + " is <= 0");
        this.n = n;
        numberofopenSites = 0;
        wuf = new WeightedQuickUnionUF(n * n + 2);  //(1,1)是最左上角的元素
        // 所有点都是blocked
        openState = new boolean[n + 1][n + 1];
        for (int i = 0; i < openState.length; i++) {
            for (int j = 0; j < openState[i].length; j++) {
                openState[i][j] = false;
            }
        }

    }

    /**
     * 判断下标是否在范围内
     */
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IndexOutOfBoundsException("point( " + row + "," + col + ") is not between 1 and " + (n));
        }
    }

    /**
     * @param row
     * @param col
     * @return uf一维数组值
     */
    private int sites(int row, int col) {
        return (row - 1) * n + col;
    }

    /**
     * open site
     * 链接四边的点，把自己的连通打开blocksites[row][col]
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        //未连通的点,开始连通操作
        openState[row][col] = true;
        numberofopenSites++;
        int p = sites(row, col);   //这个点
        int left = sites(row, col - 1);
        int right = sites(row, col + 1);
        int up = sites(row - 1, col);
        int down = sites(row + 1, col);

        if (col - 1 >= 1) {  //left
            if (isOpen(row, col - 1)) {
                wuf.union(p, left);
            }
        }
        //right
        if (col + 1 <= n) {
            if (isOpen(row, col + 1)) {
                wuf.union(p, right);
            }
        }
        //up
        if (row - 1 >= 1) {
            if (isOpen(row - 1, col)) {
                wuf.union(p, up);
            }
        } else {  //顶
            wuf.union(0, p);//0为虚拟顶点，如果判断是顶部，则连接顶点
        }
        //down
        if (row + 1 <= n) {
            if (isOpen(row + 1, col)) {
                wuf.union(p, down);
            }
        } else {
            wuf.union(n * n + 1, p); //若p点是底部点，则连接虚拟底部点
        }
    }

    /**
     * 如果点是已经连通的则返回真
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openState[row][col];
    }

    /*
     * 判断是否与上方连通
     */
    // TODO 怎么避免回溯问题
    public boolean isFull(int row, int col) {
        validate(row, col);
        int p = sites(row, col);
        return wuf.connected(p, 0);
    }

    /**
     * 计算已经连通的点
     *
     * @return
     */
    public int numberOfOpenSites() {
        return numberofopenSites;
    }

    /**
     * does system percolate,系统是否渗透
     * 返回bool值
     *
     * @return
     */
    public boolean percolates() {
        return wuf.connected(0, n * n + 1);
    }



}
