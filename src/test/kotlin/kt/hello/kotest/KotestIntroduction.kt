/**
 * https://kotest.io/docs/framework/framework.html
 */
package kt.hello.kotest

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.startWith
import kotlin.math.max

/**
 * Test with Style
 */
class MyTests : StringSpec({
    "length should return size of string" {
        "hello".length shouldBe 5
    }

    "startsWith should test for a prefix" {
        "world" should startWith("wor")
    }
})

/**
 * Check all the Tricky Cases With Data Driven Testing
 */
class StringSpecExample : StringSpec({
    "maximum of two numbers" {
        forAll(
            row(1, 5, 5),
            row(1, 0, 1),
            row(0, 0, 0)
        ) { a, b, max ->
            max(a, b) shouldBe max
        }
    }
})
