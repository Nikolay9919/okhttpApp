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
        fetchJson(actorId)
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
                            login.text = getString(R.string.loginDetails) + actorDetails.login
                            email.text = getString(R.string.emailDetails) + actorDetails.email
                            public_repos.text = getString(R.string.publicReposDetails) + actorDetails.public_repos
                            public_gists.text = getString(R.string.PublicGistsDetails) + actorDetails.public_gists
                            followers.text = getString(R.string.FollowersDetails) + actorDetails.followers.toString()
                            followings.text = getString(R.string.FollowingDetails) + actorDetails.following.toString()
                            created_at.text = getString(R.string.CreatedAtDetails) + actorDetails.created_at
                            updated_at.text = getString(R.string.UpdatedAtDetails) + actorDetails.updated_at

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
