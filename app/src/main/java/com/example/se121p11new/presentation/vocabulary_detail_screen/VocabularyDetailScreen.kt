package com.example.se121p11new.presentation.vocabulary_detail_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.domain.data.Vocabulary
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun VocabularyDetailScreen(
    engWord: String,
    vocabulary: Resource<Vocabulary>
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = engWord,
            color = Color(0xff9A00F7),
            textDecoration = TextDecoration.Underline,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            when(vocabulary) {
                is Resource.Error -> {}
                is Resource.Loading ->
                    CircularProgressIndicator(
                        color = Color(0xff9A00F7),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                is Resource.Success -> {

                }
            }
        }
    }
}

@Preview
@Composable
private fun VocabularyDetailScreenPreview() {
    SE121P11NewTheme {
        VocabularyDetailScreen(
            engWord = "Hello",
            vocabulary = Resource.Loading()
        )
    }
}