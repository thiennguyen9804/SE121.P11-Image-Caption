package com.example.se121p11new.presentation.dashboard_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se121p11new.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {
    val images = imageRepository
        .getFirstNImage(5)
        .map {
            it.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}