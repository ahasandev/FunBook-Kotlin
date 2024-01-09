package com.kingtech.funbook.model

import com.kingtech.funbook.model.Post
import com.kingtech.funbook.model.User

data class PostWithUser(
    val post: Post,
    val user: User
)