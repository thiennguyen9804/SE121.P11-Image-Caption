package com.example.se121p11new.core.presentation.components

import android.widget.CheckBox
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.data.local.realm_object.ImageFolder
import com.example.se121p11new.ui.theme.SE121P11NewTheme
import io.realm.kotlin.ext.realmListOf

@Composable
fun ImageFolderBottomSheetItem(
    checked: Boolean,
    imageFolder: ImageFolder,
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
            text = imageFolder.name,
            modifier = Modifier.weight(1f),
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun ImageFolderBottomSheetItemPreview() {
    SE121P11NewTheme {
        ImageFolderBottomSheetItem(
            checked = true,
            imageFolder = ImageFolder().apply {
                name = "Folder 1"
                imageList = realmListOf()
            },
//            onAddImageToFolder = {
//                println("add image to folder")
//            },
//            onRemoveImageOutOfFolder = {
//                println("remove image out of folder")
//            },
            onCheckedChange = {}
        )
    }
}