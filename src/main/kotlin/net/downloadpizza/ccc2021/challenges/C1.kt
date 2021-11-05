package net.downloadpizza.ccc2021.challenges

import net.downloadpizza.ccc2021.Solution
import net.downloadpizza.ccc2021.util.readIntMatrix

object C1 : Solution {
    override fun solve(input: String): String {
        val baskets = readIntMatrix(input.trim())
        return baskets.map {
            it.drop(1).sum()
        }.joinToString("\n")
    }
}