import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Random;


public class TestRadixSort {

    @Test
    public void testRadixSort() {
        Random rand = new Random(10);
        String[] test = new String[10];
        for (int i = 0; i < 10; i += 1) {
            int n = rand.nextInt(10);
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < n; j += 1) {
                int ascii = rand.nextInt(256);
                char ch = (char) ascii;
                str.append(ch);
            }
            test[i] = str.toString();
        }

        System.out.println(Arrays.toString(test));
        String[] res = RadixSort.sort(test);
        Arrays.sort(test);
        Assert.assertArrayEquals(res, test);

    }

    @Test
    public void test() {
        String[] test = new String[]{"abcde", "bcde", "dvbe", "123"};
        String[] res = RadixSort.sort(test);
        System.out.println(Arrays.toString(res));
        System.out.println(Arrays.toString(test));
        Arrays.sort(test);
        Assert.assertArrayEquals(res, test);
    }

    @Test
    public void tes() {
        for (int i = 10; i >= 0; i --) {
            System.out.println(i);
        }
    }

}
