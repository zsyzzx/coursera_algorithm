package part1.week2.assignment;


import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by seven on 2017/1/22.
 * 使用链表实现
 * 双向队列,可以从前面添加，也可以从后面添加
 * 可双向移除
 * 遍历从队列first开始到最后
 */
public class Deque<Item> implements Iterable<Item> {
    private int count;   // 队列元素数量
    private Node<Item> first;
    private Node<Item> last;
    private Node<Item> null_node;   //哨兵点，内容全是null，判断边界

    private class Node<Item> {
        Item item;
        Node next;
        Node prev;

        public Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public Deque() {
        count = 0;
        first = new Node<>(null, null, null);
        last = new Node<>(null, null, null);
        null_node = new Node<>(null, null, null);
        first.next = last.prev;

    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("null pointer ");
        Node<Item> newFirst = new Node(item, null_node, first);   //将原有的节点，放在新节点后面
        if (isEmpty()) {
            first = newFirst;
            last = newFirst;
        } else {
            first.prev = newFirst;  //将新节点放在原有的结点前面
            first = newFirst;
        }
        count++; // 统计节点数量

    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("null pointer ");

        Node<Item> newLast = new Node(item, last, null_node);
        if (isEmpty()) {   //向空队列添加节点元素1
            first = newLast;
            last = newLast;
        } else {
            last.next = newLast;
            last = newLast;
        }
        count++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow 空队列");

        Item oldItem = first.item;
        first = first.next;
        if (first.item == null) {   //原来的队列只有一个元素，首尾部指针指向同一个节点，
            last = null_node;       // 将尾部置空
        } else {
            first.prev = null_node;  // 将新首部的前驱置空
        }

        count--;


        return oldItem;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow 空队列");
        Item oldItem = last.item;
        last = last.prev;
        if (last.item == null) {
            first = null_node;
        } else {
            last.next = null_node;
        }
        count--;
        return null;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null_node;
            // current.next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more item");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("不可用操作");
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < 6; i += 2) {
            deque.addFirst(i);
            deque.addLast(i + 1);
        }
        Iterator<Integer> it = deque.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println(" size: " + deque.size());

        for (int i = 0; i < 1; ++i) {
            deque.removeLast();
            deque.removeFirst();
        }
        StdOut.println("size: " + deque.size());
        it = deque.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println();


        for (int i = 0; i < 6; i += 2) {
            deque.addLast(i);
            deque.addFirst(i + 1);
        }
        it = deque.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println(" size: " + deque.size());
    }
}
