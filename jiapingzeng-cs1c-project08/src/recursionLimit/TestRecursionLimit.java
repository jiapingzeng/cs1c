package recursionLimit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Tests the effect of recursion limit by testing arrays with sizes ranging from 20,000
 * to 10,000,000. Uses recursion limits ranging from 2-300 with increment 2, resulting
 * in about 3000 entries
 *
 * For each recursion limit / array size combination, uses 3 int arrays with randomly
 * filled with numbers ranging from 0-100, sorts each array individually, then takes
 * the average of the sorting time
 */
public class TestRecursionLimit {

    /**
     * Writes time entries to data file
     * @param args arguments
     */
    public static void main(String[] args) {
        File dataFile = new File("resources/data.csv");
        try {
            // initialize file
            if (dataFile.exists() && dataFile.isFile()) {
                dataFile.delete();
            }
            dataFile.getParentFile().mkdirs();
            dataFile.createNewFile();
            FileWriter fw = new FileWriter(dataFile);
            fw.write("array size,recursion limit,estimated time\n");

            // try different array sizes (multiplying by 1.38 gives about 20 tri als)
            for (int size = 20000; size < 10000000; size*=1.38) {
                System.out.println("Testing array with size " + size);
                Integer[][] a = new Integer[][] {
                        generateArray(size),
                        generateArray(size),
                        generateArray(size)
                };
                // test different recursion limits
                for (int j = 2; j < 300; j+=2) {
                    fw.write(size + ",");
                    fw.write(j + ",");
                    fw.write(getAverageTime(runTestCase(a, j)) + "\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Writing file failed. Please try again");
            System.exit(0);
        }
    }

    /**
     * prints a generic array
     * @param a the array to be printed
     * @param <E> type of elements
     */
    static <E> void printArray(E[] a) {
        for (E e : a) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    /**
     * generate array with integers from 0-100
     * @param size size of array
     * @return array generated
     */
    static Integer[] generateArray(int size) {
        Random r = new Random();
        Integer[] a = new Integer[size];
        for (int i = 0; i < size; i++) {
            a[i] = r.nextInt(100);
        }
        return a;
    }

    /**
     * sorts an array and returns a time estimate
     * @param a array to be sorted
     * @param rl recursion limit
     * @return estimated time of sorting
     */
    static long estimateTime(Integer[] a, int rl) {
        long startTime = System.nanoTime();
        FHsort.setRecursionLimit(rl);
        FHsort.quickSort(a);
        long estimatedTime = System.nanoTime() - startTime;
        return estimatedTime;
    }

    /**
     * sorts an array of arrays and returns an array of sorting time
     * @param a array of arrays
     * @param rl recursion limit
     * @return array of sorting time
     */
    static long[] runTestCase(Integer[][] a, int rl) {
        long[] results = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            results[i] = estimateTime(a[i], rl);
        }
        return results;
    }

    /**
     * returns average sorting time from sorting time array
     * @param la sorting time array
     * @return average time
     */
    static long getAverageTime(long[] la) {
        long sum = 0;
        for (long l : la) {
            sum += l;
        }
        return sum / la.length;
    }
}
