public class TestSort{

	/** Test the Sort.sort method. */

	public static void testSort(){
		String[] input = {"i", "have", "an", "egg"};
		String[] expected = {"an", "egg", "have", "i"}

		Sort.sort(input);

		/* Not very useful, not giving enough information 
		//if (input != expected){// Onlying comparing the address, but not the content itself.  
		if (!java.util.Arrays.equals(input,expected)){
			System.out.println("Error");
		
		}
		*/

		// junit testing
		org.junit.Assert.assertEquals(expected,input);

		// Ad-Hoc Testing
		for (int i = 0; i<input.length; i+=1){
			if (!input[i].equals(expected[i])){
				System.out.println("Mismatch in position" + i + "expected" + expected[i] + ", but got: " + input[i]);
			}
		}

	}
	public static void main(String[] args) {
		testSort();
		
	}
}