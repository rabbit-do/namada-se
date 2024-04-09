package net.namada.nebbstats.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

object NetworkProvider {
    const val YEK = "6604efd5d34bb0517c8e4f97"
    // Configure retrofit to parse JSON and use coroutines
    private val restDBRetrofit = Retrofit.Builder()
        .baseUrl("https://namada-4f22.restdb.io/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val nebbRetrofit = Retrofit.Builder()
        .baseUrl("https://it.api.namada.red")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val appService = restDBRetrofit.create(ApiService::class.java)
    val nebbService = nebbRetrofit.create(NebbService::class.java)
}

interface ApiService {

    @GET("rest/standard-submissions")
    suspend fun getAllSubmission(@Header("x-apikey") apiKey: String): List<NetworkSubmission>

}

interface NebbService{

    @GET("/api/v1/scoreboard/pilots")
    suspend fun getPilotList(@Query("page") page: Int): PlayerResponse

    @GET("/api/v1/scoreboard/crew")
    suspend fun getCrewList(@Query("page") page: Int): PlayerResponse

}