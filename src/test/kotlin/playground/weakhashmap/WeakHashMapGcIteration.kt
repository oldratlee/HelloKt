package playground.weakhashmap

import java.util.*
import kotlin.random.Random

/**
 * when [WeakHashMap] key is `gc`ed in the [WeakHashMap] iteration,
 * [WeakHashMap] iteration will throw [ConcurrentModificationException]?
 *
 * Result
 * ---------------
 *
 * NO.
 */
fun main() {
    println(Key(271828)) // this Key 271828 will be GCed soon

    val (keyList, weakHashMap) = prepareData()

    repeat(3) { round ->
        val beginKeyListSize = keyList.size
        val beginWeakHashMapSize = weakHashMap.size
        println("[round $round] begin! keyListSize: $beginKeyListSize, weakHashMapSize: $beginWeakHashMapSize")

        for (entry in weakHashMap.entries) {
            check(entry.key.num == entry.value)

            if (keyList.isEmpty()) {
                println("keyList is empty, stop demo")
                return
            }
            if (Random.nextDouble() >= 0.05) continue

            val removed = keyList.removeAt(Random.nextInt(keyList.size))
            println("[round $round] KeyList: removed $removed, keyListSize: ${keyList.size}, weakHashMapSize: ${weakHashMap.size}")
            System.gc()
        }
        println("[round $round] end! keyListSize: $beginKeyListSize -> ${keyList.size}, weakHashMapSize: $beginWeakHashMapSize -> ${weakHashMap.size}")
    }
}

private fun prepareData(): Pair<MutableList<Key>, WeakHashMap<Key, Int>> {
    println("Preparing data...")

    val keyList = (0 until 1000).map { Key(it) }.toMutableList()

    // hold Keys in WeakHashMap
    val weakHashMap = WeakHashMap<Key, Int>()
    weakHashMap.putAll(keyList.associateWith { e -> e.num })

    return Pair(keyList, weakHashMap)
}


private data class Key(val num: Int) {
    // private val bigMem = (1..100).toList()

    protected fun finalize() {
        println("finalize $this")
    }
}
