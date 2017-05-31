package part1.week2.practice;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by seven on 2017/1/23.
 * 冒泡排序: 就是两两交换，最后得到一个有序的序列,大的往后移，一次循环的结果就是: 最大的在最右边
 * 这里是升序排序
 */
public class Bubble {
    private Bubble() {
    }

    public static void sort(Comparable[] a) {
        int n = a.length;
        /*
        * 在未排序的空间，排序，
        * 已经排序的空间不变，*/
//        for (int i = 0; i < n-1; i++) {
//            for (int j = 0; j < n - (i + 1); j++) {
//                if (less(a[j + 1], a[j])) {   // left(a[i])> right(a[i+1])
//                    exchange(a, j, j + 1);
//                }
//            }
//        }

//        boolean swapped ;
//        for (int i = 0; i < n-1; i++) {
//            swapped = false;
//            for (int j = 0; j < n - (i + 1); j++) {
//                if (less(a[j + 1], a[j])) {   // left(a[i])> right(a[i+1])
//                    exchange(a, j, j + 1);
//                    swapped = true;
//                }
//            }
//            if(!swapped) break;
//        }

//        仿照visualalg算法
        /*
        * 出现无序状况则交换，
        * 若已经排完序，则停止排序*/
        boolean swapped ;  //标志位，如果这个循环中,元素间没有发生交换则代表输入是有序的
        int numOfSorted = 0;  //记录已经排好序的元素数量
        do {
            swapped = false;
            for (int i = 0; i < n - numOfSorted-1; i++) {
                if (less(a[i + 1], a[i])) {
                    exchange(a, i, i + 1);
                    swapped = true;
                }
                show(a);
            }
            numOfSorted++;
        } while (swapped);
        assert isSorted(a);

    }


    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = 0; j < n - (i + 1); j++) {
//                if (less(comparator, a[j + 1], a[j])) {   // left(a[i])> right(a[i+1])
//                    exchange(a, j, j + 1);
//                }
//
//            }
//        }
        boolean swapped = false;  // 标记是否已经排好序了
        int numOfSorted = 0;
        do {
            swapped = false;
            for (int i = 0; i < n - numOfSorted-1; i++) {
                if (less(comparator, a[i + 1], a[i])) {
                    exchange(a, i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
        assert isSorted(a, comparator);
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
            StdOut.print(a[i]+" ");
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
