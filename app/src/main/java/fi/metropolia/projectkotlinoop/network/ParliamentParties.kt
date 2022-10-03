package fi.metropolia.projectkotlinoop.network

import com.squareup.moshi.Json
import fi.metropolia.projectkotlinoop.data.Member

data class ParliamentParties(
    val party: Member,
    @Json(name = "party") val PartyDisplayed: Member,
    val firstname:Member,
    @Json(name = "firstname") val MemberFirstNameDisplayed: Member,
    val lastname:Member,
    @Json(name = "lastname") val MemberLastNameDisplayed: Member,
)

