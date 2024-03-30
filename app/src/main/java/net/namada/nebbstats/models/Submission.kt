package net.namada.nebbstats.models

data class Submission(val id: String?,
                      val dateReceived: String?,
                      val timeReceived: String?,
                      val fromAddress: String?,
                      val category: String?,
                      val description: String?,
                      val evidence: String?,
                      val eligibleForRoids: String?,
                      val subClass: String?,
                      val comment: String?
)



/**
 *
 */