package part1.week3.practice;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

/**
 * alg4 若干快速排序实现方法
 * Created by seven on 2017/6/7.
 */

// todo 添加注释，做笔记
public class QuickSort {

    private QuickSort() {
    }


    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exchange(a, i, j);
        }
//        exchange(a,i,lo);
        exchange(a, j, lo);    //j与i有区别么
//        保证交换的两个元素符合分区规定(分界点)，即a[j]< a[low]
//        而 a[i] >a{low}
        return j;
//        return i;
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
//        a[j] 是在正确位置的
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);


    }

    public static void Quick3Way(Comparable[] a) {
        StdRandom.shuffle(a);
        Quick3Way(a, 0, a.length - 1);
        assert isSorted(a);

    }

    /**
     * 对重复值的优化  Duplicate keys
     * a[i] < v a[lt]与a[i]交换 i\lt +1
     * a[i] > v a[gt]与a[i] 交换  gt-1
     * a[i] = v  i+1
     *
     * @param a  数组
     * @param lo 最小
     * @param hi 高
     */
    private static void Quick3Way(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = v.compareTo(a[i]);
            if (cmp > 0) exchange(a, i++, lt++);
            else if (cmp < 0) exchange(a, i, gt--);
//            int cmp = a[i].compareTo(v);
//            if (cmp < 0) exchange(a, i++, lt++);
//            else if (cmp > 0) exchange(a, i, gt--);
            else i++;
        }
        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
        Quick3Way(a, lo, lt - 1);
        Quick3Way(a, gt + 1, hi);
        assert isSorted(a, lo, hi);

    }

    /*
    * 使用快排找出第k的的元素
    **/
    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k > a.length) {
            throw new IndexOutOfBoundsException("超出边界");
        }
        StdRandom.shuffle(a);
        int low = 0, high = a.length - 1;
        while (high > low) {
            int i = partition(a, low, high);
            if (i > k) high = i - 1;
            else if (i < k) low = i + 1;
            else return a[i];
        }
        return a[low];
    }


    public static void QuickX(Comparable[] a) {
        QuickX(a, 0, a.length - 1);
    }

    // cutoff to insertion sort, must be >= 1
    private static final int INSERTION_SORT_CUTOFF = 8;

    // cutoff to median-of-3 partitioning
    private static final int MEDIAN_OF_3_CUTOFF = 40;

    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    //     return the index of the median element among a[i], a[j], and a[k]
    private static int median3(Comparable[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
                (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
                (less(a[k], a[j]) ? j : less(a[i], a[k]) ? i : k));
    }

    /**
     * 使用插入排序等
     * 选择合适的点: 中位数3 median-of-3 partitioning
     *
     * @param a
     */
    private static void QuickX(Comparable[] a, int low, int high) {
        int n = high - low + 1;

//        不同规模的排序使用不同的排序策略，选择不同的中心轴
        if (n < INSERTION_SORT_CUTOFF) {
            insertionSort(a, low, high);
            return;
        } else if (n < MEDIAN_OF_3_CUTOFF) {   // 轴点位于a[low]
            int m = median3(a, low, low + n / 2, high);
            exchange(a, m, low);
        } else {
//            分为9个点，选出中间点
            int eps = n / 8;
            int mid = low + n / 2;
            int m1 = median3(a, low, low + eps, low + eps + eps);
            int m2 = median3(a, mid - eps, mid, mid + eps);
            int m3 = median3(a, high - eps - eps, high - eps, high);
            int ninther = median3(a, m1, m2, m3);
            exchange(a, low, ninther);
        }

        int i = low, j = high + 1;
        int p = low, q = high + 1;
        Comparable v = a[low];

//        普通快排，需要特别处理相等的元素
//        将相等的key分别放在两边，
//        排序完成后，再将相等的key放在相应的位置

        while (true) {
            while (less(a[++i], v))
                if (i == high) break;
            while (less(v, a[--j]))
                if (j == low) break;
            if (i == j && eq(v, a[i]))
                exchange(a, ++p, i);
            if (i >= j) break;

            exchange(a, i, j);
            if (eq(v, a[i])) exchange(a, i, ++p);
            if (eq(v, a[j])) exchange(a, j, --q);
        }
        i = j + 1;
        for (int k = low; k <= p; k++) {
            exchange(a, k, j--);
        }
        for (int k = high; k >= q; k--) {
            exchange(a, k, i++);
        }
        QuickX(a, low, j);
        QuickX(a, i, high);
    }

    public static void DualPivot(Comparable[] a){
        DualPivot(a,0,a.length-1);
    }

    /*
    * 双轴快排，两个轴点，分别快排*/
    private static void DualPivot(Comparable[] a, int lo, int hi) {
        if (hi<=lo) return;

        if (less(a[hi],a[lo])) exchange(a,lo,hi);

        int lt = lo+1, gt = hi-1;

        int i = lo+1;
        while (i<= gt){
            if(less(a[i],a[lo])) exchange(a,i++,lt++);
            else if (less(a[hi],a[i])) exchange(a,i,gt--);
            else i++;
        }
        exchange(a,lo,--lt);
        exchange(a,hi,++gt);

        DualPivot(a,lo,lt);
        if(less(a[lt],a[gt])) DualPivot(a,lt+1,gt-1);
        DualPivot(a,hi,gt);

    }


    public static void main(String[] args) {
        Integer[] a = {234, 46, 12, 21, 76, 54, 3, 23, 125, 76, 65};
//        Integer[] a1 = {234, 46, 12, 21, 76, 54, 3, 23, 125, 76, 65};
//        String[] s = {"ab", "gey", "China", "USA", "Guandong", "Jiangsu", "Shenzhen", "hello"};
//        sort(a);
//        System.out.println(select(a, 3));
//        show(a);
        DualPivot(a);
        show(a);
//        System.out.println("\nquick");
//        Quick3Way(a1);
//        show(a1);
//        sort(s);
//        show(s);
//        Double[] a = new Double[100];
//        for (int i = 0; i < 100; i++) {
//             a[i] = StdRandom.uniform();
//        }
//        show(a);
//        QuickX(a);
//        System.out.println("排序后=====");
//        show(a);

    }

    /*
      * check if array is sorter*/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }

    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    private static boolean isSorted(Object[] a, Comparator comparator, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            if (less(comparator, a[i], a[i - 1])) return false;
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
    }


    /*
    * Helper sorting function*/
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;   // is v < w? using comparator
    }

    private static boolean eq(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


}
