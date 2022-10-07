package fi.metropolia.projectkotlinoop.repository

import androidx.lifecycle.LiveData
import fi.metropolia.projectkotlinoop.context.MemberApplication
import fi.metropolia.projectkotlinoop.data.*
import fi.metropolia.projectkotlinoop.network.MemberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository Class implemented to decide whether to fetch data from internet or local storage
 * Makes it easier and more structural for debugging
 */

class MemberRepository(private val memberDao: MemberDao) {
    //Get instance of Dao for Likes/Dislikes Feature
    private val dao = MemberDB.getDatabase(MemberApplication.appContext).memberLikesDao()
    val allPartiesMembers: LiveData<List<ParliamentMember>> = memberDao.getAll()

    //function to read database from network
    suspend fun loadDatabase() {
        withContext(Dispatchers.IO) {
            val partyResult = MemberApi.retrofitService.getMemberList()
            memberDao.insertAll(partyResult)
        }
    }


    fun getLikesNumber(): LiveData<Int>{
        return dao.getLikesNumber()
    }

    fun getDislikesNumber(): LiveData<Int>{
        return dao.getDislikesNumber()
    }

    //function insert data into MemberLikes table
    suspend fun insert(likesOrDislikes: MemberLikes){
        dao.insert(likesOrDislikes)
    }



}