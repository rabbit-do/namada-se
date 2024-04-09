package net.namada.nebbstats.models

class CategoryStat (val yesCount: Int, val noCount: Int, val spamCount: Int,
                    val pendingCount: Int, val totalInt: Int,
                    val distincApprovedAddress:  HashSet<String>)