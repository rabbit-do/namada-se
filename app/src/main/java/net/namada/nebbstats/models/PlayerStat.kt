package net.namada.nebbstats.models

import java.io.Serializable

class PlayerStat ( val sClassCategoryCount: Int, val playerCompletedCount: Int,
                   val players : List<String>): Serializable