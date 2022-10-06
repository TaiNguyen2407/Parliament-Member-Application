package fi.metropolia.projectkotlinoop.viewmodel

import androidx.lifecycle.*
import fi.metropolia.projectkotlinoop.MemberRepository
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class PartyMemberListViewModel(memberDao: MemberDao) : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    // The external immutable LiveData for the request status

    val repository = MemberRepository(memberDao)

    lateinit var memberDisplayed: LiveData<List<ParliamentMember>>


    fun chosenParty(chosenParty: String) {
        memberDisplayed = Transformations.map(repository.allPartiesMembers){ list ->
            list.filter { it.party == chosenParty }
        }
    }

    init {
        getMembers()
    }

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