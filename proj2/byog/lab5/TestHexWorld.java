package byog.lab5;

import org.junit.Test;
import org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class TestHexWorld {

    @Test
    public void testIsInHexagon() {
        int size = 4;
        int width = 3 * size - 2;
        int height = 2 * size;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                boolean res = HexWorld.isInHexagon(i, j, size);
                System.out.println("i = " + i + ", j = " + j + ", result = " + res);
                if (i == width - 1) {
                    System.out.println("");
                }
            }
        }
    }

    @Test
    public void testChange() {
        int [][] wor= new int[10][10];
        for (int[] a: wor) {
            System.out.println(Arrays.toString(a));
        }
        int X = 2;
        int Y = 3;
        int size = 2;
        System.out.println("");

        HexWorld.changeContent(wor, X, Y, size);
        for (int[] a: wor) {
            System.out.println(Arrays.toString(a));
        }
    }
}
