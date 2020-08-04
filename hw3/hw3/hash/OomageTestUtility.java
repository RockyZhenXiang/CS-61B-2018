package hw3.hash;

import edu.princeton.cs.algs4.LinkedQueue;

import java.util.*;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        int max = (int) (oomages.size() / 2.5);
        int min = oomages.size() / 50;

        Deque<Oomage>[] oos = new ArrayDeque[M];
        for (int i = 0; i < M; i += 1) {
            oos[i] = new ArrayDeque<>();
        }
        // adding each oomage into the right bucket
        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            oos[bucketNum].addLast(oomage);
        }

        for (Deque<Oomage> oList: oos) {
            if (oList.size() < min || oList.size() > max) {
                return false;
            }
        }
        return true;
    }
}
