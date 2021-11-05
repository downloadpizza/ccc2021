package net.downloadpizza.ccc2021.challenges

import net.downloadpizza.ccc2021.Solution
import net.downloadpizza.ccc2021.util.readIntMatrix

object C3 : Solution {
    override fun solve(input: String): String {
        val matrix = readIntMatrix(input.trim())
        val n: Int = matrix[0][0]
        val ecoLines: List<List<Int>> = matrix.drop(1).take(n)
        val t: Int = matrix.drop(1 + n).first()[0]
        val sweetValueLines: List<List<Int>> = matrix.drop(2 + n).take(t)

        val ecos = ecoLines.map {
            Economist3(it[0], it.drop(1))
        }

        val valueMap = sweetValueLines.associate {
            it[0] to it.drop(1)
        }

        fun getSubjectiveValue(ec: Int, sweetId: Int) = valueMap[sweetId]!![ec - 1]

        val trades = ecos.map { ec ->
            val others = ecos.filter { it !== ec }
            val possibleTrades = others.flatMap { o ->
                o.sweetIds.flatMap { oId ->
                    ec.sweetIds.map { meId ->
                         oId to meId
                    }
                }.filter {
                    getSubjectiveValue(ec.id, it.first) > getSubjectiveValue(ec.id, it.second) &&
                            getSubjectiveValue(o.id, it.second) > getSubjectiveValue(o.id, it.first)
                }.map {
                    Trade3(ec.id, it.second, o.id, it.first)
                }
            }
            possibleTrades.maxWithOrNull(
                compareBy<Trade3> {
                    getSubjectiveValue(it.giverId, it.takerSweetId) - getSubjectiveValue(it.giverId, it.giverSweetId)
                }.thenByDescending {
                    it.takerId
                }.thenByDescending {
                    it.giverSweetId
                }.thenByDescending {
                    it.takerSweetId
                }
            )
        }

        return trades.joinToString("\n") {
            if (it == null)
                "NO TRADE"
            else
                "${it.giverId} ${it.giverSweetId} ${it.takerId} ${it.takerSweetId}"
        }
    }
}

data class Economist3(val id: Int, val sweetIds: List<Int>)

data class Trade3(val giverId: Int, val giverSweetId: Int, val takerId: Int, val takerSweetId: Int)