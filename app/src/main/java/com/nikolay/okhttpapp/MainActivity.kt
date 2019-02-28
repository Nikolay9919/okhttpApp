package com.nikolay.okhttpapp

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.GsonBuilder
import com.nikolay.okhttpapp.Models.User
import kotlinx.android.synthetic.main.activity_main.*

import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    private val userList = ArrayList<User>()

    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipe_container)
        swipeRefreshLayout.setOnRefreshListener(this)

        swipeRefreshLayout.post {
            run {
                swipeRefreshLayout.isRefreshing = true
                get()
            }
        }

        userList.addAll(get())

        Log.d("tag", get().toString())
    }

    override fun onRefresh() {
        userList.clear()
        userList.addAll(get())

    }

    fun get(): Array<User> {
        val url =
            "https://api.github.com/repos/apple/swift/events?access_token=bdc20dc0f4ded955e03f90a30e6bd812fbe0a1d0"
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
                runOnUiThread {
                    val adapter = UserAdapter(userList)
                    {
                        Log.d("user", it.toString())
                    }
                    recycler_view.layoutManager = LinearLayoutManager(applicationContext)
                    recycler_view.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                swipeRefreshLayout.isRefreshing = false

                Log.d("userList", userList.toString())
            }
        })

        return userList
    }
}
