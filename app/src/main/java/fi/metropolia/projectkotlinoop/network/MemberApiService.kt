package fi.metropolia.projectkotlinoop.network
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Class used to implement moshi and retrofit
 * Purpose is to fetch data from internet under JSON file
 * then parse it into needed value type
 */

private const val BASE_URL = "https://users.metropolia.fi/~peterh/"

//Implement moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Implement retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

//Getting the correct file from the base URL
interface MemberApiService{
    @GET("seating.json")
    suspend fun getMemberList(): List<ParliamentMember>
}

object MemberApi{
    val retrofitService: MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java)
    }
}
