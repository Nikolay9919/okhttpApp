package com.nikolay.okhttpapp


data class Repo(var id: Long, var name: String) {
    override fun toString(): String {
        return "Repo(id=$id, name='$name')"
    }
}