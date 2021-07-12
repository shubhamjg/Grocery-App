package com.shubham.groceryapp.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.adapter.AdapterViewPager
import com.shubham.groceryapp.models.Category
import com.shubham.groceryapp.models.SubCatData
import com.shubham.groceryapp.models.SubCategoryResult
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*

class SubCategory : AppCompatActivity() {

    var category: Category? = null
    var mList: ArrayList<SubCatData> = ArrayList()
    var subCatAdapter: AdapterViewPager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)

        category = intent.getSerializableExtra(Category.KEY_CATEGORY) as Category

        var url = "https://grocery-second-app.herokuapp.com/api/subcategory/${category?.catId}"

        init(url)

        var toolbar = my_toolbar
        toolbar.title = "Category"
        setSupportActionBar(toolbar)


    }


    private fun init(url:String) {
        Log.d("abc", "SUBCATACTIVITY INIT")
        getData(url)
        subCatAdapter = AdapterViewPager(supportFragmentManager)
        view_pager.adapter = subCatAdapter
        tab_layout.setupWithViewPager(view_pager)

    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            //R.id.action_cart -> startActivity(Intent(this, ShoppingCartActivity::class.java))
       }
        return true
    }

    private fun getData(url: String) {

        var pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setMessage("Loading.. Please wait")
        var request = StringRequest(Request.Method.GET, url, {
            var gson = Gson()
            var subCatResult = gson.fromJson(it, SubCategoryResult::class.java)

            Log.d("abc", url)
            mList.addAll(subCatResult.data)

            for(i in 0 until mList.size){
                subCatAdapter?.addFragment(mList[i].subName, mList[i].subId)
            }
            subCatAdapter?.datachanged()
            pd.dismiss()

        }, {
            //Log.d("abc", it.message)
        })

        Volley.newRequestQueue(this).add(request)
        pd.show()
    }
}