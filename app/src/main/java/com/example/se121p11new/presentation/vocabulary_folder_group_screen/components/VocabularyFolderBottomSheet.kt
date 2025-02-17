package com.example.se121p11new.presentation.vocabulary_folder_group_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VocabularyFolderBottomSheet(
    onDismiss: () -> Unit,
    onCreateNewVocabularyFolder: () -> Unit,
    folderList: List<RealmVocabularyFolder>,
    vocabularyFolderList: List<RealmVocabularyFolder>,
    onAddVocabularyToFolder: (RealmVocabularyFolder) -> Unit,
    onRemoveVocabularyOutOfFolder: (RealmVocabularyFolder) -> Unit,
    showBottomSheet: Boolean,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Lưu từ vựng vào...", color = Color(0xff9A00F7))
                TextButton(
                    onClick = { onCreateNewVocabularyFolder() }
                ) {
                    Text(text = "Thư mục mới")
                }
            }
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .background(Color.Black)
            )
            if (folderList.isEmpty()) {
                Text("Không có thư mục nào :((", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    folderList.forEach { folder ->
                        var checked by remember {
                            mutableStateOf(vocabularyFolderList.contains(folder))
                        }
                        VocabularyFolderSheetItem(
                            checked = checked,
                            onCheckedChange = {
                                if(checked) {
                                    onRemoveVocabularyOutOfFolder(folder)
                                } else {
                                    onAddVocabularyToFolder(folder)
                                }
                                checked = it
                            },
                            vocabularyFolder = folder,

                        )
                    }
                }
            }
        }
    }
}