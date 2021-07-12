package com.shubham.groceryapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.SessionManager
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.UserResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var sessionManager : SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
       // val utility = Utility()
       // utility.saveintosharedpref(this, Person("Shubham", 26))
      //  var person= utility.getfromsharedpref(this,"CUSTOM_OBJECT")
       // Log.d("xyz",person.name)

        sessionManager = SessionManager(this)


    }


    private fun init(){

        button_register.setOnClickListener(this)
        login_button.setOnClickListener {
            var paramsLogin = HashMap<String, String>()

            paramsLogin["email"] = login_email.text.toString()
            paramsLogin["password"] = password_login.text.toString()

            var jsonObjectLogin = JSONObject(paramsLogin as Map<*, *>)

            var request = JsonObjectRequest(Request.Method.POST, Endpoints.getLoginUrl(), jsonObjectLogin,{
                val gson = Gson()
                var loginResult = gson.fromJson(it.toString(), UserResponse::class.java)

                val token = it.getString("token")
                Log.d("login", "token: $token")

                sessionManager.saveUserInput(loginResult.user)
                sessionManager.saveToken(token)


                startActivity(Intent(this, CategoryActivity::class.java))
                finish()
            }, {
                Toast.makeText(applicationContext, "Incorrect email/password!", Toast.LENGTH_SHORT).show()
            })
            Volley.newRequestQueue(this).add(request)
        }
    }

    override fun onClick(v: View?) {
        when(v){
            button_register -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}