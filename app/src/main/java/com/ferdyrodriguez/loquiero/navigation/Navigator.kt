package com.ferdyrodriguez.loquiero.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ferdyrodriguez.loquiero.models.ProductItem
import com.ferdyrodriguez.loquiero.usecases.addproduct.AddProductActivity
import com.ferdyrodriguez.loquiero.usecases.login.LoginActivity
import com.ferdyrodriguez.loquiero.usecases.loginOrRegistration.LoginOrRegistrationActivity
import com.ferdyrodriguez.loquiero.usecases.main.MainActivity
import com.ferdyrodriguez.loquiero.usecases.productDetail.ProductDetailActivity
import com.ferdyrodriguez.loquiero.usecases.registration.RegistrationActivity
import com.ferdyrodriguez.loquiero.usecases.search.SearchActivity
import com.ferdyrodriguez.loquiero.usecases.userProducts.UserProductsActivity
import com.ferdyrodriguez.loquiero.usecases.userProfile.UserProfileActivity
import com.ferdyrodriguez.loquiero.utils.Constants

class Navigator constructor(private val context: Context) {

    fun toLoginOrRegistration() {
        val intent = Intent(context, LoginOrRegistrationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun toRegistration() {
        val intent = Intent(context, RegistrationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun toLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun toMain(finish: Boolean) {
        val intent = Intent(context, MainActivity::class.java)
        if(finish) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun toAddProduct(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(Intent(activity, AddProductActivity::class.java), requestCode)
    }

    fun toUserProducts() = context.startActivity(Intent(context, UserProductsActivity::class.java))

    fun toSearch() = context.startActivity(Intent(context, SearchActivity::class.java))

    fun toUserProfile(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(Intent(context, UserProfileActivity::class.java), requestCode)
    }

    fun toProfileRegistration() {

    }

    fun toProductDetail(product: ProductItem) {
        val intent = Intent(context, ProductDetailActivity::class.java)
        intent.putExtra(Constants.PRODUCT, product)
        context.startActivity(intent)
    }
}