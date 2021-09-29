package playground.ttl.deepcopy

import com.alibaba.ttl.TransmittableThreadLocal
import kotlin.concurrent.thread

fun main() {
    context.get().add(Person("cat", 18))

    thread {
        println(context.get())
    }.join()
}

private val context: TransmittableThreadLocal<MutableSet<Person>> =
    object : TransmittableThreadLocal<MutableSet<Person>>() {
        override fun copy(parentValue: MutableSet<Person>): MutableSet<Person> = copyByJavaFor(parentValue)

        override fun childValue(parentValue: MutableSet<Person>): MutableSet<Person> = copyByJavaFor(parentValue)

        override fun initialValue(): MutableSet<Person> = hashSetOf()
    }

private data class Person(val name: String, val age: Int)


////////////////////////////////////////////////////////////////////////////////


/**
 * use java old school for statement.
 *
 * high performance for both big and small set
 */
private fun copyByJavaFor(parentValue: MutableSet<Person>): MutableSet<Person> = hashSetOf<Person>().also {
    for (person in parentValue) {
        it.add(person.copy(name = "${person.name}+1", age = person.age + 1))
    }
}

/**
 * use simple and direct kotlin style.
 *
 * - poor performance for big set
 * - high performance for small set
 */
private fun copyByKotlinStyle(parentValue: MutableSet<Person>): MutableSet<Person> =
    parentValue.map { person ->
        person.copy(name = "${person.name}+1", age = person.age + 1)
    }.toHashSet()

/**
 * use Kotlin [Sequence].
 *
 * - high performance for big set
 * - poor performance for small set
 */
private fun copyByKotlinSequence(parentValue: MutableSet<Person>): MutableSet<Person> =
    parentValue.asSequence().map { person ->
        person.copy(name = "${person.name}+1", age = person.age + 1)
    }.toCollection(hashSetOf())
