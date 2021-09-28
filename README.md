# HelloKt 😸

My Kotlin playground:

- new kotlin version
- Gradle Kts
- kotest

## Playground 

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [🍺 `WeakHashMap`: `ConcurrentModificationException` and `GC`](#-weakhashmap-concurrentmodificationexception-and-gc)
- [🍺 `WeakHashMap`: the copy overhead of `WeakHashMap`](#-weakhashmap-the-copy-overhead-of-weakhashmap)
- [🍺 the queue time of tasks in `ThreadPoolExecutor`](#-the-queue-time-of-tasks-in-threadpoolexecutor)
- [🍺 `AhoCorasickDoubleArrayTrie` performance demo](#-ahocorasickdoublearraytrie-performance-demo)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

### 🍺 `WeakHashMap`: `ConcurrentModificationException` and `GC`

when `WeakHashMap` key is `gc`ed in the `WeakHashMap` iteration,
`WeakHashMap` iteration will throw `ConcurrentModificationException`?

Demo Result: NO.

Demo code: [`WeakHashMapGcIteration`](src/test/kotlin/playground/weakhashmap/WeakHashMapGcIteration.kt)

```sh
./gradlew execTestMain -P mainClass=playground.weakhashmap.WeakHashMapGcIterationKt
```

### 🍺 `WeakHashMap`: the copy overhead of `WeakHashMap`

Demo Result: copy ~30M items per second.  
test on my MacBookPro16(Intel Core i9).

Demo code: [`WeakHashMapCopyOverheadDemo`](src/test/kotlin/playground/weakhashmap/WeakHashMapCopyOverheadDemo.kt)

```sh
./gradlew execTestMain -P mainClass=playground.weakhashmap.WeakHashMapCopyOverheadDemoKt
```

### 🍺 the queue time of tasks in `ThreadPoolExecutor`

Result:

average queue time = queue size / maximumPoolSize * average task execution time

Demo code: [`TaskQueueTimeThreadPoolExecutorDemo`](src/test/kotlin/playground/threadpool/TaskQueueTimeThreadPoolExecutorDemo.kt)

```sh
./gradlew execTestMain -P mainClass=playground.threadpool.TaskQueueTimeThreadPoolExecutorDemoKt
```

### 🍺 `AhoCorasickDoubleArrayTrie` performance demo

[`AhoCorasickDoubleArrayTrie`](https://github.com/hankcs/AhoCorasickDoubleArrayTrie) performance simple demo.

Result: hit search ~O(1) to the size of search dictionary.

Demo code: [`AhoCorasickDoubleArrayTrieDemo`](src/test/kotlin/playground/text/AhoCorasickDoubleArrayTrieDemo.kt)

```sh
./gradlew execTestMain -P mainClass=playground.text.AhoCorasickDoubleArrayTrieDemoKt
```
