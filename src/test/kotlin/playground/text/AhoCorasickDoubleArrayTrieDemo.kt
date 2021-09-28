package playground.text

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * A performance demo of [AhoCorasickDoubleArrayTrie].
 *
 * Result
 * ---------------
 *
 * hit search ~O(1) to the size of search dictionary.
 *
 * @see <a href="https://github.com/hankcs/AhoCorasickDoubleArrayTrie>AhoCorasickDoubleArrayTrie github repo</a>
 * @see <a href="https://mvnrepository.com/artifact/com.hankcs/aho-corasick-double-array-trie>Maven Repository: aho-corasick-double-array-trie</a>
 * @see <a href="https://github.com/hankcs/HanLP>HanLP github repo</a>
 * @see <a href="https://mvnrepository.com/artifact/com.hankcs/hanlp>Maven Repository: hanlp</a>
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
        val trie = AhoCorasickDoubleArrayTrie<String>().apply { build(dict) }
        repeat(10_000) { _ ->
            trie.parseText(text)
        }
    }.let { println("[Warmup] small dict(${dict.size}) time ${it}ms") }

    var trie = AhoCorasickDoubleArrayTrie<String>().apply { build(dict) }

    measureTimeMillis {
        repeat(10_000) { _ ->
            trie.parseText(text)
        }
    }.let {
        println("small dict(${dict.size}) time ${it}ms")
        //println("\n\t${trie.parseText(text)}")
    }

    dict.putAll(generateKeys().associateWith { e -> e })
    trie = AhoCorasickDoubleArrayTrie<String>().apply { build(dict) }

    measureTimeMillis {
        repeat(10_000) { _ ->
            trie.parseText(text)
        }
    }.let {
        println("big dict(${dict.size}) time ${it}ms")
        //println("\n\t${trie.parseText(text)}")
    }
}

private fun generateKeys(): List<String> {
    val ret = mutableListOf<String>()
    ('a'..'z').forEach { c1 ->
        ('a'..'z').forEach { c2 ->
            ret.add(c1.toString() + c2)
        }
    }
    return ret
}

/*
-----------------------------------------
An output

test on my MacBookPro16, Intel Core i9
-----------------------------------------

[Warmup] small dict(5) time 1375ms
small dict(5) time 1011ms
big dict(681) time 993ms
 */
