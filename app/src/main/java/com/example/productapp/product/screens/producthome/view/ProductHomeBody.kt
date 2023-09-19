package com.example.productapp.product.screens.producthome.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.productapp.R
import com.example.productapp.base.model.NetworkState
import com.example.productapp.product.model.Product
import com.example.productapp.product.utils.validateSearchText
import com.example.productapp.product.viewmodel.SharedProductViewModel
import com.example.productapp.ui.theme.ProductAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductHomeBody(viewModel: SharedProductViewModel, onItemClick: ((Int) -> Unit)? = null) {
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

                    val productsState = viewModel._productsStateFlow.collectAsStateWithLifecycle()

                    when (productsState.value) {
                        is NetworkState.Success -> {
                            productsState.value.data?.let {
                                Products(it, onItemClick)
                            }
                        }

                        is NetworkState.Failure -> {
                            productsState.value.error?.let {
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
    Card(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        border = BorderStroke(0.5.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
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

            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),

            // below line is used to add placeholder
            // for our text field.
            placeholder = { Text(text = stringResource(R.string.search_products)) },

            // modifier is use to add padding
            // to our text field.
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
fun Products(data: List<Product>, onItemClick: ((Int) -> Unit)? = null) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(data) {
            ProductItem(it, onItemClick)
        }
    }
}

@Composable
fun ProductItem(product: Product, onItemClick: ((Int) -> Unit)? = null) {
    Card(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .clickable { onItemClick?.invoke(product.id) },
        border = BorderStroke(0.5.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        ProductDetails(product)
    }
}

@Composable
fun ProductDetails(product: Product, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    Column(
        modifier = Modifier.padding(contentPadding)
    ) {
        AsyncImage(
            model = product.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(
                    align = Alignment.CenterVertically
                ),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = product.brand,
                fontSize = 16.sp
            )
            Text(text = product.description)
            Row {
                Text(text = "Rating - ")
                Text(
                    text = product.rating.toString(),
                    color = if (product.rating > 4) {
                        Color.Green
                    } else if (product.rating > 2) {
                        Color.Yellow
                    } else {
                        Color.Red
                    }
                )
            }
            if (product.discountPercentage <= 0) {
                Text(
                    text = "Price - $${product.price}",
                    modifier = Modifier.padding(start = 8.dp)
                )
            } else {
                Row {
                    Text(
                        text = "-${product.discountPercentage}%",
                        color = Color.Red
                    )
                    Text(
                        text = "$${product.price + (product.price * product.discountPercentage / 100)}",
                        modifier = Modifier.padding(start = 8.dp),
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        color = Color.DarkGray
                    )
                    Text(
                        text = "$${product.price}",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            Text(
                text = "[${product.category}]",
                fontSize = 12.sp
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
fun ProductBodyPreview() {
    //ProductHomeBody()
}