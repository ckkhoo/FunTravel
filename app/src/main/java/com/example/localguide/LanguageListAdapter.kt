package com.example.localguide

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localguide.Interface.ItemClickListener
import com.example.localguide.Model.Language

class LanguageListAdapter internal constructor(context: Context, arrayLanguage: ArrayList<Language>) :
    RecyclerView.Adapter<LanguageListAdapter.LanguageViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var language = emptyList<Language>()
    var context: Context = context
    var arrayLanguage: ArrayList<Language> = arrayLanguage

    class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val languageNameTextView: TextView = itemView.findViewById(R.id.textViewLanguageName)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView = inflater.inflate(R.layout.adapter_listview_language, parent, false)
        return LanguageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val current = language[position]

        holder.languageNameTextView.text = current.name
        holder.setOnItemClickListener(object: ItemClickListener {
            override fun onItemClickListener(v: View, position: Int) {

                if(arrayLanguage[position].name.equals("English (United Kingdom)")) {

                    //do somethings
                }
                else if (arrayLanguage[position].name.equals("简体中文")) {

                    //do somethings
                }

            }
        })
    }

    internal fun setLanguages(language: ArrayList<Language>) {
        this.language = language
        notifyDataSetChanged()
    }

    override fun getItemCount() = language.size

}