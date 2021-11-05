package net.downloadpizza.ccc2021.challenges

import net.downloadpizza.ccc2021.Solution

object C2 : Solution {
    override fun solve(input: String): String {
        val ecos = input.trim().lines().drop(1).map {
            val parts = it.split(" ").map(String::toInt)
            Economist2(parts[0], parts.drop(1))
        }

        val trades = ecos.map {
            val give = it.values.minOrNull()!!
            val taker = ecos.filter { t -> it !== t }
                .maxWithOrNull(compareBy<Economist2> {
                    it.values.maxOrNull()!!
                }.thenComparing(compareByDescending { it.id }))!!
            val take = taker.values.maxOrNull()!!
            if (give >= take)
                null
            else
                Trade2(it.id, give, taker.id, taker.values.maxOrNull()!!)
        }
        return trades.joinToString("\n") {
            if (it == null)
                "NO TRADE"
            else
                "${it.giverId} ${it.giverValue} ${it.takerId} ${it.takerValue}"
        }
    }
}

data class Economist2(val id: Int, val values: List<Int>)

data class Trade2(val giverId: Int, val giverValue: Int, val takerId: Int, val takerValue: Int)