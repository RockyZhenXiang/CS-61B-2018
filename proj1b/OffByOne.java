public class OffByOne implements CharacterComparator{

    @Override
    public boolean equalChars(char x, char y) {
        int intX = x;
        int intY = y;

        return Math.abs(x - y) == 1;
    }

}
