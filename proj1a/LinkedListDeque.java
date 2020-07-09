public class LinkedListDeque {

    public class LinkedNode {
        public LinkedNode prev;
        public int item;
        public LinkedNode next;

        public LinkedNode (int i, LinkedNode n){
            item = i;
            next = n;
        }

        public LinkedNode (LinkedNode p, int i, LinkedNode n){
            prev = p;
            item = i;
            next = n;
        }
    }


    private LinkedNode sentinelNode;
    private int size;
    private LinkedNode lastNode;

    // Constructors
    public LinkedListDeque(int x){
        sentinelNode = new LinkedNode(0, null);
        sentinelNode.next = new LinkedNode(x, null);
        lastNode = sentinelNode.next;
        size = 1;
    }

    public LinkedListDeque(){
        size = 0;
        sentinelNode = new LinkedNode(0, null);
        lastNode = sentinelNode;
    }

    // Methods
    public void addFirst(int x){

        sentinelNode.next = new LinkedNode(sentinelNode, x, sentinelNode.next);

        if (sentinelNode.next.next != null) {
            sentinelNode.next.next.prev = sentinelNode.next;
        }else{
            lastNode = lastNode.next;
        }
        size += 1;
    }

    public int getFirst(){

        return sentinelNode.next.item;
    }

    public void addLast(int x){

        lastNode.next = new LinkedNode(x,null);
        lastNode = lastNode.next;
        size += 1;
    }

    public int getLast(){

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

        while(ptr != lastNode.next){
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }


    }


    public static void main(String[] args) {
        LinkedListDeque S = new LinkedListDeque();
        S.addFirst(10);
        S.addFirst(5);
        S.addFirst(3);
        S.addLast(20);


        System.out.println(S.getFirst());
        System.out.println(S.getLast());
        System.out.println(S.size());
        System.out.println(S.isEmpty());

        LinkedListDeque L = new LinkedListDeque();
        System.out.println(L.isEmpty());

        L.printDeque();
        S.printDeque();


    }


}
