package com.shubham.groceryapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shubham.groceryapp.R
import com.shubham.groceryapp.adapter.AdapterProducts
import com.shubham.groceryapp.app.Endpoints
import com.shubham.groceryapp.models.ProductData
import com.shubham.groceryapp.models.ProductsResult
import kotlinx.android.synthetic.main.fragment_product.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProductFragment : Fragment() {

    private var tabTitle: String? = null
    private var subId: Int? = null

    var mList: ArrayList<ProductData> = ArrayList()
    var adapterProducts: AdapterProducts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabTitle = it.getString(ARG_PARAM1)
            subId = it.getInt(ARG_PARAM2)
        }
        getData(subId!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_product, container, false)

        view.progress_bar_product.visibility = View.GONE
        init(view)

        return view
    }

    private fun init(view: View) {
        adapterProducts = AdapterProducts(requireActivity(), mList)
        view.recycler_view_product.layoutManager = LinearLayoutManager(requireActivity())
        view.recycler_view_product.adapter = adapterProducts
    }

    private fun getData(subId: Int) {
        var url = Endpoints.getProductBySubId(subId)

        var request = StringRequest(Request.Method.GET, url, {

            var productsResult = Gson().fromJson(it, ProductsResult::class.java)

            mList.addAll(productsResult.data)
            adapterProducts?.setData()


        }, {
            //Log.d("abc", it.message)
        })

        Volley.newRequestQueue(requireActivity()).add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(tabTitle: String, subId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, tabTitle)
                    putInt(ARG_PARAM2, subId)

                }
            }
    }
}