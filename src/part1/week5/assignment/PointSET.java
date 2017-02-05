package part1.week5.assignment;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.TreeSet;

/**
 * Created by seven on 2017/2/2.
 * 暴力解法:遍历集合所有点，是否在矩形中，或找到距离最近的点
 * 使用Java默认的TreeSet存储：默认红黑树实现
 */
public class PointSET {
    private TreeSet<Point2D> set;  // 存储点集

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        set.add(p);

    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p :
                set) {
            p.draw();
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        SET<Point2D> inRect = new SET<>();

        for (Point2D p : set) {
            if (rect.contains(p)) inRect.add(p);
        }

        return inRect;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        Point2D nearest = null;
        double min = Double.MAX_VALUE;  // 存储两点最小距离，初始为最大值
//        double min = Double.POSITIVE_INFINITY;  // 存储两点最小距离，初始为最大值:正无穷
        for (Point2D q :
                set) {
            double distance_square = p.distanceSquaredTo(q);
            if (distance_square < min) {
                min = distance_square;
                nearest = q;
            }
        }
        return nearest;
    }
}
