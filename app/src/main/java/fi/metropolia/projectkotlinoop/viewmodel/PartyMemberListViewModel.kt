package fi.metropolia.projectkotlinoop.viewmodel

import androidx.lifecycle.*
import fi.metropolia.projectkotlinoop.MemberRepository
import fi.metropolia.projectkotlinoop.data.Member
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import fi.metropolia.projectkotlinoop.network.MemberApi
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class PartyMemberListViewModel(private val memberDao: MemberDao) : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    // The external immutable LiveData for the request status
    //val status: LiveData<String> = _status

    val respository = MemberRepository(memberDao)
    val memberDisplayed: LiveData<List<String>> = Transformations.distinctUntilChanged(
        Transformations.map(respository.allPartiesMembers){list -> list.map { it.firstname + it.lastname}.toSet().toList()}
    )


    private fun getMembers(){
        viewModelScope.launch {
            try {
                val memberResult = MemberApi.retrofitService.getMemberList()
                _status.value = memberResult.toString()
            } catch (e:Exception){
                _status.value = "failure: ${e.message}"
            }
        }
    }
}

class PartyMemberListViewModelFactory(private val memberDao: MemberDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartyMemberListViewModel::class.java)){
            return PartyMemberListViewModel(memberDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}