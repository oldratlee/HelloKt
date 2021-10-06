package playground.guava

import com.google.common.base.Preconditions
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.apache.commons.collections4.MapUtils

/**
 * Preconditions in Google Guava
 * https://javadevcentral.com/preconditions-in-google-guava
 *
 * Google Guava Preconditions does not allow us to throw custom exceptions.
 */
class CheckUtilShowcases : StringSpec({
    "null safe check functions, return boolean" {
        MapUtils.isEmpty(null) shouldBe true
        MapUtils.isEmpty(emptyMap<Any, Any>()) shouldBe true
        MapUtils.isEmpty(mapOf(1 to 2)) shouldBe false
    }

    "null safe check functions, throw exception" {
        val exception = shouldThrow<IllegalArgumentException> {
            Preconditions.checkArgument(false, "msg")
        }
        exception.message shouldBe "msg"
    }
})
