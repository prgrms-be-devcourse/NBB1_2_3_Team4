package com.example.Nadeuri.comment.exception

class CommentTaskException(
    override val message: String,
    val code: Int
) : RuntimeException(message)
