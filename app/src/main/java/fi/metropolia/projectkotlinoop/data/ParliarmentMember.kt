package fi.metropolia.projectkotlinoop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ParliamentMember(
        @PrimaryKey
        val hetekaId: Int,
        val seatNumber: Int = 0,
        val lastname: String,
        val firstname: String,
        val party: String,
        val minister: Boolean = false,
        val pictureUrl: String = ""
    )