package com.example.localguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localguide.Model.Function

class SettingActivity : AppCompatActivity() {

    val arrayFunction: ArrayList<Function> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val recycleView = findViewById<RecyclerView>(R.id.recycleView)
        val adapter = FunctionListAdapter(this, arrayFunction)

        arrayFunction.add(Function("Change Password"))
        arrayFunction.add(Function("Language"))

        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this)

        adapter.setFunctions(arrayFunction)

    }
}
