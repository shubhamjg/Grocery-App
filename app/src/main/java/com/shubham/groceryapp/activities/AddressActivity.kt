package com.shubham.groceryapp.activities

import android.content.Intent
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
import com.shubham.groceryapp.adapter.AdapterAddress
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.Address
import com.shubham.groceryapp.models.AddressResponse
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*

class AddressActivity : AppCompatActivity() {
    var mList: ArrayList<Address> = ArrayList()
    var adapterAddress: AdapterAddress? = null

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        sessionManager = SessionManager(this)

        init()

        var toolbar = my_toolbar
        toolbar.title = "Address"
        setSupportActionBar(toolbar)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    private fun init() {
        
        getData()

        adapterAddress = AdapterAddress(this, mList)
        recycler_view_address.layoutManager = LinearLayoutManager(this)
        recycler_view_address.adapter = adapterAddress

        button_new_address.setOnClickListener{
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
    }

    private fun getData() {
        val url = "${Endpoints.postAddress()}/${sessionManager.getUserId()}"
        var request = StringRequest(Request.Method.GET, url, {

            var addressResult = Gson().fromJson(it, AddressResponse::class.java)

            mList.addAll(addressResult.data)
            adapterAddress?.setData(mList)
            progress_bar_address.visibility = View.GONE

        }, {
            Toast.makeText(applicationContext, "Error Loading Data", Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(request)
    }

    override fun onRestart() {
        super.onRestart()
        mList.clear()
        getData()
    }
}