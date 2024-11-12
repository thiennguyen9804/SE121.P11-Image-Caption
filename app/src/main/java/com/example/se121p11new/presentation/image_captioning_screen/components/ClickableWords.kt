package com.example.se121p11new.presentation.image_captioning_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun ClickableWords(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Left,
    onWordTap: (String) -> Unit,
) {
    val words = text.trim().split(" ")


    val annotatedString = buildAnnotatedString {
        words.forEachIndexed { index, word ->
            // Add annotation to make each word clickable
            pushStringAnnotation(tag = "WORD", annotation = word)
            withStyle(style = SpanStyle(color = textColor)) {
                append(word)
            }
            pop()
            if (index != words.size - 1) append(" ")
        }
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = TextStyle(
            fontSize = fontSize,
            color = textColor,
            fontWeight = fontWeight,
            textAlign = textAlign
        )

    ) { offset ->
        annotatedString.getStringAnnotations(tag = "WORD", start = offset, end = offset)
            .firstOrNull()?.let { annotation ->
                onWordTap(annotation.item)
            }
    }
}

@Preview
@Composable
private fun ClickableWordsPreview() {
    var selectedWord by remember { mutableStateOf<String?>(null) }
    SE121P11NewTheme {
        ClickableWords(
            modifier = Modifier.background(Color.White),
            text = "Hello World, my crush is Kiana",
        ) {
            selectedWord = it
            println("You just tapped on $selectedWord")
        }
    }
}