public class D {
    public static void main(String[] args) {
        // B a0 = new A(); Subclass can only stay at the RHS
        //a0.m1();
        //a0.m2(16);
        A b0 = new B();
        System.out.println(b0.x);
        b0.m1();
        b0.m2();
        //b0.m2(61); m2(int x) is not define in static type
        B b1 = new B();
        b1.m2(61);
        b1.m3();
        A c0 = new C();
        c0.m2();
        //C c1=(A) new C(); Subclass can only stay at the RHS
        A a1=(A) c0;
        C c2=(C) a1;
        c2.m3();
        //c2.m4(); No m2 anymore
        c2.m5();
        ((C) c0).m3();
        //(C) c0.m3(); this cast only the result but not c0
        b0.update();
        b0.m1();

    }
}
