import javax.annotation.processing.SupportedSourceVersion;

public class LinkedListDeque<AnyType> {

    public class LinkedNode {
        public LinkedNode prev;
        public AnyType item;
        public LinkedNode next;

        public LinkedNode (AnyType i, LinkedNode n){
            item = i;
            next = n;
        }

        public LinkedNode (LinkedNode p, AnyType i, LinkedNode n){
            prev = p;
            item = i;
            next = n;
        }
    }


    private LinkedNode sentinelNode;
    private int size;
    private LinkedNode lastNode;

    // Constructors
    public LinkedListDeque(AnyType x){
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
    public void addFirst(AnyType x){

        sentinelNode.next = new LinkedNode(sentinelNode, x, sentinelNode.next);

        if (sentinelNode.next.next != null) {
            sentinelNode.next.next.prev = sentinelNode.next;
        }else{
            lastNode = sentinelNode.next;
        }
        size += 1;
    }

    public AnyType getFirst(){

        return sentinelNode.next.item;
    }

    public void addLast(AnyType x){

        lastNode.next = new LinkedNode(lastNode, x,sentinelNode);
        lastNode = lastNode.next;
        size += 1;
    }

    public AnyType getLast(){

        return lastNode.item;
    }

    public boolean isEmpty(){
        if (sentinelNode.next == null){
            return true;
        }
        return  false;
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

    public AnyType removeFirst(){
        if (size == 0){return null;}
        AnyType res = sentinelNode.next.item;

        if (lastNode == sentinelNode.next){
            lastNode = sentinelNode;
            sentinelNode.next = null;
        }

        sentinelNode.next = sentinelNode.next.next;

        return res;
    }

    public AnyType removeLast(){
        if (size == 0){return null;}
        AnyType res = lastNode.item;

        lastNode = lastNode.prev;
        lastNode.next = sentinelNode;
        return res;
    }




    public static void main(String[] args) {
        LinkedListDeque S = new LinkedListDeque("Master");
        S.addFirst("Rocky");
        S.addFirst("Ricky");
        S.addFirst("Hailie");
        S.addLast(20);


        System.out.println(S.getFirst());
        System.out.println(S.getLast());
        System.out.println(S.size());
        System.out.println(S.isEmpty());

        LinkedListDeque L = new LinkedListDeque();
        System.out.println(L.isEmpty());

        L.printDeque();
        S.printDeque();

//        System.out.println(S.removeFirst());
//        S.printDeque();

        for (int i=0; i<S.size(); i+=1) {
            System.out.println(S.removeLast());
            S.printDeque();
        }


    }


}
