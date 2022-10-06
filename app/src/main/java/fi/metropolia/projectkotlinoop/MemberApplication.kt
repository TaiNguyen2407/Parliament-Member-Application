package fi.metropolia.projectkotlinoop

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.work.*
import fi.metropolia.projectkotlinoop.data.MemberDB
import fi.metropolia.projectkotlinoop.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MemberApplication: Application() {
    val database: MemberDB by lazy { MemberDB.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }
    companion object {
        lateinit var appContext: Context
    }


    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit(){
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setupRecurringWork()
        }
    }
    private fun setupRecurringWork(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                setRequiresDeviceIdle(true)
            }
            .build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        Timber.d("Periodic Work request for sync is scheduled")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )

    }
}