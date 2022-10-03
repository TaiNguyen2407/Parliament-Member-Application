package fi.metropolia.projectkotlinoop.network
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://users.metropolia.fi/~peterh/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MemberApiService{
    @GET("seating.json")
    suspend fun getMemberList(): List<ParliamentMember>
}

object MemberApi{
    val retrofitService: MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java)
    }
}
