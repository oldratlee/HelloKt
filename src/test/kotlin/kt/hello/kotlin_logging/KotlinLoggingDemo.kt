package kt.hello.kotlin_logging

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * https://www.baeldung.com/kotlin/kotlin-logging-library
 * https://logging.apache.org/log4j/2.x/manual/configuration.html
 */
fun main() {
    logger.info { "Hello kotlin logging!" }
}
