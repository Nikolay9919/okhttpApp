package com.nikolay.okhttpapp.Models

data class Actor(var id: Long, var login: String, var avatar_url: String) {
    override fun toString(): String {
        return "Actor(id=$id, login='$login', avatar_url='$avatar_url')"
    }
}