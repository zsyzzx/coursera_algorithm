package part1.week2.practice;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by seven on 2017/1/23.
 * 希尔排序：交换不相邻的元素来对数组的局部进行排序
 * 让数组中的任意间隔为h的元素都是有序的
 */
public class Shell {

    private Shell() {
    }

    //    先找合适的间隔h，按照间隔来进行排序，形成h有序数组
    public static void sort(Comparable[] a) {
    //        升序排列
            int n = a.length;
            int h = 1;
            while (h < n / 3) {
                h = 3 * h + 1; //经验证明：3*x+1 的间隔效果最好

            }
                while (h >= 1) {
                    for (int i = h; i < n; i++) {  //从最小满足间隔的元素开始,一直遍历到数组最后一个
                        /**
                         * h数组内的排序，保证h数组是有序的，
                         *  如果j<h 则代表h数组没有了其他j上的元素，
                         *  如果没有发生交换代表h数组的其他元素是有序的，h数组排序完成
                         *  否则遍历下一个h数组的元素
                         */
                        for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                            exchange(a, j, j - h);
                        }

                    }
                    assert isHsorted(a, h);
                    h /= 3;
                }
                assert isSorted(a);

        }

    public static void sort(Object[] a, Comparator comparator) {
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

    private static boolean isHsorted(Comparable[] a, int h) {
        for (int i = h; i < a.length; i++) {
            if (less(a[i], a[i - h])) return false;
        }
        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i]+",");
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

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


}
