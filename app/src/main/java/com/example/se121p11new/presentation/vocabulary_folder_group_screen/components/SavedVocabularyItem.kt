package com.example.se121p11new.presentation.vocabulary_folder_group_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.components.PopUpMenu
import com.example.se121p11new.core.presentation.utils.SelectItem
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.remote.dto.RealmVocabulary

@Composable
fun SavedVocabularyItem(
    vocabulary: RealmVocabulary,
    onClick: (RealmVocabulary) -> Unit,
    onDeleteVocabulary: (RealmVocabulary) -> Unit,
    folderList: List<RealmVocabularyFolder>,
    onAddVocabularyToFolder: (RealmVocabulary, RealmVocabularyFolder) -> Unit,
    onRemoveVocabularyOutOfFolder: (RealmVocabulary, RealmVocabularyFolder) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    val items = listOf(
        SelectItem(
            title = "View",
            icon = R.drawable.ic_view,
            onClick = {
                onClick(vocabulary)
            },
            tint = Color(0xff616AE5)
        ),

        SelectItem(
            title = "Delete",
            icon = R.drawable.ic_delete,
            onClick = {
                onDeleteVocabulary(vocabulary)
            },
            tint = Color(0xffEA1616)
        ),

        SelectItem(
            title = "Lưu",
            icon = R.drawable.ic_save,
            onClick = {
                showBottomSheet = true
                expanded = false
            },
            tint = Color(0xff9A00F7)
        ),
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(vocabulary)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (showBottomSheet) {
            VocabularyFolderBottomSheet(
                folderList = folderList,
                vocabularyFolderList = vocabulary.vocabularyList,
                showBottomSheet = showBottomSheet,
                onDismiss = {
                    showBottomSheet = false
                },
                onAddVocabularyToFolder = {
                    onAddVocabularyToFolder(vocabulary, it)
                },
                onRemoveVocabularyOutOfFolder = {
                    onRemoveVocabularyOutOfFolder(vocabulary, it)
                },
                onCreateNewVocabularyFolder = {},
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = vocabulary.engVocab,
                fontSize = 17.sp,
            )
        }

        Box {
            IconButton(
                onClick = {
                    expanded = true
                }
            ) {
                Icon(painter = painterResource(R.drawable.ic_kebab_menu), contentDescription = null)
            }

            MaterialTheme(
                shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
            ) {
                PopUpMenu(
                    items = items,
                    onDismissPopUpMenuRequest = {
                        expanded = false
                    },
                    expanded = expanded
                )
            }

        }
    }
}