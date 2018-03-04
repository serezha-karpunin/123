import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;

public class Exam809 {
    @Test
    public void ex2() throws Exception {
        //Given
        class Toy {
            double price;
            String color;

            Toy(String color, double price) {
                this.color = color;
                this.price = price;
            }

            public double getPrice() {
                return price;
            }

            public String getColor() {
                return color;
            }
        }
        //And given the code fragment:
        List<Toy> toys = new ArrayList<>();
        toys.add(new Toy("red", 10));
        toys.add(new Toy("yellow", 10));
        toys.add(new Toy("red", 10));

        /*
        double totalPrice = toys.stream()
                .filter(e -> e.getColor() == "red")
                //Line n1
                .sum();
        System.out.println("Total Price of Red Toys: " + totalPrice);
        */

        //Which code fragment can be inserted at Line n1 to enable the code to print Total Price of Red Toys:
        //20.0?
    }

    @Test
    public void ex3() throws Exception {
        //Given the code fragment:
        class MyResource1 implements AutoCloseable {
            public void close() throws IOException {
                System.out.print("1 ");
            }
        }
        class MyResource2 implements Closeable {
            public void close() throws IOException {
                throw new IOException();
            }
        }

        // Note that the close methods of resources are called in the opposite order of their creation
        try (MyResource1 r1 = new MyResource1();
             MyResource2 r2 = new MyResource2();) {
            System.out.print("T ");
        } catch (IOException ioe) {
            System.out.print("IOE ");
        } finally {
            System.out.print("F ");
        }

        /*
            What is the result?
                A) T 1 IOE F
                B) T IOE F
                C) T IOE 1 F
                D) Compilation fails.
        */
    }

    @Test
    public void ex4() throws Exception {
        /*
        class TestReadFile {
            public void readFile(String fName) throws IOException {
                // Line n1
                Stream<String> content = Files.lines(p);
                content.forEach(s1 -> System.out.println(s1));
            }
        }

        TestReadFile trf = new TestReadFile();
        trf.readFile("TestResult.txt ");


        Which code fragment at Line n1 compiles?
            A) Path p = new Path(fName);
            B) Path p = Paths.get(fName);
            C) Path p = Paths.toPath(fName);
            D) Path p = Paths.get(new File(fName));
        */
    }
}


class A {
    private int a;
    private static int b;

    class B{
        public void hello(int d){
            Function<String, Integer> func = s -> s.length() + a + d + b;
            System.out.println("Hello" + a);
        }
    }

    static class C{
        public void hello(){
            System.out.println("Hello" + b);
        }
    }
}