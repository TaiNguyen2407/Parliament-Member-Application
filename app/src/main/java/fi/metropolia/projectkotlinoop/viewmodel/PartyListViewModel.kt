package fi.metropolia.projectkotlinoop

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.viewModelFactory
import fi.metropolia.projectkotlinoop.data.Member
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import fi.metropolia.projectkotlinoop.data.Parliarment
import fi.metropolia.projectkotlinoop.data.Parliarment.ParliamentMembersData.members
import fi.metropolia.projectkotlinoop.network.MemberApi
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PartyListViewModel(private val memberDao: MemberDao) : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    // The external immutable LiveData for the request status
    //val status: LiveData<String> = _status


    val repository = MemberRepository(memberDao)
    val partyDisplayed: LiveData<List<String>> = Transformations.distinctUntilChanged(
        Transformations.map(repository.allParties){list -> list.map { it.party }.toSet().toMutableList()}
    )

    init {
       getParties()
    }

    private fun getParties(){
        viewModelScope.launch {
           try {
               val partyResult = MemberApi.retrofitService.getParties()
               _status.value = partyResult.toString()
           } catch (e: Exception){
               _status.value = "Failure: ${e.message}"
           }
        }
    }
}

class PartyListViewModelFactory(private val memberDao: MemberDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PartyListViewModel::class.java)){
            return PartyListViewModel(memberDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
