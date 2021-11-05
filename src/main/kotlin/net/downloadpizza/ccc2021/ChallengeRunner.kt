package net.downloadpizza.ccc2021

import net.downloadpizza.ccc2021.challenges.*
import net.downloadpizza.ccc2021.util.readIntMatrix
import java.io.File

val res = mapOf(
    C1 to 1,
    C2 to 2,
    C3 to 3,
    C4 to 4,
    C5 to 5,
    C6 to 6,
)

fun testFile(challenge: Int): String = getFile(challenge, "level${challenge}_example.in")
fun testResultFile(challenge: Int): String = getFile(challenge, "level${challenge}_example.out")

fun inputFile(challenge: Int, input: Int) = getFile(challenge, "level${challenge}_$input.in")

fun main() {
    val solution: Solution = C1
    val challenge = res[solution]!!
    val testIn = testFile(challenge)
    val testOut = solution.solve(testIn)
    val testExpect = testResultFile(challenge)
    File("${res[solution]}test.out").writeText(testOut)
    if(testOut.trimEnd() != testExpect.trimEnd() ) { throw Exception("Test files do not match!") }

    for(i in 1..5) {
        val rout = solution.solve(inputFile(challenge, i))
        File("${res[solution]}_${i}.out").writeText(rout)
    }
}

private fun getFile(challenge: Int, filename: String) = String(object {}.javaClass.getResource("c$challenge/$filename").readBytes())

interface Solution {
    fun solve(input: String): String
}