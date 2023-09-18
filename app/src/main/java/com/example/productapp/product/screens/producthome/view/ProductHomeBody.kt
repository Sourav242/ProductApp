package com.example.productapp.product.screens.producthome.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productapp.R
import com.example.productapp.base.model.NetworkState
import com.example.productapp.product.model.Product
import com.example.productapp.product.viewmodel.SharedProductViewModel
import com.example.productapp.ui.theme.ProductAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductHomeBody(viewModel: SharedProductViewModel) {
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
                Column(
                    modifier = Modifier.padding(contentPadding)
                ) {
                    SearchBody()

                    val productState = viewModel._productStateFlow.collectAsStateWithLifecycle()

                    when (productState.value) {
                        is NetworkState.Success -> {
                            productState.value.data?.let {
                                Products(it)
                            }
                        }
                        is NetworkState.Failure -> {
                            productState.value.error?.let {
                                ProductError(it, snackbarHostState)
                            }
                        }
                        is NetworkState.Loading -> {

                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductError(error: Throwable, snackbarHostState: SnackbarHostState) {
    LaunchedEffect("ProductError") {
        error.message?.let {
            snackbarHostState.showSnackbar(
                it
            )
        }
    }
}

@Composable
fun SearchBody() {
    //TODO("Not yet implemented")
}

@Composable
fun Products(data: List<Product>) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(data) {
            Text(text = it.toString())
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.app_name),
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleMedium
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProductAppTheme {
        Greeting()
    }
}