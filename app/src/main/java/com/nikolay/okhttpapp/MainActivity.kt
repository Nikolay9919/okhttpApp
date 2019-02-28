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


    private var recentList: ArrayList<String> = arrayListOf()
    private val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("CommitTransaction")
    override fun onResume() {
        super.onResume()


            button_run.setOnClickListener {
                if (edit_text_url.text.isEmpty()) {
                    button_run.isClickable = false
                } else {
                val bundle = Bundle()
                recentList.add(edit_text_url.text.toString())
                val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, recentList)
                edit_text_url.setAdapter(adapter)
                bundle.putString("url", edit_text_url.text.toString())
                val fragment = ListFragment()
                fragment.arguments = bundle
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
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
