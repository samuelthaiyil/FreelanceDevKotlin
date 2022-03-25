package com.example.freelanceapp

class Post(fullName: String, posterUID: String, postContent: String, likes: Double, comments: Double, shares: Double, postID: String) {
    var fullName: String
    var posterUID: String
    var postContent: String
    var likes: Double
    var comments: Double
    var shares: Double
    var postID: String

    init {
        this.fullName = fullName
        this.posterUID = posterUID
        this.postContent = postContent
        this.likes = likes
        this.comments = comments
        this.shares = shares
        this.postID = postID
    }
}