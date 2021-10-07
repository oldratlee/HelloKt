package playground.lunar

import com.nlf.calendar.Lunar
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    require(args.size in 3..4)
    val searchYears = if (args.size == 4) args[3].toInt() else 500

    val date = LocalDate.of(args[0].toInt(), args[1].toInt(), args[2].toInt())
    val lunar = date.toLunar()

    println("search $searchYears years, from $date to ${date.plusYears(searchYears.toLong())}")

    // println("$date ${date.month.value} ${date.dayOfMonth} - $lunar ${lunar.month} ${lunar.day}\n")
    println("$date - ${lunar.monthInChinese}月${lunar.dayInChinese}")
    for (count in 1 until searchYears) {
        val d = date.plusYears(count.toLong())
        val l = d.toLunar()

        // println("$d ${d.month.value} ${d.dayOfMonth} - $l ${l.month} ${l.day}")
        if (l.month.absoluteValue == lunar.month.absoluteValue && l.day == lunar.day) {
            println("$d - ${l.monthInChinese}月${l.dayInChinese}")
        }
    }
}

private fun LocalDate.toDate(): Date {
    val zoneId = ZoneId.systemDefault()
    val zdt: ZonedDateTime = this.atStartOfDay(zoneId)
    return Date.from(zdt.toInstant())
}

private fun LocalDate.toLunar(): Lunar {
    return Lunar(this.toDate())
}
