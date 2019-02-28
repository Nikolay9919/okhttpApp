package com.nikolay.okhttpapp

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
import com.google.gson.GsonBuilder
import com.nikolay.okhttpapp.Models.User
import okhttp3.*
import java.io.IOException

class ListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    private val userList = ArrayList<User>()

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
                get(url)
            }
        }

        return rootView
    }

    override fun onRefresh() {
        get(url)
    }

    fun get(url: String): Array<User> {

        client = OkHttpClient()
        var userList: Array<User> = arrayOf()
        val json = GsonBuilder().create()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Failure Connection to ", url)
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response.body()?.string()
                userList = json.fromJson(body, Array<User>::class.java)
                Log.d("response", userList.toString())
                activity!!.runOnUiThread {
                    val adapter = UserAdapter(userList)
                    {
                        Log.d("user", it.toString())
                    }
                    recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                swipeRefreshLayout.isRefreshing = false

                Log.d("userList", userList.toString())
            }
        })

        return userList
    }
}
