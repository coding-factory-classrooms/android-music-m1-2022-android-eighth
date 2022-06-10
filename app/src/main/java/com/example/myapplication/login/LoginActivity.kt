package com.example.myapplication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding

    val TAG = "LOGIN_ACTIVITY"
class LoginActivity : AppCompatActivity() {

    val viewModel : LoginViewModel by viewModels()

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val duration = Toast.LENGTH_SHORT
        val username = binding.usernameEditText.text.toString()
        val password = binding.editTextPassword.text.toString()


        binding.loginButton.setOnClickListener {viewModel.login(username,password)}
        Log.i(TAG , " username = $username , password = $password")


        viewModel.stateLiveData.observe(this){ state ->
            when(state){
                is LoginViewModelState.Failure -> {
                    Log.i(TAG,"coucou")
                    val toast = Toast.makeText(this, state.errorMessage , duration)
                    toast.show()
                }
                is LoginViewModelState.Success ->
                {
                    val toast = Toast.makeText(this , "Login OK ! Redirection vers la liste des films..." , duration)
                    toast.show()
//                    val intent = Intent(this, MovieListActivity::class.java)
//                    startActivity(intent)
                }
            }
        }
    }
}