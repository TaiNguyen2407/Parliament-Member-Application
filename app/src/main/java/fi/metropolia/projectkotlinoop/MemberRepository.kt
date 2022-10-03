package fi.metropolia.projectkotlinoop

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fi.metropolia.projectkotlinoop.data.Member
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import fi.metropolia.projectkotlinoop.network.MemberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemberRepository(private val memberDao: MemberDao) {
    val allParties: LiveData<List<ParliamentMember>> = memberDao.getAll()
    val allPartiesMembers: LiveData<List<ParliamentMember>> = memberDao.getAll()

    //function to read database from network

}