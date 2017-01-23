package part1.week2.practice;

/**
 * Created by seven on 2017/1/22.
 */

//TODO 测试    Selectionsort Insertsort Shellsort Bubblesort

public class learnSort {


//    Selection
//    Selection.sort(s);
public static void main(String[] args) {
    String[] s = {"ab", "gey", "China", "USA", "Guandong", "Jiangsu", "Shenzhen", "hello"};
    Integer[] a = {234,46,12,21,76,54,3,23,125,76,65};
    Selection.sort(a);
    Selection.sort(s);
    System.out.println(a);
}

}
