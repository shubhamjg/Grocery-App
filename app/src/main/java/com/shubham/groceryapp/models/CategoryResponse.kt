package com.shubham.groceryapp.models

import java.io.Serializable

data class CategoryResponse(
    val count: Int,
    val data: List<Category>,
    val error: Boolean
)

data class Category(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
): Serializable{
    companion object{
        const val KEY_CAT_ID = "catId"
        const val KEY_CATEGORY = "Category"
    }
}