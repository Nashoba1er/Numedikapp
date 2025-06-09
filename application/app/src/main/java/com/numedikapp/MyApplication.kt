package com.numedikapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.numedikapp.model.UserViewModel

class MyApplication : Application() {
    // ViewModel au niveau de l'application
    val userViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
            .create(UserViewModel::class.java)
    }
}