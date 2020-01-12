package com.example.localguide

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country (@PrimaryKey val name: String,
                    val description: String,
                    val ethnicity: String,
                    val etiquette: String,
                    val language: String,
                    val religion: String){
}