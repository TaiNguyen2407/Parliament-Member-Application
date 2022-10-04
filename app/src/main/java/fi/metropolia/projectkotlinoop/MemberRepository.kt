package fi.metropolia.projectkotlinoop

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember
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
}