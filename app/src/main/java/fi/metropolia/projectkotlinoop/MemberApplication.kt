package fi.metropolia.projectkotlinoop

import android.app.Application
import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import fi.metropolia.projectkotlinoop.data.MemberDB
import fi.metropolia.projectkotlinoop.work.RefreshDataWorker
import java.util.concurrent.TimeUnit

class MemberApplication: Application() {
    val database: MemberDB by lazy { MemberDB.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
    companion object {
        lateinit var appContext: Context
    }
    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    private fun setupRecurringWork(){
        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS).build()
    }
}