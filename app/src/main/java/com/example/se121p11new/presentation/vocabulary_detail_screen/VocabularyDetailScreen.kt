package com.example.se121p11new.presentation.vocabulary_detail_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.domain.data.Definition
import com.example.se121p11new.domain.data.PartOfSpeech
import com.example.se121p11new.domain.data.PhrasalVerb
import com.example.se121p11new.domain.data.Vocabulary
import com.example.se121p11new.presentation.vocabulary_detail_screen.components.PartOfSpeechText
import com.example.se121p11new.presentation.vocabulary_detail_screen.components.PhrasalVerbText
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun VocabularyDetailScreen(
    engWord: String,
    vocabulary: Resource<Vocabulary>
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = engWord,
            color = Color(0xff9A00F7),
            textDecoration = TextDecoration.Underline,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
        )
        when (vocabulary) {
            is Resource.Error -> {
                Toast.makeText(
                    context,
                    vocabulary.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Resource.Loading -> {
                Spacer(modifier = Modifier.height(50.dp))
                CircularProgressIndicator(
                    color = Color(0xff9A00F7),
                )
            }

            is Resource.Success -> {
                val data = vocabulary.data!!


                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = data.engVocab,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = data.ipa,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    )

                    data.partOfSpeeches.forEach {
                        PartOfSpeechText(it)
                    }

                    data.phrasalVerbs.forEach {
                        PhrasalVerbText(it)
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun VocabularyDetailScreenLoadingPreview() {
    SE121P11NewTheme {
        VocabularyDetailScreen(
            engWord = "learn",
            vocabulary = Resource.Loading()
        )
    }
}

@Preview
@Composable
private fun VocabularyDetailScreenSuccessPreview() {
    SE121P11NewTheme {
        VocabularyDetailScreen(
            engWord = vocab.engVocab,
            vocabulary = Resource.Success(vocab)
        )
    }
}

internal val vocab = Vocabulary(
    "learn",
    "/lə:n/",
    partOfSpeeches = listOf(
        PartOfSpeech(
            partOfSpeech = "ngoại động từ learnt  /lə:nt/",
            definitions = listOf(
                Definition(
                    "nghe thất, được nghe, được biết",
                    listOf(
                        "to learn a piece of news from someone: biết tin qua ai"
                    )
                ),
                Definition(
                    "học, học tập, nghiên cứu",
                    emptyList()
                )
            )
        ),
    ),
    phrasalVerbs = listOf(
        PhrasalVerb(
            phrasalVerb = "I am (have) yet to learn",
            definitions = listOf(
                Definition(
                    "tôi chưa biết như thế nào, để còn xem đã",
                    emptyList()
                )
            )
        )
    )
)
