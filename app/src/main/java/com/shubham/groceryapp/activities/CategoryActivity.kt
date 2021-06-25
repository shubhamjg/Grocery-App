package com.shubham.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.adapter.AdapterCategory
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.Category
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    var mList: ArrayList<Category> = ArrayList()
    var adapterCategory: AdapterCategory? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        init()
    }

    private fun init(){
        getData()
        adapterCategory = AdapterCategory(this,mList)
        recycler_view.adapter = adapterCategory
        recycler_view.layoutManager = GridLayoutManager(this,2 )
    }

    private fun getData() {
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            Endpoints.getCategoryUrl(),null,
            Response.Listener {
                progress_bar.visibility = View.GONE


                var gson = Gson()
                //var categoryResponse = gson.fromJson(it.toString(),CategoryResponse::class.java)
                var jsonArray = it.getJSONArray("data")
                for (i in 0 until jsonArray.length()){
                    val categoryResponse = gson.fromJson<Category>(jsonArray.getJSONObject(i).toString(),Category::class.java)
                    mList.add(categoryResponse)
                }

                /*var jsonArray = JSONArray(it)
                for (i in 0 until jsonArray.length()){
                    var jsonObject = jsonArray.getJSONObject(i)
                    var catImage = jsonObject.getString("catImage")
                    var catName = jsonObject.getString("catName")
                    var catId = jsonObject.getInt("catId")
                    var catDescription = jsonObject.getString("catDescription")
                    var position = jsonObject.getInt("position")
                    var status = jsonObject.getBoolean("status")
                    var _id = jsonObject.getString("_id")
                    var slug = jsonObject.getString("slug")
                    var __v = jsonObject.getInt("__v")
                    var category = Category(catImage = catImage,catDescription = catDescription,position = position,status = status,_id = _id,catId = catId,catName = catName,slug = slug,__v = __v)
                    mList.add(category)
                }*/
                adapterCategory?.setData(mList)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext,it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
    }


}