package com.google.engedu.wordstack

// https://stackoverflow.com/a/20589105/10797722
fun scramble(random: java.util.Random, inputString: String?): String {
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