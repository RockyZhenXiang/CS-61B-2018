import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Quick;
import org.junit.Test;

import java.util.Random;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     * The items in q3 will be catenated after all of the items in q2.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2, Queue<Item> q3) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }

        for (Item item: q3) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        for (Item item: unsorted) {
            if (item.compareTo(pivot) < 0) {
                less.enqueue(item);
            } else if (item.compareTo(pivot) == 0) {
                equal.enqueue(item);
            } else {
                greater.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {

        if (items == null || items.size() < 2) {
            return items;
        }
        Item pivot = getRandomItem(items);
        Queue<Item> less = new Queue<>();
        Queue<Item> equal= new Queue<>();
        Queue<Item> grater = new Queue<>();

        partition(items, pivot, less, equal, grater);

        return catenate(quickSort(less), equal, quickSort(grater));
    }


    @Test
    public void testPar() {
        Queue<Integer> test = new Queue<>();
        Random rand = new Random(10);
        for (int i= 0; i < 10; i += 1) {
            test.enqueue(rand.nextInt(20));
        }
        System.out.println(test.toString());

        int pivot = getRandomItem(test);
        Queue<Integer> less = new Queue<>();
        Queue<Integer> equal= new Queue<>();
        Queue<Integer> grater = new Queue<>();

        partition(test, pivot, less, equal, grater);
        System.out.println(test.toString());
        System.out.println(pivot);
        System.out.println(less.toString());
        System.out.println(equal.toString());
        System.out.println(grater.toString());
    }


    public static void main(String[] args) {
        Queue<Integer> test = new Queue<>();
        Random rand = new Random(10);
        for (int i= 0; i < 10; i += 1) {
            test.enqueue(rand.nextInt(20));
        }
        System.out.println(test.toString());

        Queue<Integer> sorted = quickSort(test);
        System.out.println(test.toString());
        System.out.println(sorted.toString());
    }
}
