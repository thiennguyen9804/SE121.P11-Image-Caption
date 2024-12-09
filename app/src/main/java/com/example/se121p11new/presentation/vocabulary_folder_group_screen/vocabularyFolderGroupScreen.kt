package com.example.se121p11new.presentation.vocabulary_folder_group_screen

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.se121p11new.core.presentation.utils.AllSavedVocabularyScreenRoute
import com.example.se121p11new.core.presentation.utils.VocabularyFolderDashboardScreenRoute
import com.example.se121p11new.core.presentation.utils.VocabularyFolderDetailScreenRoute
import com.example.se121p11new.core.presentation.utils.VocabularyFolderGroupScreenRoute
import com.example.se121p11new.core.presentation.utils.VocabularyFolderScreenRoute
import com.example.se121p11new.core.presentation.utils.sharedViewModel
import com.example.se121p11new.core.presentation.utils.toIdString
import com.example.se121p11new.presentation.vocabulary_folder_group_screen.all_saved_vocabulary_screen.AllSavedVocabularyScreen
import com.example.se121p11new.presentation.vocabulary_folder_group_screen.vocabulary_folder_dashboard_screen.VocabularyFolderDashboardScreen
import com.example.se121p11new.presentation.vocabulary_folder_group_screen.vocabulary_folder_detail_screen.VocabularyFolderDetailScreen
import com.example.se121p11new.presentation.vocabulary_folder_group_screen.vocabulary_folder_screen.VocabularyFolderScreen
import org.mongodb.kbson.ObjectId

fun NavGraphBuilder.vocabularyFolderGroupScreen(
    navController: NavHostController
) {
    navigation<VocabularyFolderGroupScreenRoute>(
        startDestination = VocabularyFolderDashboardScreenRoute
    ) {
        composable<VocabularyFolderDashboardScreenRoute> {
            val sharedViewModel = it.sharedViewModel<VocabularyFolderGroupViewModel>(navController)
            VocabularyFolderDashboardScreen(
                onFolderCreate = sharedViewModel::createFolder,
                onGoToVocabularyFolder = {
                    navController.navigate(VocabularyFolderScreenRoute)
                },
                onGoToAllSavedVocabulary = {
                    navController.navigate(AllSavedVocabularyScreenRoute)
                }
            )
        }

        composable<VocabularyFolderScreenRoute> {
            val sharedViewModel = it.sharedViewModel<VocabularyFolderGroupViewModel>(navController)
            val allVocabularyFolder by sharedViewModel.vocabularyFolders.collectAsStateWithLifecycle()
            VocabularyFolderScreen(
                onFolderCreate = sharedViewModel::createFolder,
                vocabularyFolderList = allVocabularyFolder,
                onFolderDelete = {},
                onFolderClick = { folder ->
                    navController.navigate(
                        VocabularyFolderDetailScreenRoute(
                            folderId = folder._id.toIdString()
                        )
                    )
                }
            )
        }

        composable<AllSavedVocabularyScreenRoute> {
            val sharedViewModel = it.sharedViewModel<VocabularyFolderGroupViewModel>(navController)
            val allVocabularyFolders by sharedViewModel.vocabularyFolders.collectAsStateWithLifecycle()
            val allVocabularies by sharedViewModel.vocabularyList.collectAsStateWithLifecycle()
            AllSavedVocabularyScreen(
                vocabularyList = allVocabularies,
                onVocabularyClick = { folder ->
                    navController.navigate(
                        VocabularyFolderDetailScreenRoute(folder._id.toIdString())
                    )
                },
                onDeleteVocabulary = sharedViewModel::deleteVocabulary,
                allFolder = allVocabularyFolders,
                onAddVocabularyToFolder = sharedViewModel::addVocabularyToFolder,
                onRemoveVocabularyOutOfFolder = sharedViewModel::removeVocabularyOutOfFolder,
            )
        }

        composable<VocabularyFolderDetailScreenRoute> {
            val args = it.toRoute<VocabularyFolderDetailScreenRoute>()
            val folderId = ObjectId(args.folderId)
            val sharedViewModel = it.sharedViewModel<VocabularyFolderGroupViewModel>(navController)
            val allVocabularyFolders by sharedViewModel.vocabularyFolders.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = Unit) {
                sharedViewModel.getVocabularyFolderById(folderId)
            }
            val vocabularyFolder by sharedViewModel.vocabularyFolder.collectAsStateWithLifecycle()
            VocabularyFolderDetailScreen(
                vocabularyFolder = vocabularyFolder,
                onVocabularyClick = {},
                onDeleteVocabulary = sharedViewModel::deleteVocabulary,
                onAddVocabularyToFolder = sharedViewModel::addVocabularyToFolder,
                onRemoveVocabularyOutOfFolder = sharedViewModel::removeVocabularyOutOfFolder,
                allFolder = allVocabularyFolders
            )
        }
    }
}