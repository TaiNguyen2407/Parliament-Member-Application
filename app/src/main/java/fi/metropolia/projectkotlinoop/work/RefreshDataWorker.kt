package fi.metropolia.projectkotlinoop.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import fi.metropolia.projectkotlinoop.context.MemberApplication
import fi.metropolia.projectkotlinoop.repository.MemberRepository
import fi.metropolia.projectkotlinoop.data.MemberDB
import retrofit2.HttpException
import timber.log.Timber

/**
 * Fragment to implement Work Manager by Android Basics in Kotlin
 */

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "fi.metropolia.projectkotlinoop.work.RefreshDataWorker"
    }


    override suspend fun doWork(): Result {
        val dao = MemberDB.getDatabase(MemberApplication.appContext).memberDao()
        val repository = MemberRepository(dao)

        try {
            repository.loadDatabase()
            Timber.d("Work request for sync is run")
        } catch (e: HttpException){
            return Result.retry()
        }
        return Result.success()
    }
}