package com.example.se121p11new.presentation.vocabulary_folder_group_screen.vocabulary_folder_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.components.CircularAvatar
import com.example.se121p11new.core.presentation.components.CreatedImageFolderItem
import com.example.se121p11new.core.presentation.components.CreatedVocabularyFolderItem
import com.example.se121p11new.core.presentation.components.CustomDialog
import com.example.se121p11new.data.local.realm_object.RealmVocabularyFolder
import com.example.se121p11new.data.local.realm_object.VocabularyFolder
import com.example.se121p11new.domain.data.DomainVocabularyFolder

@Composable
fun VocabularyFolderScreen(
    onFolderCreate: (String) -> Unit,
    vocabularyFolderList: List<RealmVocabularyFolder>,
    onFolderDelete: (RealmVocabularyFolder) -> Unit,
    onFolderClick: (RealmVocabularyFolder) -> Unit,

    ) {
    var openDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 30.dp, horizontal = 15.dp)
        //            .verticalScroll(rememberScrollState())
    ) {
        if (openDialog) {
            CustomDialog(
                onDismissRequest = {
                    openDialog = false
                },
                text = "Thư mục ảnh mới",
                onFolderCreate = {
                    openDialog = false
                    onFolderCreate(it)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            CircularAvatar(
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(text = stringResource(R.string.vocabulary_storing_text))
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {

                    }
                )
            }

            Row {
                Icon(
                    imageVector = Icons.Outlined.AddCircleOutline,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        openDialog = true
                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier.clickable {

                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Outlined.GridView,
                    contentDescription = null,
                    modifier = Modifier.clickable {

                    }
                )

            }

        }


        if (vocabularyFolderList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.no_created_folder_text),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Spacer(modifier = Modifier.height(15.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(vocabularyFolderList) { folder ->
                    CreatedVocabularyFolderItem(
                        vocabularyFolder = folder,
                        onDeleteVocabularyFolder = { onFolderDelete(folder) },
                        onGoToVocabularyFolderDetail = { onFolderClick(folder) },
                    )
                }
            }
        }
    }
}