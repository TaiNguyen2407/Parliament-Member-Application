package fi.metropolia.projectkotlinoop.data

import androidx.lifecycle.LiveData
import androidx.room.*



@Entity
data class MemberLikes(
    @PrimaryKey
    val hetekaId: Int,
    val likesNumber: Int ,
    val dislikeNumber: Int,
    val amount: Int
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

    @Query("SELECT likesNumber from MemberLikes")
    fun getLikesNumberInc(): Int

}