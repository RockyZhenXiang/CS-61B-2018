import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testSize(){
        ArrayDeque AL = new ArrayDeque();
        AL.addLast(10);
        AL.addLast(9);
        AL.addLast(8);
        AL.addFirst(10);
        AL.addFirst(9);
        AL.addFirst(8);
        assertEquals(6, AL.size());
        ArrayDeque AL2 = new ArrayDeque();
        assertEquals(0, AL2.size());

    }


}
