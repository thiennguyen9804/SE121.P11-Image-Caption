package com.example.se121p11new.presentation.vocabulary_detail_screen.components

import android.text.style.ParagraphStyle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.data.remote.dto.RealmDefinition
//import com.example.se121p11new.domain.data.Definition

@Composable
fun DefinitionText(
    definition: RealmDefinition,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.KeyboardDoubleArrowRight,
                contentDescription = null,
                tint = Color(0xff9A00F7)
            )
            Text(
                text = definition.definition,
                color = Color(0xff9A00F7)
            )
        }

        Column {
            definition.examples.forEach {
                ExampleText(it, modifier = Modifier.padding(horizontal = 5.dp))
            }
        }

    }
}