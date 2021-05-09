package com.dudnyk.projectwithmaterialdesign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dudnyk.projectwithmaterialdesign.Data.Product
import com.dudnyk.projectwithmaterialdesign.databinding.FragmentProductDetailBinding
import com.google.android.material.appbar.MaterialToolbar

class ProductDetailFragment : Fragment() {
    lateinit var toolbar: MaterialToolbar
    lateinit var productBinding: FragmentProductDetailBinding
    lateinit var product: Product

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        productBinding = FragmentProductDetailBinding.inflate(inflater, container, false)
        product = arguments?.getParcelable(PRODUCT)!!
        setUpToolBar(product.name)
        return productBinding.root
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