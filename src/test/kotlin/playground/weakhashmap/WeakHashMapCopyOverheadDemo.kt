package playground.weakhashmap

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.measureTimeMillis

/**
 * Test the copy overhead demo of [WeakHashMap].
 *
 * Result
 * ---------------
 *
 * test on my MacBookPro16(Intel Core i9)
 *
 * copy ~30M items per second.
 */
fun main() {
    println("Warmup".bar())
    runCopy()

    println("target".bar())
    runCopy(keyCount = 10_000, runCount = 10_000)
    runCopy(keyCount = 1_000, runCount = 100_000)
    runCopy(keyCount = 100, runCount = 1_000_000)
    runCopy(keyCount = 10, runCount = 10_000_000)
    runCopy(keyCount = 100, runCount = 1_000_000)
    runCopy(keyCount = 1_000, runCount = 100_000)
    runCopy(keyCount = 10_000, runCount = 10_000)

    println("check warmup".bar())
    runCopy()
}

private fun runCopy(keyCount: Int = 1_000, runCount: Int = 100_000) {
    val keys = (0 until keyCount).map {
        String.format("key-%04d", it)
    }
    val weakHashMap = WeakHashMap<String, String>()
    weakHashMap.putAll(keys.associateWith { e -> e })

    measureTimeMillis {
        // println("[${now()}] start")
        repeat(runCount) {
            WeakHashMap(weakHashMap)
        }
    }.let {
        // println("[${now()}] copy ${keys.size} key, run $runCount time, consume ${String.format("%.3f", it / 10e6)} ms")
        println("[${now()}] copy ${keys.size} key, run $runCount time, consume ${String.format("%s", it)} ms")
    }
}

private fun String.bar(): String = "====================\n${this}\n===================="

private fun now(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    return now.format(formatter)
}

/*
-----------------------
An output

test on my MacBookPro16, Intel Core i9
copy ~30M items per second.
-----------------------

====================
Warmup
====================
[2021-09-28 16:55:23.481] copy 1000 key, run 100000 time, consume 2607 ms
====================
target
====================
[2021-09-28 16:55:26.644] copy 10000 key, run 10000 time, consume 3127 ms
[2021-09-28 16:55:29.377] copy 1000 key, run 100000 time, consume 2731 ms
[2021-09-28 16:55:32.118] copy 100 key, run 1000000 time, consume 2740 ms
[2021-09-28 16:55:35.154] copy 10 key, run 10000000 time, consume 3035 ms
[2021-09-28 16:55:38.176] copy 100 key, run 1000000 time, consume 3022 ms
[2021-09-28 16:55:41.047] copy 1000 key, run 100000 time, consume 2870 ms
[2021-09-28 16:55:43.975] copy 10000 key, run 10000 time, consume 2922 ms
====================
check warmup
====================
[2021-09-28 16:55:46.741] copy 1000 key, run 100000 time, consume 2765 ms
 */
