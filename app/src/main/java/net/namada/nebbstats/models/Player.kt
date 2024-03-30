package net.namada.nebbstats.models

import java.io.Serializable

class Player(val id: String,
    val moniker: String ,
    val tnamAddress: String,
    val score: Double,
    val rankingPosition: Int,
    val avatarUrl: String?,
    val upTimePercentage: Float?,
    val stakePercentage: Float?,
    val isBanned: Boolean
    ): Serializable