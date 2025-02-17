package com.example.se121p11new.presentation.image_captioning_screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.presentation.image_captioning_screen.components.ClickableWords
import com.example.se121p11new.presentation.image_captioning_screen.components.SelectedVocabulary
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun ImageCaptioningScreen(
    uri: Any,
    englishText: Resource<String>,
    vietnameseText: Resource<String>,
    imageName: Resource<String>,
    capturedTime: Resource<String>,
    onGoToVocabularyDetail: (String) -> Unit,
    onDeleteVocabulary: (String) -> Unit,
    onSaveVocabulary: (String) -> Unit,
    onBack: () -> Unit
) {
    val selectedWords = rememberMutableStateListOf<String>()

    val testSelectedWords = rememberSaveable {
        mutableListOf<String>()
    }

    LaunchedEffect(key1 = Unit) {
        println("test selected words: $testSelectedWords")
    }

    BackHandler {
        onBack()
    }

    val context = LocalContext.current as ComponentActivity
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        IconButton(
            modifier = Modifier
                .offset(x = 5.dp, y = 10.dp),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(35.dp)
                .padding(top = 30.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xffD9D9D9))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .build(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .fillMaxSize()
                    .width(180.dp)
                    .aspectRatio(9.0f / 16.0f)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillHeight,
                placeholder = painterResource(R.drawable.kiana_and_captain)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.image_name_text) + ": ", fontWeight = FontWeight.Bold)
                when(imageName) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> Text(imageName.data!!)
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource( R.string.capture_time_text) + ": ",
                    fontWeight = FontWeight.Bold
                )
                when(capturedTime) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> Text(capturedTime.data!!)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.description_text),
                modifier = Modifier.align(Alignment.Start),
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                when (englishText) {
                    is Resource.Loading ->
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xff9A00F7)
                        )

                    is Resource.Error ->
                        Toast.makeText(context, vietnameseText.message, Toast.LENGTH_SHORT).show()

                    is Resource.Success ->
                        ClickableWords(
                            text = englishText.data ?: "",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            selectedWords = selectedWords
                        ) {
                            if(selectedWords.contains(it)) {
                                selectedWords -= it
                                testSelectedWords -= it
                            } else {
                                selectedWords += it
                                testSelectedWords += it
                            }
                        }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                when (vietnameseText) {
                    is Resource.Loading ->
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xff9A00F7)
                        )

                    is Resource.Error ->
                        Toast.makeText(context, vietnameseText.message, Toast.LENGTH_SHORT).show()

                    is Resource.Success ->
                        Text(
                            text = vietnameseText.data ?: "",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                        )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.vocabulary_text),
                modifier = Modifier.align(Alignment.Start),
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .height(300.dp)
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(selectedWords) { selectedWord ->
                        SelectedVocabulary(
                            word = selectedWord,
                            onGoToDetail = onGoToVocabularyDetail,
                            onDeleteVocabulary = onDeleteVocabulary,
                            onSaveVocabulary = onSaveVocabulary,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            OutlinedButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffEFCFED),
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp),
                onClick = {

                }
            ) {
                Text(
                    text = stringResource(R.string.recaptioning_text),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun ImageCaptioningScreenPreview() {
    SE121P11NewTheme {
        ImageCaptioningScreen(
//            bitmap = Resource.Success(ImageBitmap.imageResource(R.drawable.kiana_and_captain).asAndroidBitmap()),
//            englishText = Resource.Loading(),
            uri = Uri.parse(
                "file:///data/user/0/com.example.se121p11new/files/1733558560621.jpg"
            ),
            vietnameseText = Resource.Success("Xin chào, đây là Kiana"),
            englishText = Resource.Success("Hello, this is Kiana"),
            imageName = Resource.Success("PTR-061124."),
            capturedTime = Resource.Success("16:53, Thứ tư, ngày 06 tháng 11, 2024."),
            onGoToVocabularyDetail = {},
            onBack = {},
            onDeleteVocabulary = {},
            onSaveVocabulary = {},
        )
    }
}

@Composable
fun <T: Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(saver = snapshotStateListSaver()) {
        elements.toList().toMutableStateList()
    }
}

private fun <T : Any> snapshotStateListSaver() = listSaver<SnapshotStateList<T>, T>(
    save = { stateList -> stateList.toList() },
    restore = { it.toMutableStateList() },
)