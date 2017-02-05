package part1.week5.assignment;

import edu.princeton.cs.algs4.*;

/**
 * Created by seven on 2017/2/2.
 * KdTree 用来存储(0,1)区间的点，并实现：rangeSearch(范围搜索)，nearest(最近)方法，参照RB BST
 * 类似空间存储: 把一个平面根据需要划分成上下左右四部分，按照位置把点存进树中
 * 这里实现的是2维树，2dTree，高维度的树怎么实现？
 */
public class KdTree {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private Node root;
    private int size;
    private RectHV unit;  // 范围在0-1之间

    //        为什么要设置整个类，整棵树可以访问
    private double min;
    private Point2D nearest;

    public KdTree() {
        root = null;
        size = 0;
        unit = new RectHV(0, 0, 1, 1);
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdTree = new KdTree();
//        RectHV test = new RectHV(0,0,1,1);
//        StdDraw.setPenRadius(.01);
//        StdDraw.setPenColor(StdDraw.RED);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
//            p.draw();
            kdTree.insert(p);
        }
        kdTree.draw();
        System.out.println(kdTree.size());

    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    /*
    * 为当前节点n创建左或右子树矩形区域，
    * 如果子树不为空，返回子树矩形
    * 如果是新建节点，则新建矩形区域
    * */
    private RectHV childRect(Node n, boolean isLeft) {
        RectHV rect;  // 子矩形
        RectHV prev_rect = n.rect;// 父节点矩形
        if (isLeft) {
            if (n.lb != null) return n.lb.rect;
//            垂直线的左子树，左边区域，改变x的最大边
            if (n.isVertical) {
                rect = new RectHV(prev_rect.xmin(), prev_rect.ymin(), n.point.x(), prev_rect.ymax());
            } else {
//                水平线的左子树，下面区域，改变y的最大边
                rect = new RectHV(prev_rect.xmin(), prev_rect.ymin(), prev_rect.xmax(), n.point.y());
            }
        } else {
//            右边区域
            if (n.rt != null) return n.rt.rect;
            if (n.isVertical) {
//                垂直线的右子树:右边区域，改变x的最小边
                rect = new RectHV(n.point.x(), prev_rect.ymin(), prev_rect.xmax(), prev_rect.ymax());
            } else {
//                水平线的右子树:上面区域，改变y的最小边
                rect = new RectHV(prev_rect.xmin(), n.point.y(), prev_rect.xmax(), prev_rect.ymax());
            }
        }

        return rect;
    }

    private Node put(Node x, Point2D p, boolean orientation, RectHV rect) {
        if (x == null) {
            size++;
            return new Node(p, rect, orientation);
        }

       /* 去除重复点
       * 如果用相同的点，返回当前节点
       * */
        if (x.point.equals(p)) return x;
        int cmp = x.compareTo(p);
        if (cmp > 0) {   // x>p  插入节点小于父节点：左边
            x.lb = put(x.lb, p, !orientation, childRect(x, true));
//            x.lb = put(x,p,!orientation,childRect(x,true));
        } else {
//            x<p 插入节点大于父节点: 右边
            x.rt = put(x.rt, p, !orientation, childRect(x, false));
//            x.rt = put(x,p,!orientation,childRect(x,false));
        }
        return x;


    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        root = put(root, p, VERTICAL, unit);

    }

    private Point2D get(Node x, Point2D p) {
        while (x != null) {
            int cmp = x.compareTo(p);
            // 左子树
            if (cmp > 0) x = x.lb;
//            右子树
            else if (cmp < 0) x = x.rt;
            else return x.point;
        }
        return null;
    }
/*
* 递归实现方法*/
//    private Node get(Point2D p, Node n) {
//        if (n == null) return null;
//        if (n.p.equals(p)) return n;
//        int cmp = n.compareTo(p);
//        if (cmp > 0) return get(p, n.left);
//        else return get(p, n.right);
//    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return get(root, p) != null;
    }

    public void draw() {
        draw(root);

    }

    private void drawRect(Node axisNode) {
        StdDraw.setPenRadius();
        if (axisNode.isVertical) {  //垂直线
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(axisNode.point.x(), axisNode.rect.ymin(), axisNode.point.x(), axisNode.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(axisNode.rect.xmin(), axisNode.point.y(), axisNode.rect.xmax(), axisNode.point.y());
        }
    }

    private void draw(Node nodeToDraw) {
        if (nodeToDraw == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        nodeToDraw.point.draw();
        drawRect(nodeToDraw);
        draw(nodeToDraw.lb);
        draw(nodeToDraw.rt);

    }

    /*
    * in 存储范围内的点
    * rect 代表矩形范围
    * n 代表当前要判断的结点
    * 要判断矩形在当前节点的位置，然后再搜索左右子树
    * 矩形和线有5种情况:分离（左右），相切（左右），相交*/
    private void range(SET<Point2D> in, RectHV rect, Node n) {
        if (n == null || !n.rect.intersects(rect)) {
            return;
        }
//       在左子树搜索：水平线(bottom) y>ymin   vertical(left) x>xmin  只要矩形在当前点的左边就要搜索左子树，所以
//        在右子树:horizontal(top) y<ymax  vertical(right) x<xmax
        boolean left = !n.isVertical && (n.point.y() > rect.ymin()) || n.isVertical && (n.point.x() > rect.xmin());
        boolean right = !n.isVertical && (n.point.y() < rect.ymax()) || n.isVertical && (n.point.x() < rect.xmax());

//        等号情况怎么取？
//        boolean left = !n.isVertical && (n.point.y() > rect.ymin()) || n.isVertical && (n.point.x() > rect.xmin());
//        boolean right = !n.isVertical && (n.point.y() < rect.ymax()) || n.isVertical && (n.point.x() < rect.xmax());
        if (left) range(in, rect, n.lb);
        if (rect.contains(n.point)) {
            in.add(n.point);
        }
        if (right) range(in, rect, n.rt);

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }
        SET<Point2D> in = new SET<>();
        range(in, rect, root);
        return in;
    }

    private void nearest(Point2D find, Node parent) {
//        当前节点为空，或者点到节点相关矩形的距离大于最小距离
        if (parent == null || parent.rect.distanceSquaredTo(find) >= min) return;
        double disSqu = parent.point.distanceSquaredTo(find);
        if (disSqu < min) {
            nearest = parent.point;
            min = disSqu;
        }
        int cmp = parent.compareTo(find);
        if (cmp > 0) {   // find < parent 先找左子树
            nearest(find, parent.lb);
            nearest(find, parent.rt);
        } else {
            nearest(find, parent.rt);
            nearest(find, parent.lb);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        min = Double.POSITIVE_INFINITY; // 初始为最大值
        nearest = null;
        nearest(p, root);
        return nearest;
    }

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node lb;  // left or bottom
        private Node rt;  // right or up
        private boolean isVertical;  // true代表垂直，false代表水平

        public Node(Point2D point, RectHV rect, boolean isVertical) {
            this.point = point;
            this.rect = rect;
            this.isVertical = isVertical;
        }


        public int compareTo(Point2D that) {
            if (isVertical) {   //垂直，比较x轴
                if (this.point.x() < that.x()) return -1;
                if (this.point.x() > that.x()) return +1;
            } else {  // 水平，比较yz轴
                if (this.point.y() < that.y()) return -1;
                if (this.point.y() > that.y()) return +1;
            }
            return 0;
        }
    }
}
