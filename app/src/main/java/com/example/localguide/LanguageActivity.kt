package com.example.localguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localguide.Model.Language

class LanguageActivity : AppCompatActivity() {

    val arrayLanguage: ArrayList<Language> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        val recycleView = findViewById<RecyclerView>(R.id.recycleViewLanguage)
        val adapter = LanguageListAdapter(this, arrayLanguage)

        arrayLanguage.add(Language("English (United Kingdom)"))
        arrayLanguage.add(Language("简体中文"))

        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this)

        adapter.setLanguages(arrayLanguage)
    }
}
