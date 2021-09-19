# HelloKt 😸

My Kotlin playground:

- new kotlin version
- Gradle Kts
- kotest

## Playground `WeakHashMap`: `ConcurrentModificationException` and `GC`

when `WeakHashMap` key is `gc`ed in the `WeakHashMap` iteration,
`WeakHashMap` iteration will throw `ConcurrentModificationException`?

Demo Result: NO.

Demo code: [`WeakHashMapGcIteration`](src/test/kotlin/playground/weakhashmap/WeakHashMapGcIteration.kt)

```sh
./gradlew execTestMain -P mainClass=playground.weakhashmap.WeakHashMapGcIterationKt
```
