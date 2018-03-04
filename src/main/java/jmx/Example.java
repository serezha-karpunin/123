package jmx;

import domain.Person;

public class Example implements ExampleMBean {
    @Override
    public void sayHello() {
        System.out.printf("Hello");
    }

    @Override
    public Person getPerson() {
        return new Person("Admin");
    }
}
