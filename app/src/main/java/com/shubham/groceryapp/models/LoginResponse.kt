package com.shubham.groceryapp.models

data class LoginResponse(
    val token: String,
    val user: Users
)

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)