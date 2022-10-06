package fi.metropolia.projectkotlinoop.data

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*


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
        parcel.readString().toString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(hetekaId)
        parcel.writeInt(seatNumber)
        parcel.writeString(lastname)
        parcel.writeString(firstname)
        parcel.writeString(party)
        parcel.writeByte(if (minister) 1 else 0)
        parcel.writeString(pictureUrl)
    }

    override fun describeContents(): Int {
        return 0
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


@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(member: List<ParliamentMember>)

    @Query("SELECT * from ParliamentMember")
    fun getAll(): LiveData<List<ParliamentMember>>

    @Query("SELECT hetekaId from ParliamentMember")
    fun getHetekaId(): LiveData<List<Int>>

}