package com.nikolay.okhttpapp.Models

data class ActorDetails(
    val login: String,
    val avatar_url: String,
    val email: String?,
    val public_repos: String,
    val public_gists: String,
    val followers: Int,
    val following: Int,
    val created_at: String,
    val updated_at: String
) {
    constructor() : this("", "", "", "", "", -1, -1, "", "")

    override fun toString(): String {
        return "ActorDetails(login='$login', avatarUrl='$avatar_url', email=$email, publicRepos='$public_repos', publicGists='$public_gists', followers=$followers, following=$following, createdAt='$created_at', updatedAt='$updated_at')"
    }

}