package fi.metropolia.projectkotlinoop.viewmodel

import androidx.lifecycle.*
import fi.metropolia.projectkotlinoop.repository.MemberRepository
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

/**
 * ViewModel class implemented to sent data from repository to UI Fragments
 */

class PartyMemberListViewModel(memberDao: MemberDao) : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    //Get instance of repository
    private val repository = MemberRepository(memberDao)

    lateinit var memberDisplayed: LiveData<List<ParliamentMember>>

    //Function to filter member that is same as selected member from user
    fun chosenParty(chosenParty: String) {
        memberDisplayed = Transformations.map(repository.allPartiesMembers){ list ->
            list.filter { it.party == chosenParty }
        }
    }

    init {
        getMembers()
    }

    //Function that runs in view model/Coroutine Scope that loads database from internet
    private fun getMembers(){
        viewModelScope.launch {
            try {
                repository.loadDatabase()
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