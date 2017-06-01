package part1.week2.practice;


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by seven on 2017/1/22.
 * 根据alg4里面的算法比较代码，
 * 输入参数为： alg1 alg2 N T
 */


public class learnSort {



    public static double time(String alg, Double[] a, boolean[] success,int alg_sequence) {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("Insertion")) {
            Insertion.sort(a);
//            System.out.println(alg + ":success");
            success[alg_sequence] = true;
        }
        if (alg.equals("InsertionX")) {
            Insertion.InsertionX(a);
//            System.out.println(alg + ":success");
            success[alg_sequence] = true;
        }
        if (alg.equals("BinaryInsertion")) {
            Insertion.BinaryInsertion(a);
//            System.out.println(alg + ":success");
            success[alg_sequence] = true;
        }
        if (alg.equals("Insertion1")){
            Insertion.InsertionXWithoutSentinel(a);
            success[alg_sequence] = true;
        }
        if (alg.equals("Shell")) {
            Shell.sort(a);
//            System.out.println(alg + ":success");
            success[alg_sequence] = true;
        }
        if (alg.equals("Selection")) {
            Selection.sort(a);
//            System.out.println(alg + ":success");
            success[alg_sequence] = true;
        }
        if (alg.equals("Quick")) {
            Selection.sort(a);
//            System.out.println(alg + ":success");
            success[alg_sequence] = true;
        }
        if (alg.equals("Merge")) {
            Selection.sort(a);
//            System.out.println(alg + ":success");
            success[alg_sequence] = true;
        }

        return timer.elapsedTime();
    }

    public static double timeRandomInput(String alg, int N, int T, boolean[] alg_success,int alg_sequence) {
        double total = 0;
        Double[] a = new Double[N];

        for (int t = 0; t < T; t++) {
            for (int i = 0; i < N; i++) {
                a[i] = StdRandom.uniform();
            }
            total += time(alg, a, alg_success,alg_sequence);

        }
        System.out.println(alg + "  :" + total + " ");
        return total;
    }

    /**
     * @param args 参数说明：一共四个参数
     *             args[0] args[1] 是排序算法的名字
     *             args[2] 是数组规模
     *             args[3] 是测试次数
     */
    public static void main(String[] args) {
//        String[] s = {"ab", "gey", "China", "USA", "Guandong", "Jiangsu", "Shenzhen", "hello"};
//        Integer[] a = {234, 46, 12, 21, 76, 54, 3, 23, 125, 76, 65};
//        Integer[] a1 = {234, 46, 12, 21, 76, 54, 3, 23, 125, 76, 65};
//        Integer[] a2 = {1,2,3,4,5};

        boolean []alg_success = {false,false};

        String alg1 = args[0];
        String alg2 = args[1];
        int N = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);
        double t1 = timeRandomInput(alg1, N, T, alg_success,0);
        double t2 = timeRandomInput(alg2, N, T, alg_success,1);

        if (alg_success[0] && alg_success[1]) {
            System.out.printf("For %d random Double\n %s is", N, alg1);
            System.out.printf("%.2f times faster than %s \n", t2 / t1, alg2);
        }else {
            System.out.println("算法名称错误");
        }

    }

}
