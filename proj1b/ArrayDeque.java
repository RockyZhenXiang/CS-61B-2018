public class ArrayDeque<T> implements Deque<T>{
    /**
     * Invariants
     * Array based data structure
     * size = number of items
     * nextFirst always points before at the first item
     * nextLast always points at the next index after the last item
     */


    // Variables
    private T[] items;
    private int size;
    private double usageRatio;  //Used to determined if the AList needed to be shorten.
    private int nextFirst;
    private int nextLast;


    /**
     * Creates an empty list.
     */
    public ArrayDeque() {
        T[] a = (T[]) new Object[8];
        items = a;
        size = 0;
        usageRatio = 0.0;
        nextFirst = items.length - 1;
        nextLast = 0;

    }

    /**
     * Resizing the array to size = capacity if to many item is added or to many empty boxes
     */

    private void reSize(int capacity) {
        if (size == 0){
            return;
        }
        T[] a = (T[]) new Object[capacity * 2];
        int firstIndex;
        int lastIndex;
        if (nextFirst + 1 - items.length == 0) {
            firstIndex = 0;
        } else {
            firstIndex = nextFirst + 1;
        }
        if (nextLast == 0){
            lastIndex = items.length - 1;
        } else {
            lastIndex = nextLast - 1;
        }

        if (firstIndex > lastIndex) {
            System.arraycopy(items, firstIndex, a, 0, items.length - firstIndex );
            System.arraycopy(items, 0, a, items.length - firstIndex, lastIndex + 1);
        } else {
            System.arraycopy(items, firstIndex, a, 0, size);
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
        usageRatio = (double) size / (double) items.length;
    }

    /**
     * Inserts X into the front of the list.
     */
    @Override
    public void addFirst(T x){
        if (size == items.length) {
            reSize(size*2);
        }
        items[nextFirst] = x;
        size += 1;
        if (nextFirst == 0) {
            nextFirst = items.length -1;
        } else {
            nextFirst -= 1;
        }
        usageRatio = (double) size / (double) items.length;
    }

    /**
     * Inserts X into the back of the list.
     */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            reSize(size * 2); // by using multiplication, the consuming time is shorten by log(n).
        }
        items[nextLast] = x;
        size += 1;
        if (nextLast != items.length - 1) {
            nextLast += 1;
        }else{
            nextLast = 0;
        }
        usageRatio = (double)size / (double)items.length;
    }
    /**
     * Check if the AList is empty
     */
    @Override
    public boolean isEmpty(){
        return size == 0;
    }


    /**
     * Returns the number of items in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    @Override
    public void printDeque(){
        if (size == 0) return;
        int i = nextFirst + 1;
        while (i != nextLast) {
            System.out.print(items[i] + " ");
            if (i == items.length-1) {
                i = 0;
            } else {
                i += 1;
            }
        }
    }


    /**
     * Gets the ith item in the list (0 is the front).
     */
    @Override
    public T get(int i) {
        if (i >= size){
            return null;
        }
        int trueIndex = nextFirst + i + 1;
        if (trueIndex>items.length-1){
            trueIndex -= items.length;
        }
        return items[trueIndex];
    }


    /**
     * Deletes item from back of the list and
     * returns deleted item.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int lastIndex;
        if (nextLast == 0){
            lastIndex = items.length - 1;
        }else{
            lastIndex = nextLast - 1;
        }
        T res = items[lastIndex];
        items[lastIndex] = null; //not necessary, because the user cannot reach it. But in genetic array, be setting it into null, we can save memory.
        size -= 1;
        nextLast = lastIndex;
        usageRatio = (double)size / (double)items.length;
        if (usageRatio < 0.25) reSize((int) (items.length * 0.25));
        return res;
    }

    /**
     * Deletes item from the head of the list and
     * returns deleted item.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int firstIndex;
        if (nextFirst == items.length - 1){
            nextFirst = 0;
        }else{
            nextFirst += 1;
        }
        T res = items[nextFirst];
        items[nextFirst] = null; //not necessary, because the user cannot reach it. But in genetic array, be setting it into null, we can save memory.
        size -= 1;

        usageRatio = (double)size / (double)items.length;
        if (usageRatio < 0.25) reSize((int) (items.length * 0.25));
        return res;
    }


    private static void main(String[] args) {
        ArrayDeque AL = new ArrayDeque();
        AL.addLast("Rocky");
        AL.addLast("Ricky");
        AL.addLast("Hailie");
        AL.addFirst("Rocky");
        AL.addFirst("Ricky");
        AL.addFirst("Hailie");
        AL.addLast("Rocky");
        AL.addLast("Ricky");
        AL.addLast("Hailie");
        AL.addFirst("Rocky");
        AL.addFirst("Ricky");
        AL.addFirst("Hailie");
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());
        System.out.println(AL.removeLast());

        AL.addLast("Rocky");
        AL.addLast("Ricky");
        AL.addLast("Hailie");
        AL.addFirst("Rocky");
        AL.addFirst("Ricky");
        AL.addFirst("Hailie");
        AL.addLast("Rocky");
        AL.addLast("Ricky");
        AL.addLast("Hailie");
        AL.addFirst("Rocky");
        AL.addFirst("Ricky");
        AL.addFirst("Hailie");
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());
        System.out.println(AL.removeFirst());

        AL.printDeque();
        for (int i=0; i<AL.size(); i+=1){
            System.out.println(AL.get(i));
        }
    }
}
