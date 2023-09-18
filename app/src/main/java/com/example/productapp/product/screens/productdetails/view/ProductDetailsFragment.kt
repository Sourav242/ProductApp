package com.example.productapp.product.screens.productdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.productapp.ProductApplication
import com.example.productapp.product.repository.ProductRepository
import com.example.productapp.product.viewmodel.SharedProductViewModel

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */

class ProductDetailsFragment : Fragment() {

    private val viewModel: SharedProductViewModel by activityViewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

        viewModel.getProduct(args.productId)

        setContent {
            ProductDetailsBody(viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPreview() {
    ProductDetailsBody(
        SharedProductViewModel(
            ProductApplication(),
            ProductRepository()
        )
    )
}