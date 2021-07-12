package com.shubham.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubham.groceryapp.R
import com.shubham.groceryapp.adapter.AdapterCart
import com.shubham.groceryapp.database.DBHelper
import com.shubham.groceryapp.models.Products
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.app_bar.*

class ShoppingCart : AppCompatActivity() {

    var mList: ArrayList<Products> = ArrayList()
    private var adapterCart: AdapterCart? = null
    lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        dbHelper = DBHelper(this)
        init()

        var toolbar = my_toolbar
        toolbar.title = "My Cart"
        setSupportActionBar(toolbar)


    }


    private fun init() {

        mList = dbHelper.getProduct()

        if (mList.isNullOrEmpty()) {
            recycler_view_cart.visibility = View.GONE

        } else {
            tv_no_cart_items.visibility = View.GONE
        }

       calcAndSetTotals()


        adapterCart = AdapterCart(this, mList)
        recycler_view_cart.layoutManager = LinearLayoutManager(this)
        recycler_view_cart.adapter = adapterCart

        button_checkout.setOnClickListener{
            if(mList.isNullOrEmpty())
                Toast.makeText(applicationContext, "Please add item to cart!", Toast.LENGTH_SHORT).show()
            else
                startActivity(Intent(this, AddressActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_person -> startActivity(Intent(applicationContext, MainActivity::class.java))

        }
        return true
    }


   fun calcAndSetTotals() {
        if(mList.isNullOrEmpty()){
            recycler_view_cart.visibility = View.GONE
            tv_no_cart_items.visibility = View.VISIBLE
            tv_subtotal_amount.visibility = View.GONE
            tv_discount_amount.visibility = View.GONE
            tv_total_amount.visibility = View.GONE
            tv_subtotal_text.visibility = View.GONE
            tv_total_text.visibility = View.GONE
            tv_discount_text.visibility = View.GONE
        }else{
            var totals = dbHelper.getOrderSummary()
            var subtotal = totals.mrp
            var discount = totals.discount
            var totalAmount = totals.total


            tv_subtotal_amount.text = "$$subtotal"
            tv_discount_amount.text = "-$$discount"
            tv_total_amount.text = "$$totalAmount"
        }

    }
}