package com.nikolay.okhttpapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

     val userList = ArrayList<User>()


    private var client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)


        userList.addAll(get())

        Log.d("tag", get().toString())
    }


    fun get()  : Array<User>{
         val url = "https://api.github.com/repos/apple/swift/events?access_token=bdc20dc0f4ded955e03f90a30e6bd812fbe0a1d0"
        client = OkHttpClient()
        var userList: Array<User> = arrayOf()
        val json = GsonBuilder().create()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Failure Connection to ", url)
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response.body()?.string()
                 userList = json.fromJson(body, Array<User>::class.java)
                runOnUiThread{
                    recycler_view.layoutManager = LinearLayoutManager(applicationContext)

                    recycler_view.adapter = UserAdapter(userList.toList()) {
                        Log.d("user", it.toString())
                    }
                }

                Log.d("userList", userList.toString())
            }
        })

        return userList
    }
}
