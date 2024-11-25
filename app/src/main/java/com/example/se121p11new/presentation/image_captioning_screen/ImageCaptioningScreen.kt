package com.example.se121p11new.presentation.image_captioning_screen

import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.components.LocalAsyncImage
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.presentation.image_captioning_screen.components.ClickableWords
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun ImageCaptioningScreen(
    uri: String,
    englishText: Resource<String>,
    vietnameseText: Resource<String>,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

//    val (uri, englishText, vietnameseText) = imageUI

    var selectedWord by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current as ComponentActivity
    LaunchedEffect(key1 = true) {
        context.enableEdgeToEdge()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
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
//            when(bitmap) {
//                is Resource.Loading ->
//                    CircularProgressIndicator()
////                        modifier = Modifier.align(Alignment.Center),
//                is Resource.Error ->
//                    Toast.makeText(context, bitmap.message, Toast.LENGTH_SHORT).show()
//
//                is Resource.Success ->
//                    Image(
//                        bitmap = bitmap.data!!.asImageBitmap(),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .width(180.dp)
//                            .aspectRatio(9.0f / 16.0f)
//                            .clip(RoundedCornerShape(20.dp)),
//                        contentScale = ContentScale.FillHeight
//                    )
//            }

            LocalAsyncImage(
                uriString = uri,
                modifier = Modifier.fillMaxSize()
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.description_text),
                modifier = Modifier.align(Alignment.Start),
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(10.dp)
            ) {
                when(englishText) {
                    is Resource.Loading ->
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    is Resource.Error ->
                        Toast.makeText(context, vietnameseText.message, Toast.LENGTH_SHORT).show()

                    is Resource.Success ->
                        ClickableWords(
                            text = englishText.data ?: "",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ) {
                            selectedWord = it
                        }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(10.dp)
            ) {
                when(vietnameseText) {
                    is Resource.Loading ->
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    is Resource.Error ->
                        Toast.makeText(context, vietnameseText.message, Toast.LENGTH_SHORT).show()
                    is Resource.Success ->
                        ClickableWords(
                            text = vietnameseText.data ?: "",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ) {
                            selectedWord = it
                        }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.vocabulary_text),
                modifier = Modifier.align(Alignment.Start),
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(10.dp)
            ) {
                Text(
                    text = "sleeping: ngủ",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
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
            uri = "",
            vietnameseText = Resource.Loading(),
            englishText = Resource.Loading(),
            onBack = {}
        )
    }
}