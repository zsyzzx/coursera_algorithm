package part1.week4.assignment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by seven on 2017/2/1.
 * 游戏画板，
 */
public class Board {
    // TODO: 2017/2/3 要优化memory使用：为什么使用char[] ; 实现hamming优先队列 ； 如何使用一个优先队列完成board和twin的比较
    private int[] titles;
    private int n, zero;
    private int hamming, manhattan;

    public Board(int[][] blocks) {
        n = blocks.length;
        titles = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                titles[i * n + j] = blocks[i][j];
                if (titles[i * n + j] == 0) zero = i * n + j;
            }
        }
        hamming = calHamming(titles);
        manhattan = calManhattan(titles);
    }

    private Board(int[] titles, int n, int zero, int hamming, int manhattan) {
        this.titles = titles;
        this.n = n;
        this.zero = zero;
        this.hamming = hamming;
        this.manhattan = manhattan;
    }

    /*
        * 画板维度，返回n*/
    public int dimension() {
        return n;
    }

    /*
    * 判断是否已经求解*/
    public boolean isGoal() {
        return hamming == 0;
    }

    private int[] exch(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
        return t;
    }

    // a board that is obtained by exchanging any pair of titles
    /*在原有的board基础上，任意调换两个元素，创建一个新board，
    * 可以任意调换，但是为了实现方便，调换第一行的两个元素，如果其中一个为0，则移到下一行*/
    public Board twin() {
        int[] newtitles = titles.clone();
        if (newtitles[0] * newtitles[1] == 0) {   //如果第一行前两个元素有一个为0则，调换下一行
            int next_i = 1 * n + 0;
            int next_j = 1 * n + 1;
            exch(newtitles, next_i, next_j);
        } else {  // 不为零则调换第一行
            exch(newtitles, 0, 1);
        }

        //新board的hamming和manhattan
        int ham = calHamming(newtitles);
        int man = calManhattan(newtitles);
        return new Board(newtitles, n, zero, ham, man);

    }

    //  在错误位置点的数量
    // 为0则代表：所有位置上的点都正确
    public int hamming() {
        return hamming;
    }

    private int calHamming(int titles[]) {
        int cnt = 0;
        for (int i = 0; i < n * n; i++) {
            if (titles[i] == 0) continue;
            if (titles[i] != i + 1) cnt++;
        }
        return cnt;
    }

    //    board上所有点回到自己应在位置的总长度
    public int manhattan() {
        return manhattan;
    }

    private int calManhattan(int titles[]) {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (titles[i * n + j] == 0) continue;
                int end_i = (titles[i * n + j] - 1) / n;
                int end_j = (titles[i * n + j] - 1) % n;
                cnt += Math.abs(end_i - i) + Math.abs(end_j - j);
            }
        }
        return cnt;
    }

    /*求一个可能情况*/
    private Board neighbor(int position) {
        int[] temp_titles = exch(titles.clone(), zero, position);
        int man = calManhattan(temp_titles);
        int ham = calHamming(temp_titles);
        int temp_zero = position;
        return new Board(temp_titles, n, temp_zero, ham, man);
    }

    //    输出所有可能情况 neighbor
    /*
    * 判断"0"点位置，如果不是边界则求出相应的board，添加到Arraylist中*/
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        if (zero / n != 0) {  // 不是第一行，顶部: zero可以上移
            neighbors.add(neighbor(zero - n));
        }
        if (zero / n != n - 1) { // 不是最后一行，底部: zero可以下移
            neighbors.add(neighbor(zero + n));
        }
        if (zero % n != 0) {  // 不是最左行，可以左移
            neighbors.add(neighbor(zero - 1));
        }
        if (zero % n != n - 1) { // 不是最右行，可以右移
            neighbors.add(neighbor(zero + 1));
        }

        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str.append(String.format("%d ", titles[i * n + j]));
            }
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(titles, board.titles);
    }

}
