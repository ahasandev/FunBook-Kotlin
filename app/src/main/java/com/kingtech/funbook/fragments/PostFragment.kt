package com.kingtech.funbook.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kingtech.funbook.adapter.PostAdapter
import com.kingtech.funbook.databinding.AddPostDialogBinding
import com.kingtech.funbook.databinding.FragmentPostBinding
import com.kingtech.funbook.model.Post
import com.kingtech.funbook.model.PostWithUser
import com.kingtech.funbook.model.User

class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate) {
    val userList: MutableList<User> = mutableListOf()
    val postList: MutableList<PostWithUser> = mutableListOf()
    lateinit var adapter: PostAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllUser()
        setData()

        binding.addPostBtn.setOnClickListener {

            addPostBottomSheetDialog()

        }


    }



    private fun setData() {
        mRef.child("Post").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (sn in snapshot.children) {
                    val post = sn.getValue(Post::class.java)
                    setUserWithPost(post!!)


                }

                adapter = PostAdapter(postList)

                val manager = LinearLayoutManager(requireContext())
//                 manager.stackFromEnd= true


                binding.rcvpost.layoutManager = manager
                binding.rcvpost.adapter = adapter


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    private fun setUserWithPost(post: Post) {
        mRef.child("User").child(post.authorId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val user: User = snapshot.getValue(User::class.java)!!
                val postWithUser = PostWithUser(post, user)
                postList.add(0, postWithUser)


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
        private fun addPostBottomSheetDialog() {

            val postDialog = BottomSheetDialog(requireContext())
            postDialog.setCancelable(true)
            postDialog.setCanceledOnTouchOutside(true)
            val postBinding: AddPostDialogBinding = AddPostDialogBinding.inflate(layoutInflater)
            postDialog.setContentView(postBinding.root)







            val post = Post(mAuth.uid.toString(), "", "", "")




            postBinding.apply {


                btnUploadPost.setOnClickListener {

                    val content = etPost.text.toString().trim()
                    Toast.makeText(requireContext(), "${content}", Toast.LENGTH_LONG).show()


                    val postId = mRef.push().key
                    post.postContent = content
                    post.postID = postId!!



                    mRef.child("Post").child(postId).setValue(post).addOnSuccessListener {
                        setData()
                        Toast.makeText(requireContext(), "Post Uploaded", Toast.LENGTH_LONG).show()
                        postDialog.dismiss()


                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_LONG).show()
                        setData()
                        postDialog.dismiss()
                    }


                }


            }




            postDialog.show()


        }
    private fun getAllUser() {
        mRef.child("User").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (sn in snapshot.children) {

                    val user: User = sn.getValue(User::class.java)!!
                    userList.add(user)
                }

                Log.d("TAG", "size: ${userList.size} ")


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


}