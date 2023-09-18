package com.example.productapp.base.viewmodel

import androidx.lifecycle.ViewModel
import com.example.productapp.base.repository.BaseRepository

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */
abstract class BaseViewModel(repository: BaseRepository): ViewModel()