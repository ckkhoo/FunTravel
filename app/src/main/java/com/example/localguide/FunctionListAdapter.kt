package com.example.localguide

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localguide.Interface.ItemClickListener
import com.example.localguide.Model.Function

class FunctionListAdapter internal constructor(context: Context, arrayFunction: ArrayList<Function>) :
    RecyclerView.Adapter<FunctionListAdapter.FunctionViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var function = emptyList<Function>()
    var context: Context = context
    var arrayFunction: ArrayList<Function> = arrayFunction

    class FunctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val functionNameTextView: TextView = itemView.findViewById(R.id.textViewName)
        var itemClickListener: ItemClickListener? = null

        fun setOnItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListener = itemClickListener
        }

        override fun onClick(v: View?) {
            this.itemClickListener!!.onItemClickListener(v!!, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {
        val itemView = inflater.inflate(R.layout.adapter_listview, parent, false)
        return FunctionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FunctionViewHolder, position: Int) {
        val current = function[position]

        holder.functionNameTextView.text = current.name
        holder.setOnItemClickListener(object: ItemClickListener {
            override fun onItemClickListener(v: View, position: Int) {

               if (arrayFunction[position].name.equals("Change Password")) {
                    var intentChangePassword = Intent(context, ChangePasswordActivity::class.java)
                    context.startActivity(intentChangePassword)
                }
                else if(arrayFunction[position].name.equals("Language")) {
                    var intentLanguage = Intent(context, LanguageActivity::class.java)
                    context.startActivity(intentLanguage)
              }

            }
        })
    }

    internal fun setFunctions(function: ArrayList<Function>) {
        this.function = function
        notifyDataSetChanged()
    }

    override fun getItemCount() = function.size

}