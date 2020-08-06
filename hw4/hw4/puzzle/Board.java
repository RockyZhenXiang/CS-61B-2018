package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public final class Board implements WorldState{

    private final int size;
    private final int[][] boardStatus;
    private static final int BLANK = 0;

    public Board(int[][] tiles) {
        size = tiles.length;
        boardStatus = new int[size][size];
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                boardStatus[row][col] = tiles[row][col];
            }
        }

    }
    public int tileAt(int i, int j) {
        int n = boardStatus.length;
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1 ) {
            throw new IndexOutOfBoundsException();
        }
        if (boardStatus[i][j] == 0) {
            return 0;
        } else {
            return boardStatus[i][j];
        }
    }
    public int size() {
        return size;
    }

    /**
     * Returns neighbors of this board.
     * @Source Josh Hung, UC Berkeley
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int sum = 0;
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                if (row == size - 1 && col == size - 1) {
                    return sum;
                }
                if (tileAt(row, col) != xy21D(row, col)) {
                    sum += 1;
                }
            }
        }
        return sum;
    }
    public int manhattan() {
        return  0;
    }

    private int xy21D(int row, int col) {
        return row * size + col + 1;
    }
    public int estimatedDistanceToGoal() {
        return hamming();
    }

    @Override
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (this == y) {
            return true;
        }

        if (this.getClass() != y.getClass()) {
            return false;
        }
        return Arrays.deepEquals(this.boardStatus, ((Board) y).boardStatus);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
