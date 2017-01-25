package part1.week2.assignment;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by seven on 2017/1/23.
 */
public class Permutation {
    public static void main(String[] args) {
        // 代表要输出的值的数量
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        String[] items = StdIn.readAllStrings();
        StdRandom.shuffle(items);
        for (int i = 0; i < count; i++) {
            randomizedQueue.enqueue(items[i]);
        }
        for (int i = 0; i < count; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }



    }
}
