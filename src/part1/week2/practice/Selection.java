package part1.week2.practice;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by seven on 2017/1/23.
 * 升序排序，increment
 * 选择排序: 找到最小放到相应的位置
 */
public final class Selection {

    private Selection() {
    }  //不能实例化

    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;  // 假设当前位置为最小
            for (int j = i + 1; j < n; j++) {
                if (less(a[j], a[min])) {
                    min = j;    // 如果找到更小的则更换下标
                }
            }
            exchange(a, i, min);
            assert isSorted(a, 0, i);   // 判断a在i之前是否已经排好序
        }
        assert isSorted(a);         // 断言a是否已经排好序
    }

    /*
    * 使用comparator排序，自定义条件排序*
    */
    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(comparator, a[j], a[min])) min = j;
            }
            exchange(a, i, min);
            assert isSorted(a, comparator, 0, i);
        }
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
            StdOut.print(a[i] + ",");
        }
        StdOut.print('\n');
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

    public static void main(String[] args) {
        String[] s = {"ab", "gey", "China", "USA", "Guandong", "Jiangsu", "Shenzhen", "hello"};
        Integer[] a = {234, 46, 12, 21, 76, 54, 3, 23, 125, 76, 65};
//        String a[] = StdIn.readAllStrings();
//        Selection.sort(a);
//        Selection.show(a);
        show(a);
        Bubble.sort(a);
        show(a);
//        Selection.sort(s);
        show(s);
        Bubble.sort(s);
        show(s);

    }


}
