public class IntList{
	public int first;
	public IntList rest;

	public IntList(int f, IntList r){
		first = f;
		rest = r;
	}

	public IntList(IntList L){
		first = L.first;
		rest = L.rest;
	}

	public int size(){

		if (rest == null){
			return 1;
		}
		return 1+rest.size();

	}

	public int iterativeSize(){
		int size = 0;
		IntList L = this;

		while(L != null){
			size += 1;
			L = L.rest;
		}
		return size;
	}

	public int get(int n){

		if (n ==0){
			return first;
		}

		return rest.get(n-1);

	}

    public static IntList incrList(IntList L, int x) {
        /* Your code here. */

        IntList p = L;
        IntList Q = new IntList(0,null);

        for (int i=1; i<L.size(); i+= 1){
        	Q = new IntList(0,Q);
        }
        IntList q = Q; 

        while (p != null){
 			q.first = p.first + x;
 			q = q.rest;
 			p = p.rest;
        }

        System.out.println("Finished incrList");
        return Q;        
    }

    /** Returns an IntList identical to L, but with
      * each element incremented by x. Not allowed to use
      * the 'new' keyword. */
    public static IntList dincrList(IntList L, int x) {
        /* Your code here. */

        IntList p = L;


        while (p != null){
        	p.first += x;
        	p = p.rest;
        }

        return L;
    }


	public static void main(String[] args) {
		IntList L = new IntList(15, null);
		L = new IntList(10, L);
		L = new IntList(5, L);

		System.out.println(L.size());		
		System.out.println(L.iterativeSize());
		for (int i=0;i<3;i+=1){
			System.out.println(L.get(i));
		}

		// IntList Q = new IntList(L);
		// for (int i=0;i<3;i+=1){
		// 	System.out.println(Q.get(i));
		// }

		// IntList R = dincrList(L,2);

		// for (int i=0;i<3;i+=1){
		// 	System.out.println(R.get(i));
		// }

		// for (int i=0;i<3;i+=1){
		// 	System.out.println(L.get(i));
		// }


		IntList S = incrList(L,2);

		for (int i=0;i<3;i+=1){
			System.out.println(S.get(i));
		}

		for (int i=0;i<3;i+=1){
			System.out.println(L.get(i));
		}
		


	}
}