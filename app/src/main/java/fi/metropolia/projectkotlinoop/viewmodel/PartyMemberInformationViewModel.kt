package fi.metropolia.projectkotlinoop.viewmodel

import android.widget.Button
import androidx.lifecycle.*
import fi.metropolia.projectkotlinoop.MemberRepository
import fi.metropolia.projectkotlinoop.data.MemberDao
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PartyMemberInformationViewModel(private val memberDao: MemberDao) : ViewModel(){
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    lateinit var memberDisplayed: LiveData<List<ParliamentMember>>
    val repository = MemberRepository(memberDao)

    init {
        getMemberInformation()
    }

    fun chosenMember(chosenMember: String){
        memberDisplayed = Transformations.map(repository.allPartiesMembers){
            it.filter { (it.firstname+" "+ it.lastname) == chosenMember }
        }
    }
    fun getMemberInformation(){
        viewModelScope.launch {
            try {
                repository.loadDatabase()
            } catch (e: Exception){
                _status.value = "Failure ${e.message}"
            }
        }
    }
}

class PartyMemberInformationViewModelFactory(private val memberDao: MemberDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartyMemberInformationViewModel::class.java)){
            return PartyMemberInformationViewModel(memberDao) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class")
    }
}
