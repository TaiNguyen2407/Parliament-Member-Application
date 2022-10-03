package fi.metropolia.projectkotlinoop

import android.app.Application
import fi.metropolia.projectkotlinoop.context.MyApp
import fi.metropolia.projectkotlinoop.data.MemberDB

class MemberApplication: Application() {
    val database: MemberDB by lazy { MemberDB.getDatabase(this) }
    val repository by lazy { MemberRepository(database.memberDao()) }
}