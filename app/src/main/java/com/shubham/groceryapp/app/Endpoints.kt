package com.shubham.groceryapp.app

class Endpoints {
    companion object{
        const val URL_REGISTER = "auth/register"
        const val URL_LOGIN = "auth/login"
        const val URL_CATEGORY = "category"
        const val URL_SUB_CATEGORY = "subcategory"
        const val URL_PRODUCT_BY_SUB = "products/sub/"
        const val URL_ADDRESS = "address"
        const val URL_ORDER = "orders"

        fun getRegisterUrl(): String{
            return "${Config.BASE_URL + URL_REGISTER}"
        }

        fun getLoginUrl():String{
            return "${Config.BASE_URL + URL_LOGIN }"
        }

        fun getCategoryUrl():String{
            return "${Config.BASE_URL + URL_CATEGORY}"
        }

        fun getSubCategoryByCatId(catId: Int): String{
            return "${Config.BASE_URL + URL_SUB_CATEGORY}/{$catId}"
        }

        fun getProductBySubId(subId: Int): String{
            return "${Config.BASE_URL + URL_PRODUCT_BY_SUB}$subId"
        }

        fun postAddress(): String{
            return "${Config.BASE_URL + URL_ADDRESS}"
        }

        fun getOrder(): String{
            return Config.BASE_URL + URL_ORDER
        }

    }
}