package fi.metropolia.projectkotlinoop

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fi.metropolia.projectkotlinoop.data.Member
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember

class MemberRepository(private val memberDao: MemberDao) {
    val allParties: LiveData<List<ParliamentMember>> = memberDao.getAll()
    val allPartiesMembers: LiveData<List<ParliamentMember>> = memberDao.getAll()
}