package fi.metropolia.projectkotlinoop

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import fi.metropolia.projectkotlinoop.databinding.ActivityMainBinding
import fi.metropolia.projectkotlinoop.network.MemberApi
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    // The external immutable LiveData for the request status
    //val status: LiveData<String> = _status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)


        /*GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT){
            val partyResult = MemberApi.retrofitService.getMemberList()



        }*/

        //implementing read database from network
        //use global scope, slide 14 (last one)
        //Call variables then insert separatedly into fragments and viewmodels
    }

    //add listener to up button, used to navigate back to previous activity/fragment
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }






}
