package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dudnyk.projectwithmaterialdesign.Data.Product
import com.dudnyk.projectwithmaterialdesign.Preferences.UserPreferences
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentProductDetailBinding
import com.google.android.material.appbar.MaterialToolbar

class ProductDetailFragment : Fragment() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var productBinding: FragmentProductDetailBinding
    private lateinit var product: Product
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initObjects(inflater, container)

        setUpToolBar(product.name)
        setUpCartButton()

        return productBinding.root
    }

    private fun initObjects(inflater: LayoutInflater, container: ViewGroup?) {
        productBinding = FragmentProductDetailBinding.inflate(inflater, container, false)
        product = arguments?.getParcelable(PRODUCT)!!
        userPreferences = UserPreferences(productBinding.root.context)
    }

    private fun setUpCartButton() {
        productBinding.addToCartBtn.setOnClickListener {
            userPreferences.getCurrentShoppingCart().addProduct(product)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(product)
    }

    companion object {
        const val TAG = "FRAGMENT_PRODUCT_DETAIL"
        const val PRODUCT: String = "PRODUCT"

        fun newInstance(product: Product) =
            ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PRODUCT, product)
                }
            }
    }

    private fun setUpToolBar(title: String) {
        toolbar = activity?.findViewById(R.id.my_toolbar)!!
        toolbar.title = title
    }

    private fun initView(product: Product) {
        productBinding.productDetailImage.setImageResource(product.resId)
        productBinding.productDetailName.text = product.name
        productBinding.productDetailPrice.text = product.getFormattedPrice()
        productBinding.productDetailDescription.text = product.description
    }
}