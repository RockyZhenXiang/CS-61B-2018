/** Array based list.
 *  @author Josh Hug, Rocky Zhenxiang Fang
 */

public class AList {

	// Variables
	private int [] items; 
	private int size; 
	private double UsageRatio; // Used to determined if the AList needed to be shorten. 


    /** Creates an empty list. */
    public AList() {
    	items = new int[100]; 
    	size = 0; 

    }

    private void reSize(int capacity){

    	    int[] a = new int[capacity]; 
    		System.arraycopy(items,0,a,0,size);
    		items = a; 
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
    	if ( size == items.length){
    		reSize(size * 2); // by using multiplication, the comuing time is shorten by log(n). 
    	}
    	items[size] = x; 
    	size += 1; 
    }

    /** Returns the item from the back of the list. */
    public int getLast() {
        return items[size-1];        
    }
    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {

        return items[i];        
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;        
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public int removeLast() {
    	int res = items[size-1]; 
    	// items[size-1] = 0; not necessary, because the user cannot reach it. But in genetic array, be setting it into null, we can save memory. 
     	size -= 1; 
        return res;
    }


} 