package fi.metropolia.projectkotlinoop.viewmodel

import androidx.lifecycle.*
import fi.metropolia.projectkotlinoop.context.MemberApplication
import fi.metropolia.projectkotlinoop.repository.MemberRepository
import fi.metropolia.projectkotlinoop.data.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PartyMemberInformationViewModel: ViewModel(){
    private val memberDao: MemberDao = MemberDB.getDatabase(MemberApplication.appContext).memberDao()
    // The MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    private val _hetekaId = MutableLiveData<Int>()

    private val memberRepository = MemberRepository(memberDao)

    val likesNum: LiveData<Int> = memberRepository.getLikesNumber()
    val dislikesNum: LiveData<Int> = memberRepository.getDislikesNumber()


    fun setHetekaId(hetekaId: Int){
        _hetekaId.value = hetekaId
    }

    fun insertLikesData(likesOrDislikes: MemberLikes){
        viewModelScope.launch {
            memberRepository.insert(likesOrDislikes)
        }
    }

    init {
        getMemberInformation()
    }

    private fun getMemberInformation(){
        viewModelScope.launch {
            try {
                memberRepository.loadDatabase()
            } catch (e: Exception){
                _status.value = "Failure ${e.message}"
            }
        }
    }
}

class PartyMemberInformationViewModelFactory: ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartyMemberInformationViewModel::class.java)){
            return PartyMemberInformationViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
