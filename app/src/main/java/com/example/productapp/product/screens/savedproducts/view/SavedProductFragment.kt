package com.example.productapp.product.screens.savedproducts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.productapp.R
import com.example.productapp.product.screens.producthome.view.ProductHomeBody
import com.example.productapp.product.viewmodel.SharedProductViewModel

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */

class SavedProductFragment : Fragment() {

    private val viewModel: SharedProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

        viewModel.getSavedProducts()

        setContent {
            ProductHomeBody(viewModel = viewModel, false, onItemClick = { id ->
                val bundle = Bundle()
                bundle.putInt("productId", id)
                findNavController().navigate(R.id.productDetailsFragment, bundle)
                /*findNavController().navigate(
                    ProductHomeFragmentDirections.actionProductHomeFragmentToProductDetailsFragment(id)
                )*/
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPreview() {
    //ProductHomeBody()
}