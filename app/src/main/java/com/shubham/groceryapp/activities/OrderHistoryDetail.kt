package com.shubham.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.SessionManager
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.OrderHistoryData
import com.shubham.groceryapp.models.OrderHistoryResult
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.row_view_recycler_order_history.*

class OrderHistoryDetail : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    private var mList: ArrayList<OrderHistoryData> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history_detail)

        var position = intent.getIntExtra("position", 0)

        sessionManager = SessionManager(this)

        init(position)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
    private fun init(position: Int) {
        getData(position)

    }

    private fun getData(position: Int) {
        var url = "${Endpoints.getOrder()}/${sessionManager.getUserId()}"

        var request = StringRequest(Request.Method.GET, url, {

            var orderResult = Gson().fromJson(it, OrderHistoryResult::class.java)

            mList.addAll(orderResult.data)
            //progress_bar_order_history.visibility = View.GONE
            tv_order_total.text = "$" + mList[position].orderSummary.totalAmount.toString()
            tv_subtotal_amount.text = "$" + mList[position].orderSummary.ourPrice.toString()
            tv_total_amount.text = "$" + mList[position].orderSummary.totalAmount.toString()
            tv_discount_amount.text = "$" + ((mList[position].orderSummary.ourPrice) - (mList[position].orderSummary.totalAmount)).toString()
            tv_delivery_amount.text = "$" + mList[position].orderSummary.deliveryCharges.toString()

            tv_house_no_street_name.text = "${mList[position].shippingAddress.houseNo}, ${mList[position].shippingAddress.streetName}"
            tv_address_city_pincode.text = "${mList[position].shippingAddress.city}, ${mList[position].shippingAddress.pincode}"

            tv_history_date.text = mList[position].date


        }, {
            Toast.makeText(applicationContext, "Error Loading Data", Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(request)
    }
}