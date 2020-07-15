import javax.annotation.processing.SupportedSourceVersion;
import java.lang.reflect.AnnotatedArrayType;

public class LinkedListDeque<T> {
    /**
     * LinkedListDeque using two ended sentineNode
     */

    private class LinkedNode {
        private LinkedNode prev;
        private T item;
        private LinkedNode next;

        private LinkedNode (LinkedNode p, T i, LinkedNode n){
            prev = p;
            item = i;
            next = n;
        }

        private T getRec(int index){
            if (index == 0){
                return item;
            }

            return next.getRec(index-1);
        }
    }


    private LinkedNode sentinelFront;
    private int size;
    private LinkedNode sentinelBack;

    // Constructor
    /**
     * Creates a empty LinkedListDeque
     */

    public LinkedListDeque(){
        size = 0;
        sentinelFront = new LinkedNode(null, null, null);
        sentinelBack = new LinkedNode(sentinelFront, null, null);
        sentinelFront.next = sentinelBack;
    }

    // Methods

    /**
     * Add @param x one step behind sentinelFront
     */
    public void addFirst(T x){

        sentinelFront.next = new LinkedNode(sentinelFront, x, sentinelFront.next);
        sentinelFront.next.next.prev = sentinelFront.next;
        size += 1;
    }
    /**
     * Add @param x one step in front of sentinelBack
     */
    public void addLast(T x){
        sentinelBack.prev = new LinkedNode(sentinelBack.prev, x, sentinelBack);
        sentinelBack.prev.prev.next = sentinelBack.prev;
        size += 1;
    }

    /**
     * Prints the items in the deque from first to last,
     * separated by a space. Once all the items have been
     * printed, print out a new line.
     */

    public void printDeque(){
        LinkedNode ptr = sentinelFront.next;

        while(ptr != sentinelBack) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.println();
    }

    /**
     * return true if the LinkListDeque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * returns the number of items in the LinkedListDeque
     */
    public int size(){
        return size;
    }

    /**
     * returns the first item in the LinkedListDeque then remove it from the deque
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T res = sentinelFront.next.item;
        sentinelFront.next = sentinelFront.next.next;
        sentinelFront.next.prev = sentinelFront;
        size -= 1;

        return res;
    }

    /**
     * returns the last item in the LinkedListDeque then remove it from the deque
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T res = sentinelBack.prev.item;

        sentinelBack.prev = sentinelBack.prev.prev;
        sentinelBack.prev.next = sentinelBack;
        size -= 1;
        return res;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        LinkedNode ptr = sentinelFront.next;
        while(index != 0) {
            ptr = ptr.next;
            index -= 1;
        }
        return ptr.item;
    }

    public T getRecursive(int index){
        if (index >= size || index < 0) {
            return null;
        }
        return sentinelFront.next.getRec(index);
    }


    private static void main(String[] args) {
        LinkedListDeque S = new LinkedListDeque();
        S.addFirst("Rocky");
        S.addFirst("Ricky");
        S.addFirst("Hailie");
        S.addLast(20);


        for (int i=0; i<S.size(); i+=1) {
            System.out.print(S.get(i));
            System.out.println(S.getRecursive(i));
            S.printDeque();
        }

    }


}
