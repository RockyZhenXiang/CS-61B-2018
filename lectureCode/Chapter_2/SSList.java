public class SSList{

	private class IntNode {
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
	private IntNode last; 


	public SSList(int x){
		sentinelNode = new .3IntNode(0, null); 
		sentinelNode.next = new IntNode(x, null);
		size = 1;
	}

	public SSList(){
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

		last.next = new IntNode(x,null);
		last = last.next; 
		size += 1;
	}



	public int size(){
		return size; 
	}


	public static void main(String[] args) {
		SSList S = new SSList(); 
		S.addFirst(10);
		S.addFirst(5);
		S.addLast(20);

		System.out.println(S.size());


	}



}