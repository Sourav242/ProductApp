package com.example.productapp.product.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.productapp.base.model.NetworkState
import com.example.productapp.base.model.ResponseModel
import com.example.productapp.base.viewmodel.BaseViewModel
import com.example.productapp.product.model.Product
import com.example.productapp.product.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */

@HiltViewModel
class SharedProductViewModel @Inject constructor(
    private val repository: ProductRepository
): BaseViewModel(repository) {

    private val productStateFlow: MutableStateFlow<NetworkState<List<Product>>> =
        MutableStateFlow(NetworkState.Empty())
    val _productStateFlow: StateFlow<NetworkState<List<Product>>> = productStateFlow

    init {
        getProducts()
    }

    fun getProducts(search: String? = null) = viewModelScope.launch {
        productStateFlow.value = NetworkState.Loading()
        repository.getProducts(search)
            .catch { e ->
                productStateFlow.value = NetworkState.Failure(e)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                ResponseModel(0, listOf(), 0, 0)
            ).collect {
                val response = it.products
                productStateFlow.value = NetworkState.Success(response)
                Log.d("apiResponse", response.toString())
            }
    }
}