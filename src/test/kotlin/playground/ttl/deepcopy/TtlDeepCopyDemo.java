package playground.ttl.deepcopy;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashSet;
import java.util.Set;

public class TtlDeepCopyDemo {
    public static void main(String[] args) throws Exception {
        context.get().add(new Person("cat", 18));

        Thread thread = new Thread(() -> System.out.println(context.get()));
        thread.start();
        thread.join();
    }

    private static final TransmittableThreadLocal<Set<Person>> context = new TransmittableThreadLocal<Set<Person>>() {
        @Override
        public Set<Person> copy(Set<Person> parentValue) {
            Set<Person> deep = new HashSet<>();
            for (Person person : parentValue) {
                deep.add(new Person(person.name + "+1", person.age + 1));
            }
            return deep;
        }

        @Override
        protected Set<Person> childValue(Set<Person> parentValue) {
            return copy(parentValue);
        }

        @Override
        protected Set<Person> initialValue() {
            return new HashSet<>();
        }
    };

    private static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + '\'' + ", age=" + age + '}';
        }
    }
}
