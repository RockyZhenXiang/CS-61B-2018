public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> result = new LinkedListDeque<Character>();

        for( char s: word.toCharArray() ) {
            result.addLast(s);
        }

        return result;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        if (wordDeque.size() < 2) {
            return true;
        }
        int lastIndex = wordDeque.size() / 2;
        for(int i = 0; i < lastIndex; i ++ ){
            char head = wordDeque.get(i);
            char tail = wordDeque.get(wordDeque.size() - 1 - i);
            if (head != tail) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        if (wordDeque.size() < 2) {
            return true;
        }
        int lastIndex = wordDeque.size() / 2;
        for(int i = 0; i < lastIndex; i ++ ){
            char head = wordDeque.get(i);
            char tail = wordDeque.get(wordDeque.size() - 1 - i);
            if (cc.equalChars(head, tail) == false) {
                return false;
            }
        }
        return true;
    }
}
