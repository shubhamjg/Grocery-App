package com.shubham.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shubham.groceryapp.R
import com.shubham.groceryapp.SessionManager
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.Address
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        sessionManager = SessionManager(this)

        init()

        var toolbar = my_toolbar
        toolbar.title = "Add Address"
        setSupportActionBar(toolbar)
    }

    private fun init() {
        var type: String = "Home"

        radio_group_add_address.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.rg_home -> type = rg_home.text.toString()
                R.id.rg_office -> type = rg_office.text.toString()
                R.id.rg_other -> type = rg_other.text.toString()
            }
        }

        button_save_address.setOnClickListener{
            var addressParams = HashMap<String, Any>()

            addressParams[Address.KEY_HOUSE_NO] = et_address_house_number.text.toString()
            addressParams[Address.KEY_STREET_NAME] = et_address_street_name.text.toString()
            addressParams[Address.KEY_PINCODE] = et_address_pincode.text.toString().toInt()
            addressParams[Address.KEY_CITY] = et_address_city.text.toString()
            addressParams[Address.KEY_TYPE] = type
            addressParams[Address.KEY_USER_ID] = sessionManager.getUserId()

            var jsonObjectRegister = JSONObject(addressParams as Map<*, *>)
            var url = Endpoints.postAddress()

            var request = JsonObjectRequest(Request.Method.POST, url, jsonObjectRegister, {
                Toast.makeText(applicationContext, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }, {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            })
            Volley.newRequestQueue(this).add(request)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}