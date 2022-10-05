package fi.metropolia.projectkotlinoop.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField


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
    ): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readString().toString(),
                parcel.readByte() != 0.toByte(),

        ) {
        }

        override fun describeContents(): Int {
                TODO("Not yet implemented")
        }

        override fun writeToParcel(p0: Parcel, p1: Int) {
                TODO("Not yet implemented")
        }

        companion object CREATOR : Parcelable.Creator<ParliamentMember> {
                override fun createFromParcel(parcel: Parcel): ParliamentMember {
                        return ParliamentMember(parcel)
                }

                override fun newArray(size: Int): Array<ParliamentMember?> {
                        return arrayOfNulls(size)
                }
        }
}