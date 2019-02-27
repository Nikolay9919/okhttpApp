package com.nikolay.okhttpapp

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val userList: List<User>, private val listener: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun getItemCount(): Int {
        Log.d("size", userList.size.toString())
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(rootView)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], listener)
    }


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, listener: (User) -> Unit) = with(itemView) {

            Log.d("userAdapter", user.toString())
            user_login.text = user.actor.login
            user_type.text = user.repo.name + " , " + user.type

            Glide.with(context)
                .load(Uri.parse(user.actor.avatar_url))
                .into(user_avatar)

            setOnClickListener { listener(user) }
        }
    }
}