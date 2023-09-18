package com.example.productapp.base.model


sealed class NetworkState<T>(
    val data : T? = null,
    val error : Throwable? = null
){
    class Success<T>(data: T) : NetworkState<T>(data = data)
    class Failure<T>(error: Throwable) : NetworkState<T>(error = error)
    class Loading<T> : NetworkState<T>()
    class LoadingWithData<T>(data: T) : NetworkState<T>(data = data)
    class Empty<T>(data: T) : NetworkState<T>(data = data)
    class Null<T> : NetworkState<T>()
}