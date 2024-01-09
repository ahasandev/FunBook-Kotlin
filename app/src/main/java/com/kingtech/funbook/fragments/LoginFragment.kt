package com.kingtech.funbook.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kingtech.funbook.R
import com.kingtech.funbook.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            var email = binding.editTextEmail.text.toString().trim()
            var password = binding.editTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()){
                userlogin(email, password)
            }else{
                binding.editTextEmail.setError("Email")
                binding.editTextPassword.setError("Password")
                Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
            }


        }


        binding.dontHaveAnAccount.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun userlogin(email: String, password: String) {
    mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener { result ->
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }.addOnFailureListener {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(it.message)
        alertDialog.create().show()
    }
    }

}