package com.shubham.groceryapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shubham.groceryapp.R
import com.shubham.groceryapp.activities.SubCategory
import com.shubham.groceryapp.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_adapter_category.view.*

class AdapterCategory(var mContext: Context, var mList: ArrayList<Category>): RecyclerView.Adapter<AdapterCategory.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_adapter_category,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category: Category = mList[position]
        holder.bind(category)
    }



    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(mList: ArrayList<Category>) {

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
         fun bind(category: Category){
             itemView.text_view_name.text = category.catName
             Picasso
                 .get()
                 .load("https://rjtmobile.com/grocery/images/" + category.catImage)
                 .into(itemView.image_view)

             itemView.setOnClickListener{
                 mContext.startActivity(Intent(mContext, SubCategory::class.java).apply { putExtra(Category.KEY_CATEGORY,category) })
             }
         }
    }
}