package net.namada.nebbstats.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object NetworkProvider {
    const val YEK = "5b62187f22402a8eb6f8525d9f1b1ff2ddede"
    // Configure retrofit to parse JSON and use coroutines
    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BASIC)
    }
    val client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
            // time out setting
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)

    }.build()
    private val restDBRetrofit = Retrofit.Builder()
        .baseUrl("https://nebb2903-0b82.restdb.io")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
    private val nebbRetrofit = Retrofit.Builder()
        .baseUrl("https://it.api.namada.red")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val appService = restDBRetrofit.create(ApiService::class.java)
    val nebbService = nebbRetrofit.create(NebbService::class.java)
}

interface ApiService {

    @GET("/rest/batch-2903")
    suspend fun getAllSubmission(@Header("x-apikey") apiKey: String): List<NetworkSubmission>

}

interface NebbService{

    @GET("/api/v1/scoreboard/pilots")
    suspend fun getPilotList(@Query("page") page: Int): PlayerResponse

    @GET("/api/v1/scoreboard/crew")
    suspend fun getCrewList(@Query("page") page: Int): PlayerResponse

}