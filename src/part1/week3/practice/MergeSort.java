package part1.week3.practice;

import edu.princeton.cs.algs4.StdOut;
import part1.week2.practice.Insertion;

import java.util.Comparator;

/**
 * Created by seven on 2017/6/7.
 */
public class MergeSort {

    private MergeSort() {
    }


    //    合并
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid, hi);

//        转存到辅助数组
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

//        将辅助数组，即两个已经有序的数组，按按序放回原数组
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];  // 做数组已经放完
            else if (j > hi) a[k] = aux[i++];  // 右边数组已经放完
            else if (less(aux[i], aux[j])) a[k] = aux[i++];  // 比较左右两个数组，较小的值先放
            else a[k] = aux[j++];
        }
        assert isSorted(a, lo, hi);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
//        int mid1 = (lo + hi) >>> 1;

//        数组分成两半
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
//        合并数组
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
        assert isSorted(a);
    }

    public static final int CUTOFF = 7;

    private static void mergeX_merge(Comparable[] src, Comparable[] dest, int lo, int hi) {


    }



    public static void main(String[] args) {
        String[] strings = {"A", "B", "a", "d", "e", "Z", "y", "K", "I"};
        assert isSorted(strings);
        MergeSort.sort(strings);
        assert isSorted(strings);
        show(strings);
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

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
