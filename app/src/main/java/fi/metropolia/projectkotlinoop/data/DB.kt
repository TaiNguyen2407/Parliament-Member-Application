package fi.metropolia.projectkotlinoop.data

import android.content.Context
import androidx.room.*
import fi.metropolia.projectkotlinoop.MemberApplication

@Database(entities = [ParliamentMember::class, MemberLikes::class], version = 3, exportSchema = false)
abstract class MemberDB: RoomDatabase() {

    abstract fun memberDao(): MemberDao
    abstract fun memberLikesDao(): MemberLikesDao

    companion object{
        @Volatile
        private var INSTANCE: MemberDB? = null
        fun getDatabase(context: Context = MemberApplication.appContext): MemberDB{
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






