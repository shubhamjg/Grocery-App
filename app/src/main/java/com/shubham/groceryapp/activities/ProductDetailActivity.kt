package com.shubham.groceryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.MenuItemCompat
import com.shubham.groceryapp.R
import com.shubham.groceryapp.app.Config
import com.shubham.groceryapp.models.ProductData
import com.shubham.groceryapp.models.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        var products = intent.getSerializableExtra(ProductData.KEY_PRODUCT) as ProductData


        init(products)
    }



    private fun init(products: ProductData) {

        //CHECK IF ITEM ALREADY EXISTS

        tv_product_detail_name.text = products.productName
        tv_product_detail_price.text = "$${products.price}"
        tv_product_detail_desc.text = products.description

        var imgLink = Config.IMAGE_URL + products.image
        Picasso.get().load(imgLink).error(R.drawable.ic_baseline_broken_image_24)
            .into(img_view_product_detail)


    }
}