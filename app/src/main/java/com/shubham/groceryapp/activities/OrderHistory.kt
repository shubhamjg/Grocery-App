package com.shubham.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.SessionManager
import com.shubham.groceryapp.adapter.AdapterOrderHistory
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.OrderHistoryData
import com.shubham.groceryapp.models.OrderHistoryResult
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.app_bar.*

class OrderHistory : AppCompatActivity() {

    var mList: ArrayList<OrderHistoryData> = ArrayList()
    var adapterOrderHistory: AdapterOrderHistory? = null

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        sessionManager = SessionManager(this)

        init()
    }

    private fun init() {
        setupToolBar()

        getData()

        adapterOrderHistory = AdapterOrderHistory(this, mList)
        recycler_view_order_history.layoutManager = LinearLayoutManager(this)
        recycler_view_order_history.adapter = adapterOrderHistory

    }

    private fun getData() {
        var url = "${Endpoints.getOrder()}/${sessionManager.getUserId()}"

        var request = StringRequest(Request.Method.GET, url, {

            var orderResult = Gson().fromJson(it, OrderHistoryResult::class.java)

            mList.addAll(orderResult.data)
            adapterOrderHistory?.setData(mList)
            progress_bar_order_history.visibility = View.GONE

        }, {
            Toast.makeText(applicationContext, "Error Loading Data", Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(request)
    }

    private fun setupToolBar(){
        var toolbar = my_toolbar
        toolbar.title = "Order History"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}

