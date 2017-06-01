package part1.week2.practice;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by seven on 2017/1/23.
 * <p>
 * 升序排序  ascending
 * 插入排序：将当前位置放在恰当位置
 */
public class Insertion {

    private Insertion() {
    }


    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {  //当前要插入元素的位置
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) { //将各个元素放到恰当的位置，若不用交换则代表所有元素都在恰当的位置
                exchange(a, j, j - 1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);

    }

    public static void sort(Object[] a, Comparator comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && less(comparator, a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
            assert isSorted(a, comparator, 0, i);
        }
        assert isSorted(a, comparator);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
            assert isSorted(a, lo, hi);
        }
        assert isSorted(a);
    }

    public static void sort(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(comparator, a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
            assert isSorted(a, comparator, lo, hi);
        }
        assert isSorted(a, comparator);
    }

    /*
    * 不改变原来数组，产生一个排好序数组的下标序列*/
    public static void indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); j--) {
                exchange(index, j, j - 1);
            }
        }
    }

    // TODO: 2017/3/5 优化的插入排序算法，减少比较次数 ：insertsortX();

    /**
     * 优化指南：较大的元素右移一位只需访问一次数组
     * 先找出最小元素并放在数组最左边(哨兵，去掉内循环的判断条件 j>0) 规避边界测试
     *
     * @param a
     */
    public static void InsertionX(Comparable[] a) {
        int n = a.length;
//        规避边界测试,找到最小的值放到最左边
//        for (int i =0;i<n-1;i++){   //把最大放最右边了
//            if(less(a[i+1],a[i])){
//                exchange(a,i,i+1);
//            }
//        }
//        从最右边开始找最小值
        // put smallest element in position to serve as sentinel
        int exchanges = 0; //交换次数
        for (int i = n - 1; i > 0; i--) {
            if (less(a[i], a[i - 1])) {
                exchange(a, i, i - 1);
                exchanges++;
            }
        }
        if (exchanges == 0) return;  // 没有发生交换则代表数组有序
//        show(a);
//        System.out.println();

//        先为准备插入的元素准备好位置，实现了较大的元素右移一位只需访问一次数组
        for (int i = 2; i < n; i++) {
            Comparable temp = a[i];  // 空间换时间
            int j = i;
//            while (j > 0 && less(temp, a[j - 1])) {
            while (less(temp, a[j - 1])) {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = temp;
        }

    }

    public static void InsertionXWithoutSentinel(Comparable[] a) {
        int n = a.length;
        for (int i = 2; i < n; i++) {
            Comparable temp = a[i];  // 空间换时间
            int j = i;
            while (j > 0 && less(temp, a[j - 1])) {
                a[j] = a[j - 1];
                j--;
            }
            a[j] = temp;
        }
    }

    /*
    * optimize */
    // TODO: 2017/1/23  添加优化代码，二分查找，优化插入等，参考《算法》书里的示例代码

    public static void BinaryInsertion(Comparable[] a) {

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

    private static void exchange(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Integer[] a = {234, 46, 12, 21, 76, 54, 3, 23, 125, 76, 65};
        String[] s = {"ab", "gey", "China", "USA", "Guandong", "Jiangsu", "Shenzhen", "hello"};

        InsertionX(a);
        show(a);
        System.out.println();
        InsertionXWithoutSentinel(s);
        show(s);
    }


}
