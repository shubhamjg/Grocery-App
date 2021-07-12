package com.shubham.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shubham.groceryapp.R
import com.shubham.groceryapp.app.Endpoints
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()

        var toolbar = my_toolbar
        toolbar.title = "Register"
        setSupportActionBar(toolbar)

    }

    private fun init(){
        register_button.setOnClickListener {
            var firstName = register_name.text.toString()
            var email = register_email.text.toString()
            var password = register_password.text.toString()
            var mobile = register_mobile.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("firstName",firstName)
            jsonObject.put("email", email)
            jsonObject.put("password",password)
            jsonObject.put("mobile", mobile)


            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getRegisterUrl(),
                jsonObject,
                Response.Listener {
                   Log.d("xyz", it.toString())
                    Toast.makeText(applicationContext, "Registration Successful", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Log.e("abc", it.message.toString())
                }
            )

            requestQueue.add(request)
        }
    }
}