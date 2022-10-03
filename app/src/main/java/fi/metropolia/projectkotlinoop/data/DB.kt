package fi.metropolia.projectkotlinoop.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import fi.metropolia.projectkotlinoop.MemberRepository
import fi.metropolia.projectkotlinoop.context.MyApp

@Database(entities = [ParliamentMember::class], version = 2, exportSchema = false)
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



@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(member: List<ParliamentMember>)

    /*@Update
    suspend fun update(comment: String)

    @Delete
    suspend fun delete(comment: String)*/

    @Query("SELECT * from ParliamentMember")
    fun getAll(): LiveData<List<ParliamentMember>>

}


