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

    /**
     * Resizing the array to size = capacity if to many item is added or to many empty boxes
     */

    private void reSize(int capacity) {
        if (size == 0) return;
        int[] a = new int[capacity];
        int firstIndex;
        int lastIndex;
        if (startIndex+1-items.length == 0){
            firstIndex = 0;
        }else{
            firstIndex = startIndex + 1;
        }
        if (endIndex == 0){
            lastIndex = items.length - 1;
        }else{
            lastIndex = endIndex - 1;
        }

        if (firstIndex > lastIndex){
            System.arraycopy(items, firstIndex, a, 0, items.length - firstIndex );
            System.arraycopy(items, 0, a, items.length - firstIndex, lastIndex + 1);
        }else{
            System.arraycopy(items, firstIndex, a, 0, size);
        }
        items = a;
        startIndex = items.length - 1;
        endIndex = size;
        UsageRatio = (double)size / (double)items.length;
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
     * Check if the AList is empty
     */
    public boolean isEmpty(){
        return size == 0;
    }


    /**
     * Returns the number of items in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */

    public void printDeque(){
        if (size == 0) return;
        int i = startIndex + 1;
        while (i != endIndex) {
            System.out.print(items[i] + " ");
            if (i == items.length-1) {
                i = 0;
            } else {
                i += 1;
            }
        }
    }


    /**
     * Returns the item from the back of the list.
     */
    public int getLast() {
        return items[endIndex - 1];
    }

    /**
     * Gets the ith item in the list (0 is the front).
     */
    public int get(int i) {
        if (i >= size){
            return 0;
        }
        int trueIndex = startIndex + i + 1;
        if (trueIndex>items.length-1){
            trueIndex -= items.length;
        }
        return items[trueIndex];
    }


    /**
     * Deletes item from back of the list and
     * returns deleted item.
     */
    public int removeLast() {
        int res = items[endIndex - 1];
        items[endIndex - 1] = 0; //not necessary, because the user cannot reach it. But in genetic array, be setting it into null, we can save memory.
        size -= 1;
        if (endIndex == 0){
            endIndex = items.length - 1;
        }else{
            endIndex -= 1;
        }
        UsageRatio = (double)size / (double)items.length;
        if (UsageRatio < 0.25) reSize((int) (items.length * 0.5));
        return res;
    }

    /**
     * Deletes item from the head of the list and
     * returns deleted item.
     */
    public int removeFirst() {

        if (startIndex == items.length - 1){
            startIndex = 0;
        }else{
            startIndex += 1;
        }
        int res = items[startIndex];
        items[startIndex] = 0; //not necessary, because the user cannot reach it. But in genetic array, be setting it into null, we can save memory.
        size -= 1;

        UsageRatio = (double)size / (double)items.length;
        if (UsageRatio < 0.25) reSize((int) (items.length * 0.5));
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
        AL.addLast(10);
        AL.addLast(9);
        AL.addLast(8);
        AL.addFirst(10);
        AL.addFirst(9);
        AL.addFirst(8);
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

        AL.addLast(10);
        AL.addLast(9);
        AL.addLast(8);
        AL.addFirst(10);
        AL.addFirst(9);
        AL.addFirst(8);
        AL.addLast(10);
        AL.addLast(9);
        AL.addLast(8);
        AL.addFirst(10);
        AL.addFirst(9);
        AL.addFirst(8);
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
