public class LinkedListDeque {

    public class IntNode {
        public IntNode prev;
        public int item;
        public IntNode next;

        public IntNode (int i, IntNode n){
            item = i;
            next = n;
        }
    }


    private IntNode sentinelNode;
    private int size;
    private IntNode lastNode;


    public LinkedListDeque(int x){
        sentinelNode = new IntNode(0, null);
        sentinelNode.next = new IntNode(x, null);
        size = 1;
    }

    public LinkedListDeque(){
        size = 0;
        sentinelNode = new IntNode(0, null);
    }

    public void addFirst(int x){

        sentinelNode.next = new IntNode(x, sentinelNode.next);

        size += 1;
    }

    public int getFirst(){

        return sentinelNode.next.item;
    }

    public void addLast(int x){

        lastNode.next = new IntNode(x,null);
        lastNode = lastNode.next;
        size += 1;
    }

    public int getLast(){

        return lastNode.item;
    }

    public int size(){
        return size;
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


    }


}
