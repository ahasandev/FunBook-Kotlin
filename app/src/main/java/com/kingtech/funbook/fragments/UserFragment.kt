package com.kingtech.funbook.fragments

import android.os.Bundle
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kingtech.funbook.adapter.UserAdapter
import com.kingtech.funbook.databinding.FragmentUserBinding
import com.kingtech.funbook.model.User

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    val userlist:MutableList<User> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.animationView.visibility = View.VISIBLE
        userlist.clear()
        mRef.child("User").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (sn in snapshot.children) {

                    val user: User = sn.getValue(User::class.java)!!
                    userlist.add(user)
                }
                binding.animationView.visibility = View.GONE
                binding.recyclerview.adapter = UserAdapter(userlist,requireContext())


            }
            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

}