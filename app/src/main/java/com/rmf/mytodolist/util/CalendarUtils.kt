package com.rmf.mytodolist.util

import java.time.LocalDate

fun String.toLocalDate(): LocalDate? {
    return try {
        val dateSplit = this.split("-")
        val year = dateSplit[2].toInt()
        val month = dateSplit[1].toInt()
        val day = dateSplit[0].toInt()

        LocalDate.of(year, month, day)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}