package tr.com.sdateapp.logic.rest

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query
import tr.com.sdateapp.logic.data.responses.LoginResponse

interface MainInterface {

    @POST
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LoginResponse>
}