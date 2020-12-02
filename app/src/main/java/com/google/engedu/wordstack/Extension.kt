package com.google.engedu.wordstack

import java.util.*

// https://stackoverflow.com/a/20589105/10797722
fun scramble(random: Random, inputString: String?): String {
    // Convert your string into a simple char array:
    val a = inputString.orEmpty().toCharArray()

    // Scramble the letters using the standard Fisher-Yates shuffle,
    for (i in a.indices) {
        val j: Int = random.nextInt(a.size)
        // Swap letters
        val temp = a[i]
        a[i] = a[j]
        a[j] = temp
    }
    return String(a)
}

fun scramble(word1: String?, word2: String?, random: Random): String {
    // Convert your string into a simple char array:
    val a = word1.orEmpty().toCharArray()
    val b = word2.orEmpty().toCharArray()
    var c = ""

    var length = (word1 + word2).length
    var countA = 0
    var countB = 0
    while (length > 0) {
        length--
        if (random.nextInt(2) == 0 && countA < a.size) {
            c += word1?.get(countA)
            countA++
        } else if (countB < b.size) {
            c += word2?.get(countB)
            countB++
        } else length++
    }
    return c
}

fun String.removeLastChars(): String {
    return if (this.isEmpty()) this
    else this.substring(0, this.length - 1)
}