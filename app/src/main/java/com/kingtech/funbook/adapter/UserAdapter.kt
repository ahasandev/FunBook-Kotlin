package com.kingtech.funbook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kingtech.funbook.model.User
import com.kingtech.funbook.databinding.UserListBinding

class UserAdapter(private var userlist :List<User>, private val context: Context) : RecyclerView.Adapter<UserAdapter.Userviewholder>() {

    class Userviewholder(var binding: UserListBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Userviewholder {
     val  binding = UserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Userviewholder(binding)
    }

    override fun getItemCount(): Int {
       return userlist.size
    }

    override fun onBindViewHolder(holder: Userviewholder, position: Int) {
       val user = userlist[position]

        holder.binding.userName.text=user.name
        holder.binding.userId.text=user.userId
        holder.binding.userImage.load(user.profileImage)
        holder.itemView.setOnClickListener {
            Toast.makeText(context,"Cliked",Toast.LENGTH_SHORT).show()

        }
    }



}