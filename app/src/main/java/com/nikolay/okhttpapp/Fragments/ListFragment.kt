package com.nikolay.okhttpapp.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.nikolay.okhttpapp.Helpers.UserAdapter
import com.nikolay.okhttpapp.Models.User
import com.nikolay.okhttpapp.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class ListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    var url: String = ""

    private var client = OkHttpClient()
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        url = arguments!!.getString("url", url)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_list_of_users, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_container)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.post {
            run {
                swipeRefreshLayout.isRefreshing = true
                jsonToView(url)
            }
        }

        return rootView
    }

    override fun onRefresh() {
        jsonToView(url)
    }

    @SuppressLint("ShowToast")
    fun jsonToView(url: String): Array<User> {

        client = OkHttpClient()
        var userList: Array<User> = arrayOf()
        if (URLUtil.isValidUrl(url)) {

            val json = GsonBuilder().create()
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("Failure Connection to ", url)
                    swipeRefreshLayout.isRefreshing = false
                    activity!!.onBackPressed()
                }

                override fun onResponse(call: Call, response: Response) {

                    val body = response.body()?.string()
                    try {
                        userList = json.fromJson(body, Array<User>::class.java)
                        Log.d("response", userList.toString())
                        activity!!.runOnUiThread {
                            val adapter = UserAdapter(userList)
                            {
                                val bundle = Bundle()


                                bundle.putString("actorId", it.actor.login)
                                val fragment = ActorDetailsFragment()
                                fragment.arguments = bundle
                                val transaction = activity!!.supportFragmentManager.beginTransaction()
                                transaction.setCustomAnimations(
                                    R.anim.enter_from_righr,
                                    R.anim.exit_to_left
                                )
                                transaction.replace(R.id.fragment_container, fragment)
                                transaction.addToBackStack("ActorDetailsFragment")
                                transaction.commit()
                            }
                            recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
                            recyclerView.adapter = adapter
                            adapter.notifyDataSetChanged()
                        }
                        swipeRefreshLayout.isRefreshing = false

                        Log.d("userList", userList.toString())
                    } catch (exception: JsonSyntaxException) {
                        activity!!.runOnUiThread {
                            Toast.makeText(activity!!.applicationContext, exception.toString(), Toast.LENGTH_SHORT)
                        }

                    }


                }
            })
        }
        return userList
    } // fetching json an adding him to recycler view

    override fun onStop() {
        super.onStop()
        activity!!.button_run.visibility = View.VISIBLE
    }

}
