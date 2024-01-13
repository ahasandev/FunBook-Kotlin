package com.kingtech.funbook.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.kingtech.funbook.databinding.FragmentRegistrationBinding
import com.kingtech.funbook.model.User


class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registrationBtn.setOnClickListener {
            val name = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextNewPassword.text.toString().trim()

            userRegistration(name, email, password)
        }

        binding.alreadyHaveAnAccount.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun userRegistration(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val user = User(
                    name = name,
                    email =email,
                    password = password,
                    profileImage = "",
                    userId = result.user!!.uid )
                mRef.child("User").child(user.userId).setValue(user).addOnSuccessListener {
                    mAuth.signOut()
                    findNavController().popBackStack()
                }.addOnFailureListener {
                    val alertDialog = AlertDialog.Builder(requireActivity()).setTitle("Error")
                        .setMessage(it.message)

                    alertDialog.create().show()

                }



            }.addOnFailureListener { error ->
                val alertDialog = AlertDialog.Builder(requireActivity()).setTitle("Error")
                    .setMessage(error.message)

                alertDialog.create().show()


            }

    }


}