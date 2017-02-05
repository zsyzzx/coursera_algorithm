package part1.week2.assignment;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by seven on 2017/1/22.
 * 随机队列，随机输入输出（使用顺序加入）
 * 常数级访问，使用数组，
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] items;

    public RandomizedQueue() {
        n = 0;
        items = (Item[]) new Object[2];

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; ++i)
            rq.enqueue(i);
        Iterator<Integer> it = rq.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println(" size: " + rq.size());
        it = rq.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println(" size: " + rq.size());

        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; ++i)
                rq.dequeue();
            it = rq.iterator();
            while (it.hasNext()) StdOut.print(it.next() + " ");
            StdOut.println(" size: " + rq.size());
        }
    }

    // 辅助类，只在内部使用，帮助实现功能
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;

//        alternative implementation  代替方法
//        items = java.util.Arrays.copyOf(items, capacity);
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Item 为空");
        if (n == items.length) resize(2 * items.length);
        items[n++] = item;
    }

    //    随机出列元素
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("空队列");
        int random = StdRandom.uniform(n); // 0~n-1
        Item item = items[random];
    /*
    * 填充random位置，用其他位置来补充，因为是随机的，所以可以是任意一个
    * n减一并要置null，所以使用n元素来填补
    * */
        items[random] = items[--n];  //最后一个
        items[n] = null;   //这个n是'n-1',即是原来数组的最后一个

        // shrink size of array if necessary
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("空队列");
        int random = StdRandom.uniform(n); // 0~n-1
        Item item = items[random];
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        //        int size = n;
        private int[] random;
        private int current;

        private RandomizedQueueIterator() {
            random = new int[n];
            for (int i = 0; i < n; i++) {
                random[i] = i;
            }
            StdRandom.shuffle(random);
            current = 0;  // 代表初始位置
        }

        /*
                * 第二个方法：使用辅助数组，随机排列固定的访问下标*/
        @Override
        public boolean hasNext() {
//            return size != 0;
//            2
            return current < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more item");
            /*
            * 这种实现方法，不能保证每个元素都只被访问一次*/
//            Item item = items[StdRandom.uniform(size)];
//            size--;

            Item item = items[random[current++]];

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("不可用操作");

        }

    }
}
