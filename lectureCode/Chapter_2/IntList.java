public class IntList{
	public int first;
	public IntList rest;

	public IntList(int f, IntList r){
		first = f;
		rest = r;
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



	public static void main(String[] args) {
		IntList L = new IntList(15, null);
		L = new IntList(10, L);
		L = new IntList(5, L);

		System.out.println(L.size());		
		System.out.println(L.iterativeSize());
		for (int i=0;i<3;i+=1){
			System.out.println(L.get(i));
		}
	}






}