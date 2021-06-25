package com.shubham.groceryapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class Utility {

    fun saveintosharedpref(context: Context,person: Person ){
        var sharedPreferences = context.getSharedPreferences("name",0)
        var gson = Gson()
        var jsonString = gson.toJson(person)
        sharedPreferences.edit().putString("CUSTOM_OBJECT",jsonString).commit()

    }

    fun getfromsharedpref(context: Context,key:String): Person{
        var sharedPreferences = context.getSharedPreferences("name",0)
        var gson = Gson()
        var jsonString = sharedPreferences.getString(key,"")
        var person =  gson.fromJson(jsonString, Person::class.java)
        return person
    }
}