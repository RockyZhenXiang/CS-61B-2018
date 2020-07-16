public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> result = new LinkedListDeque<Character>();

        for( char s: word.toCharArray() ) {
            result.addLast(s);
        }

        return result;
    }
}
