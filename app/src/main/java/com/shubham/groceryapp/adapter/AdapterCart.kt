package com.shubham.groceryapp.adapter

import android.content.Context
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shubham.groceryapp.R
import com.shubham.groceryapp.activities.ShoppingCart
import com.shubham.groceryapp.app.Config
import com.shubham.groceryapp.database.DBHelper
import com.shubham.groceryapp.models.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_view_recycler_cart.view.*

class AdapterCart(private var context: Context, private var list: ArrayList<Products>) :
RecyclerView.Adapter<AdapterCart.ViewHolder>() {

    val parentActivity = context as ShoppingCart

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(
            R.layout.row_view_recycler_cart,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var products: Products = list[position]
        holder.bind(products, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setData(l: ArrayList<Products>) {
        list = l
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(products: Products, position: Int) {

            var dbHelper = DBHelper(context)

            var quantity = products.quantity
            Log.d("quantity", quantity.toString())

            //SET VIEWS WITH DATA
            itemView.tv_cart_name.text = products.productName
            itemView.tv_cart_price.text = "$${products.price}"
            itemView.tv_cart_quantity.text = quantity.toString()

            //STRIKE THROUGH FOR MRP
            val str = "$${products.mrp}"
            val spannableString = SpannableString(str)
            spannableString.setSpan(StrikethroughSpan(), 0, str.length, 0)
            itemView.tv_cart_mrp.text = spannableString

            var imgURL = Config.IMAGE_URL + products.img

            Picasso.get().load(imgURL).error(R.drawable.ic_baseline_broken_image_24)
                .into(itemView.img_view_product)

            fun deleteItem() {
                dbHelper.deleteProduct(products._id)
                list.removeAt(position)
                notifyItemRemoved(position)
                setData(list)
            }

            //ONCLICK FUNCTIONS
            itemView.button_delete_cart.setOnClickListener {
                deleteItem()

            }

            itemView.button_quantity_add_cart.setOnClickListener {
                // var productsDB = dbHelper.getRecord(products._id) // RETRIEVES DATA FROM DB
                dbHelper.updateQuantityProduct(products, true)
                notifyItemChanged(list.indexOf(products))
                products.quantity+=1
                parentActivity.calcAndSetTotals()
//                quantity++
//                itemView.tv_cart_quantity.text = quantity.toString()

            }


            itemView.button_quantity_subtract_cart.setOnClickListener {
                if (quantity > 1) {
                    //var productsDB = dbHelper.getRecord(products._id) // RETRIEVES DATA FROM DB
                    dbHelper.updateQuantityProduct(products, false)
                    notifyItemChanged(position)
                    products.quantity -= 1
                    //quantity--
                    //itemView.tv_cart_quantity.text = quantity.toString()
                } else {
                    deleteItem()

                }
                parentActivity.calcAndSetTotals()
            }


        }
    }

}