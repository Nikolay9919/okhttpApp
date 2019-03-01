package com.nikolay.okhttpapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.nikolay.okhttpapp.Models.ActorDetails
import kotlinx.android.synthetic.main.fragment_user.*
import okhttp3.*
import java.io.IOException

class ActorDetailsFragment : Fragment() {
    var actorId = ""
    private var client = OkHttpClient()
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        actorId = arguments!!.getString("actorId")
        Log.d("actorId", actorId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val actor: ActorDetails = (fetchJson(actorId))


        return inflater.inflate(R.layout.fragment_user, container, false)
    }


    private fun fetchJson(userLogin: String): ActorDetails {

        val url = "https://api.github.com/users/$userLogin"
        Log.d("url", url)
        client = OkHttpClient()
        var actorDetails = ActorDetails()
        if (URLUtil.isValidUrl(url)) {

            val json = GsonBuilder().create()
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Failure Connection to ", url)
                    activity!!.onBackPressed()
                }

                @SuppressLint("ShowToast")
                override fun onResponse(call: Call, response: Response) {

                    val body = response.body()?.string()
                    try {
                        activity!!.runOnUiThread {
                            actorDetails = json.fromJson(body, ActorDetails::class.java)
                            Log.d("response", actorDetails.toString())
                            Glide.with(activity!!.applicationContext)
                                .load(Uri.parse(actorDetails.avatar_url))
                                .into(avatar_url)
                            login.text = "Login     :" + actorDetails.login
                            email.text = "Email     :" + actorDetails.email
                            public_repos.text = "Public Repos   :" + actorDetails.public_repos
                            public_gists.text = "Public Gists  :" + actorDetails.public_gists
                            followers.text = "Followers     :" + actorDetails.followers.toString()
                            followings.text = "Following    :" + actorDetails.following.toString()
                            created_at.text = "Created At   :" + actorDetails.created_at
                            updated_at.text = "Updated At   :" + actorDetails.updated_at

                        }

                    } catch (exception: JsonSyntaxException) {
                        activity!!.runOnUiThread {
                            Toast.makeText(activity!!.applicationContext, exception.toString(), Toast.LENGTH_SHORT)
                            activity!!.onBackPressed()
                        }

                    }


                }
            })
        }
        return actorDetails
    }
}
