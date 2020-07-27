package byog.lab6;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Assert.*;
import org.junit.Test;

public class TestMemory {

    @Test
    public void testGenerateRandomString() {
        int seed = 20;
        MemoryGame mem = new MemoryGame(40,40, seed);
        System.out.println(mem.generateRandomString(100));
    }

    @Test
    public void testDrawFrame() {
        int seed = 20;
        MemoryGame mem = new MemoryGame(40,40, seed);
        String str = mem.generateRandomString(seed);
        mem.drawFrame(str);
    }

    @Test
    public void testFlashSequence() {
        int seed = 5;
        MemoryGame mem = new MemoryGame(40,40, seed);
        String str = mem.generateRandomString(seed);
        System.out.println(str);
        mem.flashSequence(str);
    }

    @Test
    public void testSolicitNCharsInput() {
        int seed = 5;
        MemoryGame mem = new MemoryGame(40,40, seed);
        mem.solicitNCharsInput(5);
    }


    public static void main(String[] args) {
        int seed = 30;
//        MemoryGame mem = new MemoryGame(40,40,seed);
//        mem.startGame();
        TestMemory test = new TestMemory();
        test.testDrawFrame();
    }

}
