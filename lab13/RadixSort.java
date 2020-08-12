import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        // find max string index
        int max = Integer.MIN_VALUE;
        for (String str: asciis) {
            max = Math.max(max, str.length());
        }
        String[] res = Arrays.copyOf(asciis, asciis.length);
        for (int i = max - 1; i >= 0; i -= 1) {
            res = sortHelperLSD(res, i);
        }

        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort

        // find max
        int max = Integer.MIN_VALUE;
        for (String i: asciis) {
            try{
                int j = (int) i.charAt(index);
                max = Math.max(j, max);
            } catch (Exception ignored) {
            }
        }

        // gather all the counts for each value
        int[] counts = new int[max + 2]; // one additional bucket for -1
        for (String str: asciis) {
            if (str.length() <= index) {
                counts[0] += 1;
            } else {
                int j = (int) str.charAt(index);
                counts[j + 1] += 1;
            }
        }

        // generalized implementation of counting sort that uses start position calculation
        int[] starts = new int[max + 2];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        String[] sorted2 = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            String str = asciis[i];
            int item;
            if (str.length() <= index) {
                item = 0;
            } else {
                item = (int) str.charAt(index) + 1;
            }
            int place = starts[item];
            sorted2[place] = str;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted2;

    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
