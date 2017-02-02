package part1.week3.assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import week3.LineSegment;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by seven on 2017/1/28.
 * Fast方法计算同一条直线的点：
 * 将所有点的斜率排序，（大小相近的比较靠近）
 * 找到长度不小于4的线段
 */

// TODO: 2017/2/1 还有immutable 还没有解决。 尝试其他两种答案
public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    // 找到所有边
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new NullPointerException();
            for (int j = i + 1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
        Point[] ps = points.clone();
        Arrays.sort(ps);
        ArrayList<LineSegment> list = new ArrayList<>();    // 存储线段
        for (int i = 0; i < n; i++) {
            Point[] p = ps.clone();        //点的临时存储
            Arrays.sort(p, p[i].slopeOrder());   // 根据斜率排序，将斜率相近，相等的放在一起  每次只以一个点为参考点(基准点),

            // 开始找相等斜率
            // 默认以排序后的第一个为标准点：p[0]
            int j = 1;
            while (j < n - 2) {   // 每次最少比较两个点，所以边界点是数组倒数第二个
                int k = j;  // j记录斜率相等的起始点，k自增，找到相等的最后一个点
                double a1 = p[0].slopeTo(p[k]);
                double a2;
                k++;
                do {
                    if (k == n) {
                        k++;
                        break; // 跳出循环
                    }
                    a2 = p[0].slopeTo(p[k]);
                    k++;
                } while (a1 == a2);   // 当两个点相等时，一直遍历到找不到相等的点

                if (k - j < 4) {   // 终点位置-起始位置，长度小于4情况, 忽略
                    j++;
                    continue;
                }
                int len = k - j;
                Point[] equalpoints = new Point[len];   // 建立一个临时数组，存储斜率相等的点
                equalpoints[0] = p[0];  // 先存基准点

                for (int t = 1; t < len; t++) {   //t-1为相对位置，起始为j
                    equalpoints[t] = p[j + t - 1];
                }
                Arrays.sort(equalpoints);   // 将点排序，可以避免重复情况
                if (p[0] == equalpoints[0]) {
                    list.add(new LineSegment(equalpoints[0], equalpoints[len - 1]));
                }
                j = k - 1; // k代表: 处于斜率相等点后的第二个位置
            }
            lineSegments = list.toArray(new LineSegment[list.size()]);


        }

    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
