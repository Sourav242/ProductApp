package com.example.productapp.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.productapp.base.repository.BaseRepository

/**
 * @Author: Sourav PC
 * @Date: 18-09-2023
 */
abstract class BaseViewModel(application: Application, repository: BaseRepository) :
    AndroidViewModel(application)