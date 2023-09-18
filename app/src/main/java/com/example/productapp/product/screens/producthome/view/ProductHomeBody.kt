package com.example.productapp.product.screens.producthome.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.productapp.R
import com.example.productapp.base.model.NetworkState
import com.example.productapp.product.model.Product
import com.example.productapp.product.utils.validateSearchText
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
                    SearchBody(viewModel)

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

                        is NetworkState.Loading, is NetworkState.LoadingWithData -> {
                            LoadingBody()
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
fun LoadingBody() {
    LinearProgressIndicator()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBody(viewModel: SharedProductViewModel) {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        // below line is used to get
        // value of text field,
        value = inputValue.value,

        // below line is used to get value in text field
        // on value change in text field.
        onValueChange = {
            inputValue.value = it
            if (it.text.validateSearchText()) {
                viewModel.getProducts(it.text)
            }
        },

        // below line is used to add placeholder
        // for our text field.
        placeholder = { Text(text = stringResource(R.string.search_products)) },

        // modifier is use to add padding
        // to our text field.
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun Products(data: List<Product>) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(data) {
            Text(
                text = it.toString(),
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )
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