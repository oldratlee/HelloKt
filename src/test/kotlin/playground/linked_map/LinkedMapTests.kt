package playground.linked_map

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LinkedMapTests : StringSpec({
    "key set keep order" {
        val linkedMap: LinkedHashMap<String, String> = linkedMapOf(
            "k1" to "v1",
            "k2" to "v2",
            "k3" to "v3",
        )
        linkedMap.keys.toList() shouldBe listOf("k1", "k2", "k3")
    }

    "key set keep order, large key count" {
        val pairList: List<Pair<String, String>> = (0 until 1000).map { Pair("k$it", "v$it") }

        val linkedMap: LinkedHashMap<String, String> = linkedMapOf(*pairList.toTypedArray())
        val keys: List<String> = pairList.map { it.first }

        linkedMap.keys.toList() shouldBe keys
    }
})
