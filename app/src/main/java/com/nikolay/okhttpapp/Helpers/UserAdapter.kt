package com.nikolay.okhttpapp.Helpers

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.nikolay.okhttpapp.Models.User
import com.nikolay.okhttpapp.R
import kotlinx.android.synthetic.main.item_user.view.*


class UserAdapter(private var userList: Array<User>, private val listener: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun getItemCount(): Int {
        Log.d("size", userList.size.toString())
        return if (userList.size > 50) {
            50
        } else {
            userList.size
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(rootView)
    }

    private fun setAnimation(viewToAnimate: View) {
        if (viewToAnimate.animation == null) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.animation = animation
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], listener)
        setAnimation(holder.itemView)
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