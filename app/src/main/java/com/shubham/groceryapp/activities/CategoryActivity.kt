package com.shubham.groceryapp.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.SessionManager
import com.shubham.groceryapp.adapter.AdapterCategory
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.database.DBHelper
import com.shubham.groceryapp.models.Category
import com.shubham.groceryapp.models.User
import com.shubham.groceryapp.models.Users
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import kotlinx.android.synthetic.main.nav_header.view.*
import kotlinx.android.synthetic.main.row_adapter_category.*

class CategoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navView: NavigationView


    var mList: ArrayList<Category> = ArrayList()
    var adapterCategory: AdapterCategory? = null

    var textViewCartCount: TextView? = null
    private lateinit var dbHelper: DBHelper
    lateinit var sessionManager: SessionManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        init()
        sessionManager = SessionManager(this)
        dbHelper = DBHelper(this)

        var toolbar = my_toolbar
        toolbar.title = "Category"
        setSupportActionBar(toolbar)

    }

    override fun onRestart() {
        super.onRestart()
        updateCartCount()
    }


    private fun init(){
        setupDrawer()
        getData()
        img_view_home_image.setImageResource(R.drawable.chicken_samosa)
        adapterCategory = AdapterCategory(this,mList)
        recycler_view.adapter = adapterCategory
        recycler_view.layoutManager = GridLayoutManager(this,2 )
    }

    private fun setupDrawer() {
        drawerLayout = drawer_layout
        navView = nav_view
        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, my_toolbar, 0, 0
        )


        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
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
        }
        return true
    }

    private fun updateCartCount(){
        var count = dbHelper.getQuantityTotal()
        if(count == 0){
            textViewCartCount?.visibility = View.GONE
        } else {
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> Toast.makeText(this, "Account selected", Toast.LENGTH_SHORT).show()
            R.id.item_settings -> Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
            R.id.item_orders -> startActivity(Intent(this@CategoryActivity, OrderHistory::class.java))
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun getData() {
        var requestQueue = Volley.newRequestQueue(this)
        val pd = ProgressDialog(this)
        pd.setCancelable(true)
        pd.setMessage("Loading..Please wait")
        var request = JsonObjectRequest(
            Request.Method.GET,
            Endpoints.getCategoryUrl(),null,
            Response.Listener {
                progress_bar.visibility = View.GONE
                var gson = Gson()
                var jsonArray = it.getJSONArray("data")
                for (i in 0 until jsonArray.length()){
                    val categoryResponse = gson.fromJson<Category>(jsonArray.getJSONObject(i).toString(),Category::class.java)
                    mList.add(categoryResponse)
                }
                adapterCategory?.setData(mList)
                pd.dismiss()
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext,it.message, Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(request)
        pd.show()
    }


}