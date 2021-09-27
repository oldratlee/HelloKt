package playground.text

import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * A demo of [AhoCorasickDoubleArrayTrie].
 *
 * @see <https://github.com/hankcs/HanLP>
 * @see <https://github.com/hankcs/AhoCorasickDoubleArrayTrie>
 * @see <https://mvnrepository.com/artifact/com.hankcs/hanlp>
 */
fun main() {
    val dict = sortedMapOf(
        "1" to "1",
        "01" to "01",
        "23" to "23",
        "123" to "123",
        "34" to "34",
    ) as TreeMap<String, String>
    val text = "1234567890".repeat(1000)

    measureTimeMillis {
        val trie = AhoCorasickDoubleArrayTrie(dict)
        (0 until 10_000).forEach { _ ->
            trie.parseText(text)
        }
    }.let { println("[Warmup] small dict(${dict.size}) time ${it}ms") }

    var trie = AhoCorasickDoubleArrayTrie(dict)

    measureTimeMillis {
        (0 until 10_000).forEach { _ ->
            trie.parseText(text)
        }
    }.let {
        println("small dict(${dict.size}) time ${it}ms")
        //println("\n\t${trie.parseText(text)}")
    }

    dict.putAll(combined().associateWith { e -> e })
    trie = AhoCorasickDoubleArrayTrie(dict)

    measureTimeMillis {
        (0 until 10_000).forEach { _ ->
            trie.parseText(text)
        }
    }.let {
        println("big dict(${dict.size}) time ${it}ms")
        //println("\n\t${trie.parseText(text)}")
    }
}

private fun combined(): List<String> {
    val ret = mutableListOf<String>()
    ('a'..'z').forEach { c1 ->
        ('a'..'z').forEach { c2 ->
            ret.add(c1.toString() + c2)
        }
    }
    return ret
}

/*
---------------
An output
---------------

[Warmup] small dict(5) time 1375ms
small dict(5) time 1011ms
big dict(681) time 993ms
 */
