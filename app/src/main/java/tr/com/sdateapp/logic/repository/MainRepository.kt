package tr.com.sdateapp.logic.repository

import android.util.Log
import tr.com.boer.boerfixturematerialstoreapp.data.network.RetrofitInstance
import tr.com.sdateapp.logic.data.responses.LoginResponse

class MainRepository {
    private val TAG = "MainRepository"
    private val mainInstance = RetrofitInstance.getMainInstance
    suspend fun login(username: String, password: String): LoginResponse{

        return LoginResponse("Başarılı $username $password",true,"")
        /*try {
            val response = mainInstance.login(username,password)
            val body = response.body()
            if (response.isSuccessful && response.body().toString() !=null)
            {
              return body!!
            }
            else
            {
                Log.e(TAG,"Login Error $body")
                return body!!
            }
        }catch (e: Exception){
            Log.e(TAG,e.message.toString())
            return LoginResponse("",false,"")
        }*/
    }
}