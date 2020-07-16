import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void randomAddAndRemoveTest(){
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> aad1 = new ArrayDequeSolution<>();
        String errorMessage = "";

        for (int i = 0; i < 100; i += 1) {
            if (sad1.size() == 0 || aad1.size() == 0) {
                int decision = StdRandom.uniform(2);
                if(decision == 0) {
                    sad1.addFirst(i);
                    aad1.addFirst(i);
                    errorMessage = errorMessage + "addFirst(" + i + ")\n" ;
                } else if(decision == 1) {
                    sad1.addLast(i);
                    aad1.addLast(i);
                    errorMessage = errorMessage + "addLast(" + i + ")\n" ;
                }
            } else {
                int decision = StdRandom.uniform(4);
                switch (decision) {
                    case 0:
                        sad1.addFirst(i);
                        aad1.addFirst(i);
                        errorMessage = errorMessage + "addFirst(" + i + ")\n" ;
                        break;
                    case 1:
                        sad1.addLast(i);
                        aad1.addLast(i);
                        errorMessage = errorMessage + "addLast(" + i + ")\n" ;
                        break;
                    case 2:
                        Integer a = sad1.removeFirst();
                        Integer b = aad1.removeFirst();
                        errorMessage = errorMessage + "removeFirst()\n" ;
                        assertEquals(errorMessage,a,b);
                        break;
                    case 3:
                        Integer c = sad1.removeLast();
                        Integer d = aad1.removeLast();
                        errorMessage = errorMessage + "removeLast()\n" ;
                        assertEquals(errorMessage,c,d);
                        break;
                    default:
                        System.out.println("Buggy!!!");
                }
            }

        }





    }
}
