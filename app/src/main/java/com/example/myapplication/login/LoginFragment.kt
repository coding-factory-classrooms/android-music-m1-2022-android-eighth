package com.example.myapplication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding

    val TAG = "LOGIN_ACTIVITY"
class LoginFragment : Fragment() {

    val viewModel : LoginViewModel by viewModels()

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val duration = Toast.LENGTH_SHORT
        val username = binding.usernameEditText.text.toString()
        val password = binding.editTextPassword.text.toString()


        binding.loginButton.setOnClickListener {viewModel.login(username,password)}
        Log.i(TAG , " username = $username , password = $password")


        viewModel.stateLiveData.observe(viewLifecycleOwner){ state ->
            when(state){
                is LoginViewModelState.Failure -> {
                    val toast = Toast.makeText(context, state.errorMessage , duration)
                    toast.show()
                }
                is LoginViewModelState.Success ->
                {
                    val toast = Toast.makeText(context , "Login OK ! Redirection vers les chansons de Trotro" , duration)
                    toast.show()
                   val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }
}