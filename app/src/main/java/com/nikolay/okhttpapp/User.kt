package com.nikolay.okhttpapp

data class User( var id: Long, var type: String, var actor: Actor, var repo: Repo) {
    override fun toString(): String {
        return "User(id=$id, type='$type', actor=$actor, repo=$repo)"
    }
}