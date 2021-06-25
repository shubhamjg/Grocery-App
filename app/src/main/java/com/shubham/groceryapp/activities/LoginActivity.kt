package com.shubham.groceryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.CategoryResponse
import com.shubham.groceryapp.models.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init(){
        login_button.setOnClickListener {
            var email = login_email.text.toString()
            var password = password_login.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("password",password)

            var sharedPreferences = getSharedPreferences("my_file",Context.MODE_PRIVATE)
            var loginResponse = sharedPreferences.getString("info",null)


            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getLoginUrl(),
                jsonObject,
                Response.Listener {
                    var gson = Gson()
                    var loginResponse = gson.fromJson(it.toString(),LoginResponse::class.java)
                    var editor = sharedPreferences.edit()
                    editor.putString("info",loginResponse.toString())
                    editor.commit()
                    Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
                    var intent = Intent(applicationContext, CategoryActivity::class.java)
                    startActivity(intent)
                     Log.d("xyz", it.toString())
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            )

            requestQueue.add(request)
        }
    }
}

