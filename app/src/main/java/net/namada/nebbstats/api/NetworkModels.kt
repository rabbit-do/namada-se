package net.namada.nebbstats.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.namada.nebbstats.models.Player
import net.namada.nebbstats.models.Submission

@JsonClass(generateAdapter = true)
data class NetworkSubmission (

    @Json(name="_id"                ) var Id               : String? = null,
    @Json(name="Received"           ) var Received         : String? = null,
    @Json(name="Time_CET"           ) var TimeCET          : String? = null,
    @Json(name="From"               ) var From             : String? = null,
    @Json(name="Category"           ) var Category         : String? = null,
    @Json(name="Description"        ) var Description      : String? = null,
    @Json(name="Evidence"           ) var Evidence         : String? = null,
    @Json(name="Eligible_for_ROIDS" ) var EligibleForROIDS : String? = null,
    @Json(name="S_Class_Subclass"   ) var SClassSubclass   : String? = null,
    @Json(name="Comments"           ) var Comments         : String? = null

)

fun NetworkSubmission.toSubmissionModel(): Submission {
    return Submission(
        id = this.Id,
        dateReceived = this.Received,
        timeReceived = this.TimeCET,
        fromAddress =  this.From,
        category = this.Category,
        description = this.Description,
        evidence = this.Evidence,
        eligibleForRoids = this.EligibleForROIDS,
        subClass = this.SClassSubclass,
        comment = this.Comments
    )
}

fun List<NetworkSubmission>.toListSubmissionModel(): List<Submission>{
    return map {
        it.toSubmissionModel()
    }
}
/**
 * curl --location 'https://namada-4f22.restdb.io/rest/submission' \
 * --header 'accept: application/json' \
 * --header 'x-apikey: 6604efd5d34bb0517c8e4f97' \
 * --header '4d22ad7cfa7c7b5a04d4c6550f1ae3ab9072d;'
 *
 * {
 *         "_id": "6604e8ce89a2764b0002f7dc",
 *         "": "1",
 *         "Received": "2024-02-02T00:00:00.000Z",
 *         "Time CET": "2024-03-28T16:38:00.000Z",
 *         "From": "tpknam1qqqu2hwp6wqkuz84arxg4mqhaa46t9pdcq7mmcdcgw5tckkeq0tgudple6q",
 *         "Category": "Providing Public RPC as a service",
 *         "Description": "Public RPC as a service for Shielded Expedition network.",
 *         "Evidence": "https://rpc-1.namada.n1stake.com/",
 *         "Eligible for ROIDS": "Yes",
 *         "S Class Subclass": "Operating Namada Infrastructure as a service",
 *         "Comments": "RPC is functioning well and synced w/ chain head"
 *     }
 */

@JsonClass(generateAdapter = true)
data class PlayerResponse (

    @Json(name="pagination" ) var pagination : Pagination?        = Pagination(),
    @Json(name="players"    ) var players    : List<NetworkPlayer> = emptyList()

)
@JsonClass(generateAdapter = true)
data class Pagination (

    @Json(name="current_page" ) var currentPage : Int?    = null,
    @Json(name="next_page"    ) var nextPage    : Int?    = null,
    @Json(name="prev_page"    ) var prevPage    : String? = null,
    @Json(name="total"        ) var total       : Int?    = null

)
@JsonClass(generateAdapter = true)
data class NetworkPlayer(
        @Json(name="id"               ) var id              : String,
        @Json(name="moniker"          ) var moniker         : String?  = null,
        @Json(name="player_address"   ) var playerAddress   : String  ,
        @Json(name="score"            ) var score           : Double?     = null,
        @Json(name="ranking_position" ) var rankingPosition : Int?     = null,
        @Json(name="avatar_url"       ) var avatarUrl       : String?  = null,
        @Json(name="uptime"           ) var uptime          : Float?  = null,
        @Json(name="stake"            ) var stake           : Float?  = null,
        @Json(name="is_banned"        ) var isBanned        : Boolean? = null

    )

fun NetworkPlayer.toPlayerModel(): Player {
    return Player(
        id = this.id,
        moniker = this.moniker ?: "",
        tnamAddress = this.playerAddress,
        score = this.score ?: Double.MAX_VALUE,
        rankingPosition = this.rankingPosition ?: Int.MAX_VALUE,
        avatarUrl = this.avatarUrl ?: "",
        upTimePercentage =  this.uptime ?: 0F,
        stakePercentage = this.stake ?: 0F,
        isBanned = this.isBanned ?: false
    )
}

fun List<NetworkPlayer>.toListPlayerModel(): List<Player>{
    return map { it.toPlayerModel() }
}
