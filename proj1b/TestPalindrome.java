import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static OffByOne offbyone = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testOddChars() {
        String test1 = "Rocky";
        String test2 = "RidowodiR";
        assertFalse(palindrome.isPalindrome(test1));
        assertTrue(palindrome.isPalindrome(test2));
    }

    @Test
    public void testEvenChars() {
        String test1 = "Rock";
        String test2 = "RidoodiR";
        assertFalse(palindrome.isPalindrome(test1));
        assertTrue(palindrome.isPalindrome(test2));
    }

    @Test
    public void testSmallChars() {
        String test1 = "";
        String test2 = "R";
        assertTrue(palindrome.isPalindrome(test1));
        assertTrue(palindrome.isPalindrome(test2));
    }

    @Test
    public void testEvenChars2() {
        String test1 = "RidowodiR";
        String test2 = "flake";
        assertFalse(palindrome.isPalindrome(test1, offbyone));
        assertTrue(palindrome.isPalindrome(test2, offbyone));
    }

    @Test
    public void testSmallChars2() {
        String test1 = "";
        String test2 = "R";
        assertTrue(palindrome.isPalindrome(test1, offbyone));
        assertTrue(palindrome.isPalindrome(test2, offbyone));
    }
}
