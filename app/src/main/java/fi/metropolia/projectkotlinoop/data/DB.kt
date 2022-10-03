package fi.metropolia.projectkotlinoop.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import fi.metropolia.projectkotlinoop.context.MyApp

@Database(entities = [Member::class], version = 1, exportSchema = false)
abstract class MemberDB: RoomDatabase() {

    abstract fun memberDao(): MemberDao

    companion object{
        @Volatile
        private var INSTANCE: MemberDB? = null
        fun getDatabase(context: Context = MyApp.appContext): MemberDB{
            return INSTANCE?: synchronized(this){
                var instance = Room.databaseBuilder(
                        context,
                        MemberDB::class.java,
                        "member_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    return instance
            }
        }
    }
}


@Entity(tableName = "Member")
data class Member (
    @PrimaryKey
    val hetekaId: Int,
    @ColumnInfo
    val firstname: String,
    @ColumnInfo
    val lastname: String,
    @ColumnInfo
    val seatNumber: Int,
    @ColumnInfo
    val party: String,
    @ColumnInfo
    val minister: Boolean,
    @ColumnInfo
    val pictureUrl: String,
    /*@ColumnInfo
    val comment: String*/
)

@Dao
interface MemberDao {
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: String)*/

    /*@Update
    suspend fun update(comment: String)

    @Delete
    suspend fun delete(comment: String)*/

    @Query("SELECT * from Member")
    fun getAll(): LiveData<List<ParliamentMember>>

}
