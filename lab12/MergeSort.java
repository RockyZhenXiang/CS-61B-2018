
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Quick;
import org.junit.Test;

import java.util.Random;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> res = new Queue<>();
        for (Item item: items) {
            Queue<Item> que = new Queue<>();
            que.enqueue(item);
            res.enqueue(que);
        }
        return res;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {

        Queue<Item> res = new Queue<>();

        while(!q1.isEmpty() || !q2.isEmpty()) {
            Item next = getMin(q1, q2);
            res.enqueue(next);
        }

        return res;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> single = makeSingleItemQueues(items);
        while (single.size() > 1) {
            Queue<Item> first = single.dequeue();
            Queue<Item> second = single.dequeue();
            Queue<Item> merged = mergeSortedQueues(first, second);
            single.enqueue(merged);
        }
        Queue<Item> res = single.dequeue();
        return res;
    }

    @Test
    public void testSingleItemQueues() {
        Queue test = new Queue<>();
        Random rand = new Random(10);
        for (int i= 0; i < 10; i += 1) {
            test.enqueue(rand.nextInt(20));
        }
        System.out.println(test.toString());
        Queue<Integer> single = makeSingleItemQueues(test);
        System.out.println(single);

    }

    @Test
    public void testMerge() {
        Queue test = new Queue<>();
        Queue test2 = new Queue();
        for (int i= 0; i < 5; i += 1) {
            test.enqueue(i);
            test2.enqueue(i * i);
        }
        System.out.println(test.toString());
        System.out.println(test2.toString());
        System.out.println(mergeSortedQueues(test, test2).toString());
    }

    public static void main(String[] args) {
        Queue<Integer> test = new Queue<>();
        Random rand = new Random(10);
        for (int i= 0; i < 10; i += 1) {
            test.enqueue(rand.nextInt(20));
        }
        System.out.println(test.toString());

        Queue<Integer> sorted = mergeSort(test);
        System.out.println(test.toString());
        System.out.println(sorted.toString());
    }
}
