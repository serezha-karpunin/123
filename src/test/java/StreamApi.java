import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamApi {
    /*
    https://www.youtube.com/watch?v=O8oN4KSZEXE
    https://www.youtube.com/watch?v=vxikpWnnnCU
    https://leanpub.com/whatsnewinjava8/read
    https://www.logicbig.com/tutorials/core-java-tutorial/java-util-stream/ordering.html
    https://www.logicbig.com/tutorials/core-java-tutorial/java-util-stream/stream-cheat-sheet.html

    A sequence of elements supporting sequential and parallel aggregate operations.

    To perform a computation, stream operations are composed into a stream pipeline.
    A stream pipeline consists of a source (an array, a collection, a generator function, an I/O channel, etc),
    zero or more intermediate operations (old stream -> new stream), and a terminal operation (produces a result or side-effect)

    Streams are lazy; computation on the source data is only performed when the terminal operation is initiated,
    and source elements are consumed only as needed.

    Collections and streams, while bearing some superficial similarities, have different goals.
    Collections are primarily concerned with the efficient management of, and access to, their elements.
    By contrast, streams do not provide a means to directly access or manipulate their elements,
    and are instead concerned with declaratively describing their source and the computational operations
    which will be performed in aggregate on that source.

    A stream should be operated on (invoking an intermediate or terminal stream operation) only once.
    This rules out, for example, "forked" streams, where the same source feeds two or more pipelines,
    or multiple traversals of the same stream.

     Stream:
     - interface
     - НЕ структура данных (нет хранения элементов)
     - операции в последний момент (ленивые)
     - одноразовый (т.к все это функционально ориентированно - не мутируем)

    +:
     - компактнее / читабельнее
     - laziness (ленивые вычисления - сначала задекларировать все что хотим сделать, потом делать -> оптимизации)
     - parallelism
    */

    @Test
    public void streamSource() throws Exception {
        /*
         * Generic Stream + specializations: IntStream, LongStream, DoubleStream
         */

        /* collection.stream() */
        Stream<Integer> s1 = Arrays.asList(1, 2, 3).stream();

        /* factories */
        IntStream s2 = Arrays.stream(new int[]{1, 2, 3});
        Stream<Integer> s3 = Stream.of(1, 2, 3);

        IntStream s4 = IntStream.range(1, 4);
        IntStream s5 = IntStream.rangeClosed(1, 3);

        /* builders */
        Stream<Object> s6 = Stream.builder().add(1).add(2).add(3).build();

        /* generators */
        Stream<Double> s7 = Stream.generate(Math::random);
        /*
            use new Random().ints(..) / longs(..) / doubles(..) instead
         */

        //iterate method is similar to generate except it takes an initial value and a Function that modifies that value
        Stream<Integer> s8 = Stream.iterate(0, i -> i + 1);

        /* text patterns */
        Stream<String> s9 = Pattern.compile(",").splitAsStream("a,b,c");

        /* files */

        //THERE IS AN EXCEPTION: you can use this.getClass().getResourceAsStream("file.txt");
        try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
            Stream<String> s10 = br.lines();
        } catch (Exception e) {
        }


        //THERE IS AN EXCEPTION: you can use this.getClass().getResourceAsStream("file.txt");
        try (Stream s11 = Files.lines(Paths.get("file.txt"))) {

        } catch (Exception e) {
             /*
                File.lines(Path): Any IOException that is thrown while processing the file
                will get wrapped in an UncheckedIOException and thrown
            */
        }
    }

    @Test
    public void intermediateOperations() throws Exception {
        /*  - Stream -> Stream
         *  - all of them are lazy
         */
    }

    @Test
    public void terminateOperations() throws Exception {

        /* Stream ->  PROFIT! */

        /*
       //todo Stream.forEach doesn't guarantee order??
         */

        Stream.of(1, 2, 3).forEach(i -> {
        });
        Iterator<Integer> iterator = Stream.of(1, 2, 3).iterator();

        /* search */

        Optional<Integer> o1 = Stream.of(1, 2, 3).findFirst();
        Optional<Integer> o2 = Stream.of(1, 2, 3).findAny();

        /*
            short-circuiting: некоторые операции могут бросить поток
            -> получают смысл операции над бесконечными потоками
        */
        Integer integer = Stream.iterate(1, i -> i + 1)
                .filter(i -> i % 2 == 0)
                .findFirst().get();

        boolean b1 = Stream.of(1, 2, 3).allMatch(i -> i % 2 == 0);
        boolean b2 = Stream.of(1, 2, 3).anyMatch(i -> i % 2 == 0);
        boolean b3 = Stream.of(1, 2, 3).noneMatch(i -> i % 2 == 0);

        /* aggregators */
        Optional<Integer> i1 = Stream.of(1, 2, 3).reduce(Integer::sum); // Optional(6)
        Integer i2 = Stream.of(1, 2, 3).reduce(10, Integer::sum); // 16

        /*
            A Collector represents a way to combine the elements of a Stream into one result.
            It consists of three things:
                - A supplier of an initial value.
                - An accumulator which adds to the initial value.
                - A combiner which combines two results into one.

            there are two ways to do this: collect(supplier, accumulator, combiner); collect(Collector)
         */

        List<Integer> c1 = Stream.of(1, 2, 3).collect(Collectors.toList());
        Set<Integer> c2 = Stream.of(1, 2, 3).collect(Collectors.toSet());

        Map<Integer, Integer> m1 = Stream.of(1, 2, 3).collect(Collectors.toMap(k -> k, v -> v * 10)); //{1=10, 2=20, 3=30}

        Map<Boolean, List<Integer>> m2 = Stream.of(1, 2, 3).collect(Collectors.partitioningBy(i -> i % 2 == 0)); //{false=[1, 3], true=[2]}
        Map<Integer, List<Integer>> m3 = Stream.of(1, 2, 3).collect(Collectors.groupingBy(i -> i)); //{1=[1], 2=[2], 3=[3]}

        /*
            To execute grouping in parallel (if you don’t care about ordering) you should use
            the groupingByConcurrent method.

            The underlying stream should be unordered to allow grouping to occur in parallel; for example:
            dragons.parallelStream().unordered().collect(groupingByConcurrent(Dragon::getColor));
         */


        /* statistics */
        OptionalDouble avg1 = IntStream.range(0, 100).average();
        Double avg2 = Stream.of("a", "b", "c").collect(Collectors.averagingInt(String::length));
        IntSummaryStatistics intSummaryStatistics = IntStream.range(0, 100).summaryStatistics();

    }

    @Test
    public void string() {
        String s1 = Arrays.stream(new String[]{"a", "b", "c"}).collect(Collectors.joining(", ")); // "a, b, c"
        /* or use StringJoiner directly */

        IntStream chars = "abc".chars();
    }

    @Test
    public void sorting() throws Exception {
        /*
            Метод sorted() является методом с состоянием. Методы с состоянием могут не иметь возможности
            вернуть результат пока не обработают весь поток -> не работают с бесконечными потоками.

         */
    }

    @Test
    public void spliterator() throws Exception {
        /*
            tryAdvance(Consumer) — объединение методов итератора hasNext() и next().
            Если у сплитератора есть следующий элемент, он должен вызвать переданную функцию с этим элементом
            и вернуть true, иначе функцию не вызывать и вернуть false.

            trySplit() — попытаться поделиться надвое. Метод возвращает новый сплитератор,
            который будет пробегать по первой половине исходного набора данных,
            при этом сам текущий сплитератор перепрыгивает на вторую половину.

            characteristics() — возвращает битовую маску характеристик сплитератора.
        */
    }

    @Test
    public void parallelism() throws Exception {
        /*
        нужно указывать явно .parallelStream() or stream().parallel()
        под капотом - ForkJoinPool

        почему не по умолчанию:
            выигрыш сильно зависит от
                количество элементов в источнике <- точно знаем
                стоимость операции над элементом <- очень сложно оценить
                доступного параллелизма на машине
                количество конкурентных клиентов
         */
    }

    @Test
    public void order() throws Exception {
        /*
        не делают ничего со стримом
        unordered() - если был упорядочен, то считаем не упорядоченным (НЕ шафл) - выигрыш производительности
        parallel() - последующая обработка в нескольких потоках
        sequential() -  в одном потоке
         */
    }
}
