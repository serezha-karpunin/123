import org.junit.Test;

public class DefaultInterfaces {
    @Test
    public void test() throws Exception {
        new AD().print(); // D
    }

    interface A {
        default void print() {
            System.out.println("A");
        }
    }

    interface B {
        default void print() {
            System.out.println("A");
        }
    }

    interface C {
        void print();
    }

    class D {
        public void print() {
            System.out.println("D");
        }
    }

//     won't compile
//    class AB implements A, B{
//
//    }

//    won't compile
//    class AC implements A, C{
//
//    }

    class AD extends D implements A {

    }
}
