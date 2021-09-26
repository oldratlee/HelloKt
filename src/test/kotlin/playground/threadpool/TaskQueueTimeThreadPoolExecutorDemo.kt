package playground.threadpool

import java.lang.Thread.sleep
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * A demo show
 * the queue time of tasks in [ThreadPoolExecutor]
 */
fun main() {
    val threadPoolExecutor = ThreadPoolExecutor(
        1, 2, 1, TimeUnit.MINUTES,
        ArrayBlockingQueue(10), ThreadPoolExecutor.CallerRunsPolicy()
    )
    warmup(threadPoolExecutor)

    // test task queue
    (0 until 30).map { num ->
        val tick = System.currentTimeMillis()
        threadPoolExecutor.submit {
            println("[${Thread.currentThread().name}] run task $num, queued ${System.currentTimeMillis() - tick}ms")
            sleep(100)
        }
    }.map { it.get() } //.let { println(it) }

    println("\nafter task run: $threadPoolExecutor")
    // cleanup
    check(threadPoolExecutor.shutdownNow().isEmpty())
}

private fun warmup(threadPoolExecutor: ThreadPoolExecutor) {
    // warmup thread pool
    println("before warmup: $threadPoolExecutor")
    (0 until 10).map {
        threadPoolExecutor.submit { sleep(1) }
    }.map { it.get() } //.let { println(it) }
    println("after warmup: $threadPoolExecutor\n")
}

/*
-----------------------
An output
-----------------------

before warmup: java.util.concurrent.ThreadPoolExecutor@7ca48474[Running, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
after warmup: java.util.concurrent.ThreadPoolExecutor@7ca48474[Running, pool size = 1, active threads = 0, queued tasks = 0, completed tasks = 10]

[pool-1-thread-1] run task 0, queued 2ms
[main] run task 12, queued 0ms
[pool-1-thread-2] run task 10, queued 0ms
[main] run task 13, queued 0ms
[pool-1-thread-1] run task 1, queued 104ms
[pool-1-thread-2] run task 2, queued 104ms
[main] run task 16, queued 0ms
[pool-1-thread-1] run task 3, queued 204ms
[pool-1-thread-2] run task 4, queued 204ms
[pool-1-thread-1] run task 5, queued 308ms
[pool-1-thread-2] run task 6, queued 308ms
[main] run task 21, queued 0ms
[pool-1-thread-1] run task 7, queued 412ms
[pool-1-thread-2] run task 8, queued 413ms
[main] run task 24, queued 0ms
[pool-1-thread-1] run task 9, queued 514ms
[pool-1-thread-2] run task 11, queued 514ms
[main] run task 27, queued 0ms
[pool-1-thread-1] run task 14, queued 412ms
[pool-1-thread-2] run task 15, queued 412ms
[main] run task 29, queued 0ms
[pool-1-thread-2] run task 18, queued 408ms
[pool-1-thread-1] run task 17, queued 408ms
[pool-1-thread-2] run task 19, queued 508ms
[pool-1-thread-1] run task 20, queued 508ms
[pool-1-thread-1] run task 22, queued 504ms
[pool-1-thread-2] run task 23, queued 504ms
[pool-1-thread-1] run task 25, queued 507ms
[pool-1-thread-2] run task 26, queued 507ms
[pool-1-thread-1] run task 28, queued 505ms

after task run: java.util.concurrent.ThreadPoolExecutor@7ca48474[Running, pool size = 2, active threads = 0, queued tasks = 0, completed tasks = 33]
 */
