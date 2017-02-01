package part1.week3.assignment;
//import part1.week3.test.LineSegment21

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import week3.LineSegment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by seven on 2017/1/28.
 * Brute force：
 * To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q, between p and r, and between p and s are all equal.
 *
 */
public class BruteCollinearPoints {
    private LineSegment[]  lineSegments;
    /*
    * finds all line segments containing 4 points*/
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("没有节点");
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new NullPointerException("这个节点为空");
            for (int j = i+1; j < n; j++) {
                if (points[i].compareTo(points[j] )== 0) throw new IllegalArgumentException("节点重复");
            }
        }

        /*
        * 暴力解法，遍历所有点，产生线段，并存储到List临时存储中*/
        ArrayList<LineSegment> lineSegmentsList = new ArrayList<>();    // 临时存储线段
        Point[] back_points = points.clone();   // 备份点
        Arrays.sort(back_points);  //将点排序
        for (int i = 0; i < n; i++) {   // 第一个节点
            for (int j = i+1; j < n; j++) {   // 第二个节点
                for (int k = j+1; k < n; k++) {   // 第三个节点
                    for (int l = k+1; l < n; l++) {
                        Point[] p = {points[i], points[j], points[k], points[l]};
//                        Point[] p = new Point[4];  // 存储四个将要构成线段的结点
                        double a1 = p[0].slopeTo(p[1]);
                        double a2 = p[0].slopeTo(p[2]);
                        if (a1 != a2 ) continue;
                        double a3 = p[0].slopeTo(p[3]);
                        if (a1 == a3){
                            Arrays.sort(p);
                            lineSegmentsList.add(new LineSegment(p[0], p[3]));
                        }

                    }
                }
            }
        }
        lineSegments = lineSegmentsList.toArray(new LineSegment[lineSegmentsList.size()]);  // 转换数组

    }
    /**
     * return number of line segments
    * */
    public int numberOfSegments(){
        return lineSegments.length;
    }

    public LineSegment[] segments(){
        return lineSegments.clone();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
