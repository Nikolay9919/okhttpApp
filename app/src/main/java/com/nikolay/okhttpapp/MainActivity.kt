package com.nikolay.okhttpapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var recentList: ArrayList<String> = ArrayList(3)
    private val manager = supportFragmentManager
    private val urlKey1 = "URL_1"
    private val urlKey2 = "URL_2"
    private val urlKey3 = "URL_3"
    private val urlKeysList: ArrayList<String> = arrayListOf(urlKey1, urlKey2, urlKey3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private val sharedPreferencesHelper = SharedPreferencesHelper()
    @SuppressLint("CommitTransaction")
    override fun onResume() {
        super.onResume()
        if (sharedPreferencesHelper[applicationContext, urlKey1] != null)
            recentList.add(sharedPreferencesHelper[applicationContext, urlKey1]!!)
        if (sharedPreferencesHelper[applicationContext, urlKey2] != null)
            recentList.add(sharedPreferencesHelper[applicationContext, urlKey2]!!)
        if (sharedPreferencesHelper[applicationContext, urlKey3] != null)
            recentList.add(sharedPreferencesHelper[applicationContext, urlKey3]!!)
        Log.d("mainRecentList", recentList.toString())
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, recentList)
        edit_text_url.setAdapter(adapter)


        button_run.setOnClickListener {
            if (edit_text_url.text.isEmpty()) {
                return@setOnClickListener
            } else {
                val bundle = Bundle()
                if (edit_text_url.text.toString() !in recentList)
                    sharedPreferencesHelper.put(
                        applicationContext,
                        urlKeysList[recentList.size],
                        edit_text_url.text.toString()
                    )

                bundle.putString("url", edit_text_url.text.toString())
                val fragment = ListFragment()
                fragment.arguments = bundle
                val transaction = manager.beginTransaction()
                transaction.setCustomAnimations(R.anim.enter_from_righr, R.anim.exit_to_left)
                transaction.add(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()

                button_run.visibility = View.GONE
                edit_text_url.visibility = View.GONE
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
        edit_text_url.visibility = View.VISIBLE
        button_run.visibility = View.VISIBLE
        when {
            sharedPreferencesHelper[applicationContext, urlKey1] != null -> recentList.add(sharedPreferencesHelper[applicationContext, urlKey1]!!)
            sharedPreferencesHelper[applicationContext, urlKey2] != null -> recentList.add(sharedPreferencesHelper[applicationContext, urlKey2]!!)
            sharedPreferencesHelper[applicationContext, urlKey3] != null -> recentList.add(sharedPreferencesHelper[applicationContext, urlKey3]!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        val recentListSaved: ArrayList<String> = recentList
        outState!!.putStringArrayList("recentList", recentListSaved)
        if (recentListSaved.isEmpty()) {
            Log.d("EmptySavedState:", recentListSaved.toString())
        } else {
            Log.d("SavedState:", recentListSaved.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recentList = savedInstanceState?.getStringArrayList("recentList")!!

    }
}
