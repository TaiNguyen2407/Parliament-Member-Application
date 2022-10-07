package fi.metropolia.projectkotlinoop.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *Entity and Dao class for Likes/Dislikes feature
 * Data will be stored inside of user's device, not fetch/send from/to the server
 */

@Entity
data class MemberLikes(
    @PrimaryKey
    val hetekaId: Int,
    val likesNumber: Int ,
    val dislikeNumber: Int,
)

@Dao
interface MemberLikesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(likesOrDislikes: MemberLikes)

    @Query("select * from MemberLikes")
    fun getAll(): LiveData<List<MemberLikes>>

    @Query("SELECT likesNumber from MemberLikes ORDER BY hetekaId")
    fun getLikesNumber(): LiveData<Int>

    @Query("SELECT dislikeNumber from MemberLikes order by hetekaId")
    fun getDislikesNumber(): LiveData<Int>

}