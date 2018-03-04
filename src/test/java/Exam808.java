import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Exam808 {
    // http://education.oracle.com/education/downloads/Exam808_SampleQuestion.pdf
    // http://education.oracle.com/education/downloads/Exam809_SampleQuestion.pdf

    @Test
    public void ex1() throws Exception {
        //Given
        List<Integer> elements = new ArrayList<>();
        elements.add(10);
        int firstElem = elements.get(1);
        System.out.println(firstElem);

        /*
           What is the result?
           A) null
           B) 10
        `  C) 0
           D) An IndexOutOfBoundsException is thrown at runtime
         */
    }

    @Test
    public void ex2() throws Exception {
        //Given the code fragment


        Integer cardVal = null; /* Line n1 */

        // @formatter:off
        switch (cardVal){
            case 4: case 5: case 6:
            case 7: case 8:
                System.out.println("Hit");
                break;
            case 9: case 10: case 11:
                System.out.println("Double");
                break;
            case 15: case 16:
                System.out.println("Surrender");
                break;
            default:
                System.out.println("Stand");
        }
        // @formatter:on

        /*
            Which two code fragments can be inserted at Line n1, independently, enable to print Stand?
                A) int cardVal = 6;
                B) int cardVal = 10;
                C) int cardVal = 14;
                D) int cardVal = 18;
         */
    }

    @Test
    public void ex3() throws Exception {
        //Given
        Writer w = new Programmer();
        w.write();

        /*
            What is the result?
                A) Writing...
                B) Writing book
                C) Writing code
                D) Compilation fails.
        */
    }

    @Test
    public void ex4() throws Exception {
        //Given
        class SuperClass {
            public SuperClass(int x) {
                System.out.println("Super");
            }
        }

        /* Commented since it won't compile

        class SubClass extends SuperClass {
            SubClass() {
                //Line n1
                System.out.println("Sub 2");
            }
        }
        */

        /*
            Which statement, when inserted at Line n1, enables the code to compile?
                A) this(10);
                B) super(10);
                C) SuperClass(10);
                D) super.SuperClass (10);
         */

    }

    @Test
    public void ex5() throws Exception {
        //Given the code fragment
        List<String> items = new ArrayList<>();
        items.add("Pen");
        items.add("Pencil");
        items.add("Box");
        for (String i : items) {
            if (i.indexOf("P") == 0) {
                continue;
            } else {
                System.out.println(i + " ");
            }
        }

        /*
            What is the result?
                A) Pen Pencil Box
                B) Pen Pencil
                C) Box
                D) Compilation fails.
         */
    }

    @Test
    public void ex7() throws Exception {
        //Given the code fragment:

        /*
            int x = 10;
            int y = 2;
            try {
                for (int z = 2; z >= 0; z--) {
                    int ans = x / z;
                    System.out.print(ans+ " ");
                }
            } catch (Exception e1) {
                System.out.println("E1");
            } catch (ArithmeticException e1) {
                System.out.println("E2");
            }

            What is the result?
                A) E1
                B) E2
                C) 5 10 E1
                D) Compilation fails
         */
    }

    @Test
    public void ex8() throws Exception {
        StringBuilder s1 = new StringBuilder("Java");
        String s2 = "Love";
        s1.append(s2);
        s1.substring(4);
        int foundAt = s1.indexOf(s2);
        System.out.println(foundAt);
        /* What is the result?
                A) -1
                B) 3
                C) 4
                D) A StringIndexOutOfBoundsException is thrown at runtime.
        */
    }
}


abstract class Writer {
    public static void write() {
        System.out.println("Writing...");
    }
}

class Author extends Writer {
    public static void write() {
        System.out.println("Writing book");
    }
}

class Programmer extends Writer {
    public static void write() {
        System.out.println("Writing code");
    }
}

