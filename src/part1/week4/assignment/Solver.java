package part1.week4.assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by seven on 2017/2/1.
 * 8puzzle的解题方法：找到最佳的走法，即找到最接近目标的走法
 * 评判标准：当前位置与目标位置的差距：manhattan 或 hamming  ，这里实现的是manhattan方法
 *
 */
public class Solver {
    //    private Board board;
    private MinPQ<SearchNode> pq, twin_pq;
    private int movesToGoals = 0;


    public Solver(Board initial) {
//        this.board = initial;
        if (initial == null) throw new NullPointerException("空Board");

//        Game tree
        pq = new MinPQ<>();

//        twin_board 的优先队列
        twin_pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        twin_pq.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode current, twin_current;

//        当两个队列都还没有到达目标，找下一个searchnode
        while (!pq.min().board.isGoal() && !twin_pq.min().board.isGoal()) {
            current = pq.delMin();
            twin_current = twin_pq.delMin();

//            找下一个searchnode,加入到minPq优先队列中
            for (Board nb :
                    current.board.neighbors()) {
//                如果当前节点的前一节点不为空，并且neighbor等于前一节点，不加入队列
                if (current.prev != null && nb.equals(current.prev.board)) continue;
                pq.insert(new SearchNode(nb, current.moves + 1, current));
            }
            for (Board t_nb :
                    twin_current.board.neighbors()) {
                if (twin_current.prev != null && t_nb.equals(twin_current.prev.board))
                    continue;
                twin_pq.insert(new SearchNode(t_nb, twin_current.moves + 1, twin_current));
            }
        }
        if (!pq.min().board.isGoal()) {
//            -1步数代表不可解
            movesToGoals = -1;
        } else {
            movesToGoals = pq.min().moves;
        }

    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    //    返回求解的最小步数
    public int moves() {
        return movesToGoals;
    }

    //    根据求解的步数来判断是否可解：-1代表不可解
    public boolean isSolvable() {
        return movesToGoals != -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> solution = new Stack<>();
        SearchNode path = pq.min();
        while (path != null) {
            solution.push(path.board);
            path = path.prev;
        }
        return solution;
    }

    /*SearchNode,插入优先队列里的，*/
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode prev;

        private SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        //        实现manhattan优先级比较，
//        可以使用hamming优先级比较：实现一个Comparator类
        @Override
        public int compareTo(SearchNode that) {
            int manhattan_priority1 = this.moves + this.board.manhattan();
            int manhattan_priority2 = that.moves + that.board.manhattan();
            return manhattan_priority1 - manhattan_priority2;

        }
    }
}
