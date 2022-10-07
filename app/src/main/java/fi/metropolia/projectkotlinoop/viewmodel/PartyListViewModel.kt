package fi.metropolia.projectkotlinoop.viewmodel

import androidx.lifecycle.*
import fi.metropolia.projectkotlinoop.repository.MemberRepository
import fi.metropolia.projectkotlinoop.data.MemberDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * ViewModel class implemented to sent data from repository to UI Fragments
 */

class PartyListViewModel(memberDao: MemberDao) : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    //Get instance of repository
    val repository = MemberRepository(memberDao)

    //Mapping from databse a list of party that is same as selected party from user
    val partyDisplayed: LiveData<List<String>> = Transformations.map(repository.allPartiesMembers){
            list -> list.map {it.party}.toSet().toList()
    }

    init {
        getParties()
    }

    //function runs in view model/CoRoutine Scope to load database from internet
    private fun getParties(){
        viewModelScope.launch {
           try {
               repository.loadDatabase()
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

