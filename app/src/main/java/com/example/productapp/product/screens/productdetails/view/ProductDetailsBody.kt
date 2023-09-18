package com.example.productapp.product.screens.productdetails.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productapp.base.model.NetworkState
import com.example.productapp.product.screens.producthome.view.Greeting
import com.example.productapp.product.screens.producthome.view.ProductDetails
import com.example.productapp.product.screens.producthome.view.ProductError
import com.example.productapp.product.viewmodel.SharedProductViewModel
import com.example.productapp.ui.theme.ProductAppTheme

/**
 * @Author: Sourav PC
 * @Date: 19-09-2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsBody(viewModel: SharedProductViewModel) {

    val snackbarHostState = remember { SnackbarHostState() }

    ProductAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Greeting()
                        }
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) { contentPadding ->

                val productState = viewModel._productStateFlow.collectAsStateWithLifecycle()
                when (productState.value) {
                    is NetworkState.Success -> {
                        productState.value.data?.let {
                            ProductDetails(it, contentPadding)
                        }
                    }

                    is NetworkState.Failure -> {
                        productState.value.error?.let {
                            ProductError(it, snackbarHostState)
                        }
                    }

                    is NetworkState.Loading, is NetworkState.LoadingWithData -> {

                    }

                    else -> {

                    }
                }
            }
        }
    }
}