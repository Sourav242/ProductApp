package com.example.productapp.product.utils

import com.example.productapp.product.utils.ProductConstants.MIN_TEXT_LENGTH_SEARCH

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */

fun String.validateSearchText() = this.length >= MIN_TEXT_LENGTH_SEARCH || this.isEmpty()