package com.example.se121p11new.presentation.vocabulary_folder_group_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.se121p11new.data.local.realm_object.VocabularyFolder

@Composable
fun VocabularyFolderSheetItem(
    checked: Boolean,
    vocabularyFolder: VocabularyFolder,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange

        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = vocabularyFolder.name,
            modifier = Modifier.weight(1f),
            color = Color.Black
        )
    }
}