package com.shubham.groceryapp.adapter

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shubham.groceryapp.R
import com.shubham.groceryapp.activities.ProductDetailActivity
import com.shubham.groceryapp.models.ProductData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_view_recycler_product.view.*

class AdapterProducts(private var mContext: Context, private var list: ArrayList<ProductData>) :
    RecyclerView.Adapter<AdapterProducts.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_view_recycler_product, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var products: ProductData = list[position]
        holder.bind(products)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData() {
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(products: ProductData) {
            //SET DATA INTO VIEWS
            itemView.tv_product_name.text = products.productName
            itemView.tv_product_price.text = "$${products.price}"
            var imgLink = "https://rjtmobile.com/grocery/images/${products.image}"

            Picasso.get().load(imgLink).error(R.drawable.ic_baseline_broken_image_24)
                .into(itemView.img_view_product)

            //STRIKETHROUGH FOR MRP
            val str = "$${products.mrp}"
            val spannableString = SpannableString(str)
            spannableString.setSpan(StrikethroughSpan(), 0, str.length,0)
            itemView.tv_product_mrp.text = spannableString

            //ONCLICK
            itemView.setOnClickListener {
                mContext.startActivity(Intent(mContext, ProductDetailActivity::class.java).apply {
                    putExtra(ProductData.KEY_PRODUCT, products)
//
                })
            }
        }
    }
}