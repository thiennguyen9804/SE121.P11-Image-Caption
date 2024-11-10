package com.example.se121p11new.presentation.shared_view_model

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private var _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()

    private var _imageName = MutableStateFlow("")
    var imageName = _imageName.asStateFlow()

    var normalImageName = ""

    fun setImageName(name: String) {
        _imageName.value = name
    }


    fun updateBitmap(newBitmap: Bitmap?) {
        _bitmap.value = newBitmap
    }


}