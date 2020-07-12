import javax.annotation.processing.SupportedSourceVersion;
import java.lang.reflect.AnnotatedArrayType;

public class LinkedListDeque<T> {

    public class LinkedNode {
        public LinkedNode prev;
        public T item;
        public LinkedNode next;

        public LinkedNode (T i, LinkedNode n){
            item = i;
            next = n;
        }

        public LinkedNode (LinkedNode p, T i, LinkedNode n){
            prev = p;
            item = i;
            next = n;
        }

        public T getRec(int index){
            if (index == 0){
                return item;
            }

            return next.getRec(index-1);
        }
    }


    private LinkedNode sentinelNode;
    private int size;
    private LinkedNode lastNode;

    // Constructors
    public LinkedListDeque(T x){
        sentinelNode = new LinkedNode(null, null);
        sentinelNode.next = new LinkedNode(sentinelNode, x, null);
        lastNode = sentinelNode.next;
        lastNode.next = sentinelNode;
        size = 1;
    }

    public LinkedListDeque(){
        size = 0;
        sentinelNode = new LinkedNode(null, null);
        lastNode = sentinelNode;

    }

    // Methods
    public void addFirst(T x){

        sentinelNode.next = new LinkedNode(sentinelNode, x, sentinelNode.next);

        if (sentinelNode.next.next != null) {
            sentinelNode.next.next.prev = sentinelNode.next;
        }else{
            lastNode = sentinelNode.next;
        }
        size += 1;
    }

    public T getFirst(){

        return sentinelNode.next.item;
    }

    public void addLast(T x){

        lastNode.next = new LinkedNode(lastNode, x,sentinelNode);
        lastNode = lastNode.next;
        size += 1;
    }

    public T getLast(){

        return lastNode.item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        if (size == 0){
            return;
        }
        LinkedNode ptr = sentinelNode.next;

        while(ptr != lastNode.next) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }

    public T removeFirst() {
        if (size == 0) { return null; }
        T res = sentinelNode.next.item;

        if (lastNode == sentinelNode.next){
            lastNode = sentinelNode;
            sentinelNode.next = null;
        }else {
            sentinelNode.next = sentinelNode.next.next;
            sentinelNode.next.prev = sentinelNode;
        }

        size -= 1;

        return res;
    }

    public T removeLast() {
        if (size == 0) { return null; }
        T res = lastNode.item;

        lastNode = lastNode.prev;
        lastNode.next = sentinelNode;
        size -= 1;
        return res;
    }

    public T get(int index) {
        if (index >= size) { return null; }

        LinkedNode ptr = sentinelNode.next;

        while(index != 0) {
            ptr = ptr.next;
            index -= 1;
        }

        return ptr.item;
    }

    public T getRecursive(int index){
        if (index >= size){return null;}
        return sentinelNode.next.getRec(index);
    }


    public static void main(String[] args) {
        LinkedListDeque S = new LinkedListDeque("Master");
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
