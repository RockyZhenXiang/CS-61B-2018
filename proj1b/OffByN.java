public class OffByN implements CharacterComparator{

    int diff;

    public OffByN(int N) {
        diff = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int intX = x;
        int intY = y;

        return Math.abs(x - y) == diff;
    }
}
