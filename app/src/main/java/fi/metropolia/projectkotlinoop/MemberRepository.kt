package fi.metropolia.projectkotlinoop

import androidx.lifecycle.LiveData
import fi.metropolia.projectkotlinoop.data.*
import fi.metropolia.projectkotlinoop.network.MemberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemberRepository(private val memberDao: MemberDao) {

    val allPartiesMembers: LiveData<List<ParliamentMember>> = memberDao.getAll()
    //function to read database from network
    suspend fun loadDatabase() {
        withContext(Dispatchers.IO) {
            val partyResult = MemberApi.retrofitService.getMemberList()
            memberDao.insertAll(partyResult)
        }
    }

    private val dao = MemberDB.getDatabase(MemberApplication.appContext).memberLikesDao()

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