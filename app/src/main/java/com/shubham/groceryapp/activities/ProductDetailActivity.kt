package com.shubham.groceryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.shubham.groceryapp.R
import com.shubham.groceryapp.app.Config
import com.shubham.groceryapp.database.DBHelper
import com.shubham.groceryapp.models.ProductData
import com.shubham.groceryapp.models.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import kotlinx.android.synthetic.main.row_adapter_category.*

class ProductDetailActivity : AppCompatActivity() {

    //DECLARE GLOBAL VARIABLES

    private lateinit var dbHelper: DBHelper

    var textViewCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        var products = intent.getSerializableExtra(ProductData.KEY_PRODUCT) as ProductData

        //INITIALIZE DATABASE HELPER
        dbHelper = DBHelper(this)

        init(products)

        var toolbar = my_toolbar
        toolbar.title = "Product Detail"
        setSupportActionBar(toolbar)

    }

    override fun onRestart() {
        super.onRestart()
        updateCartCount()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        var item = menu.findItem(R.id.action_cart_custom)
        MenuItemCompat.setActionView(item, R.layout.layout_menu_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewCartCount = view.text_view_cart_count
        updateCartCount()

        view.setOnClickListener {
            startActivity(Intent(this, ShoppingCart::class.java))
            finish()
        }
        return true
    }

    private fun updateCartCount() {
        var count = dbHelper.getQuantityTotal()
        if(count == 0){
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_cart -> {
//                startActivity(Intent(this, ShoppingCartActivity::class.java))
//                finish()
//            }
//        }
        return true
    }

    private fun init(products: ProductData) {

        //CHECK IF ITEM ALREADY EXISTS
        if (dbHelper.checkIfRecordExists(products._id)) {
            button_product_add.visibility = View.GONE

            var productsDB = dbHelper.getRecord(products._id) // RETRIEVES DATA FROM DB
            tv_product_detail_quantity.text = productsDB.quantity.toString()

        } else {
            layout_quantity.visibility = View.GONE
            tv_in_cart.visibility = View.GONE
        }

        //SET DATA INTO VIEWS
        tv_product_detail_name.text = products.productName
        tv_product_detail_price.text = "$${products.price}"
        tv_product_detail_desc.text = products.description

        var imgLink = Config.IMAGE_URL + products.image
        Picasso.get().load(imgLink).error(R.drawable.ic_baseline_broken_image_24)
            .into(img_view_product_detail)

//        var record: Products = dbHelper.getRecord(products._id)
//        Log.d("RECORDCHECK", record.productName)


        // ADD TO CART BUTTON
        button_product_add.setOnClickListener {
            var productsDB = Products(
                products._id,
                products.productName,
                products.image,
                products.mrp,
                products.price,
                1,
            )
            tv_product_detail_quantity.text = "1"
            dbHelper.addProduct(productsDB)
            button_product_add.visibility = View.GONE
            layout_quantity.visibility = View.VISIBLE
            tv_in_cart.visibility = View.VISIBLE
            updateCartCount()
        }

        //LINEAR LAYOUT TO ADD OR REMOVE ITEMS
        button_quantity_add.setOnClickListener {
            var productsDB = dbHelper.getRecord(products._id) // RETRIEVES DATA FROM DB
            dbHelper.updateQuantityProduct(productsDB, true)
            tv_product_detail_quantity.text =
                "${tv_product_detail_quantity.text.toString().toInt() + 1}"
            updateCartCount()
        }

        button_quantity_subtract.setOnClickListener {
            var quantity = tv_product_detail_quantity.text.toString().toInt()
            if (quantity > 1) {
                var productsDB = dbHelper.getRecord(products._id) // RETRIEVES DATA FROM DB
                dbHelper.updateQuantityProduct(productsDB, false)
                tv_product_detail_quantity.text =
                    "${tv_product_detail_quantity.text.toString().toInt() - 1}"

            } else {
                dbHelper.deleteProduct(products._id)
                layout_quantity.visibility = View.GONE
                tv_in_cart.visibility = View.GONE
                button_product_add.visibility = View.VISIBLE
            }
            updateCartCount()
        }

    }
}