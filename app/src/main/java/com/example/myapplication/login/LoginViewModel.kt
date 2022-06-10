package com.example.myapplication.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.api.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


sealed class LoginViewModelState{
    class Success () : LoginViewModelState()
    data class Failure (val errorMessage : String ) : LoginViewModelState()
}

class LoginViewModel : ViewModel(){
    val TAG : String = "LOGIN_ACTIVITY_VM"

    val stateLiveData = MutableLiveData<LoginViewModelState>()

    private val api : LoginAPI


    init {
        api = ServiceBuilder.buildService(LoginAPI::class.java)
    }



    fun login(username : String , password : String) {
            val call = api.getLoginToken(APIUserInfo(username, password))
            call.enqueue(object : Callback<APIToken> {
                override fun onResponse(
                    call: Call<APIToken>,
                    response: Response<APIToken>
                ) {
                    val token = response.body()!!
                    globalApiKey = token.token
                    stateLiveData.value = LoginViewModelState.Success()
                }

                override fun onFailure(call: Call<APIToken>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                    stateLiveData.postValue(LoginViewModelState.Failure("Je peux pas can't believe"))
                    Log.i(TAG,"Je peux pas can't believe")
                }

            })

        }



}