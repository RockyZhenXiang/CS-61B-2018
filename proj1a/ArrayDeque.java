public class ArrayDeque {
    /**
     * Invariants
     * Array based data structure
     * size = number of items
     * startIndex always points before at the first item
     * endIndex always points at the next index after the last item
     */


    // Variables
    private int[] items;
    private int size;
    private double UsageRatio;  //Used to determined if the AList needed to be shorten.
    private int startIndex;
    private int endIndex;


    /**
     * Creates an empty list.
     */
    public ArrayDeque() {
        items = new int[8];
        size = 0;
        UsageRatio = 0.0;
        startIndex = items.length-1;
        endIndex = 0;

    }

    private void reSize(int capacity) {

        int[] a = new int[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    /**
     * Inserts X into the front of the list.
     */
    public void addFirst(int x){
        if (size == items.length){
            reSize(size*2);
        }
        items[startIndex] = x;
        size += 1;
        if (startIndex == 0){
            startIndex = items.length -1;
        }else{
            startIndex -= 1;
        }
        UsageRatio = (double)size / (double)items.length;
    }

    /**
     * Inserts X into the back of the list.
     */
    public void addLast(int x) {
        if (size == items.length) {
            reSize(size * 2); // by using multiplication, the consuming time is shorten by log(n).
        }
        items[endIndex] = x;
        size += 1;
        if (endIndex != size) {
            endIndex += 1;
        }else{
            endIndex = 0;
        }
        UsageRatio = (double)size / (double)items.length;
    }

    /**
     * Returns the item from the back of the list.
     */
    public int getLast() {
        return items[size - 1];
    }

    /**
     * Gets the ith item in the list (0 is the front).
     */
    public int get(int i) {

        return items[i];
    }

    /**
     * Returns the number of items in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Deletes item from back of the list and
     * returns deleted item.
     */
    public int removeLast() {
        int res = items[size - 1];
        // items[size-1] = 0; not necessary, because the user cannot reach it. But in genetic array, be setting it into null, we can save memory.
        size -= 1;
        return res;
    }


    public static void main(String[] args) {
        ArrayDeque AL = new ArrayDeque();
        AL.addLast(10);
        AL.addLast(9);
        AL.addLast(8);
        AL.addFirst(10);
        AL.addFirst(9);
        AL.addFirst(8);

    }
}
