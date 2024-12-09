package com.example.se121p11new.presentation.image_folder_group_screen

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.se121p11new.core.presentation.utils.AllCapturedImagesScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageCaptioningScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageFolderDashboardScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageFolderDetailScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageFolderGroupScreenRoute
import com.example.se121p11new.core.presentation.utils.ImageFolderScreenRoute
import com.example.se121p11new.core.presentation.utils.sharedViewModel
import com.example.se121p11new.core.presentation.utils.toIdString
import com.example.se121p11new.presentation.image_folder_group_screen.all_captured_images.AllCapturedImagesScreen
import com.example.se121p11new.presentation.image_folder_group_screen.image_folder_dashboard_screen.ImageFolderDashboardScreen
import com.example.se121p11new.presentation.image_folder_group_screen.image_folder_detail_screen.ImageFolderDetailScreen
import com.example.se121p11new.presentation.image_folder_group_screen.image_folder_screen.ImageFolderScreen
import org.mongodb.kbson.ObjectId

fun NavGraphBuilder.imageFolderGroupScreen(
    navController: NavHostController
) {
    navigation<ImageFolderGroupScreenRoute>(
        startDestination = ImageFolderDashboardScreenRoute
    ) {
        composable<ImageFolderDashboardScreenRoute> {
            val sharedViewModel = it.sharedViewModel<ImageFolderGroupViewModel>(navController)
            val images by sharedViewModel.images.collectAsStateWithLifecycle()
            val allImageFolder by sharedViewModel.imageFolderList.collectAsStateWithLifecycle()
            ImageFolderDashboardScreen(
                onImageClick = { image ->
                    navController.navigate(
                        ImageCaptioningScreenRoute(image.pictureUri)
                    )
                },
                onDeleteImage = sharedViewModel::deleteImage,
                onFolderCreate = sharedViewModel::createFolder,
                onGoToAllImageFolder = {
                    navController.navigate(ImageFolderScreenRoute)
                },
                images = images,
                folderList = allImageFolder,
                onAddImageToFolder = sharedViewModel::addImageToFolder,
                onRemoveImageOutOfFolder = sharedViewModel::removeImageOutOfFolder,
                onGoToAllCapturedImages = {
                    navController.navigate(AllCapturedImagesScreenRoute)
                }
            )
        }

        composable<ImageFolderScreenRoute> {
            val sharedViewModel = it.sharedViewModel<ImageFolderGroupViewModel>(navController)
            val allImageFolder by sharedViewModel.imageFolderList.collectAsStateWithLifecycle()
            ImageFolderScreen(
                onChangeFolder = {},
                onFolderClick = { folder ->
                    navController.navigate(ImageFolderDetailScreenRoute(folderId = folder._id.toIdString()))
                },
                onFolderDelete = sharedViewModel::deleteFolder,
                onFolderCreate = sharedViewModel::createFolder,
                folders = allImageFolder,
            )
        }

        composable<ImageFolderDetailScreenRoute> {
            val sharedViewModel = it.sharedViewModel<ImageFolderGroupViewModel>(navController)
            val args = it.toRoute<ImageFolderDetailScreenRoute>()
            val folderId = ObjectId(args.folderId)
            LaunchedEffect(key1 = Unit) {
                sharedViewModel.getFolderById(folderId)
            }
            val folder by sharedViewModel.folder.collectAsStateWithLifecycle()
            val allImageFolder by sharedViewModel.imageFolderList.collectAsStateWithLifecycle()
            ImageFolderDetailScreen(
                imageFolder = folder,
                onImageClick = { _image ->
                    navController.navigate(ImageCaptioningScreenRoute(_image.pictureUri))
                },
                onDeleteImage = sharedViewModel::deleteImage,
                onAddImageToFolder = sharedViewModel::addImageToFolder,
                onRemoveImageOutOfFolder = sharedViewModel::removeImageOutOfFolder,
                allFolder = allImageFolder
            )
        }

        composable<AllCapturedImagesScreenRoute> {
            val sharedViewModel = it.sharedViewModel<ImageFolderGroupViewModel>(navController)
            val allImages by sharedViewModel.images.collectAsStateWithLifecycle()
            val folder by sharedViewModel.folder.collectAsStateWithLifecycle()
            val allImageFolder by sharedViewModel.imageFolderList.collectAsStateWithLifecycle()
            AllCapturedImagesScreen(
                imageList = allImages,
                onImageClick = { _image ->
                    navController.navigate(ImageCaptioningScreenRoute(_image.pictureUri))
                },
                onDeleteImage = sharedViewModel::deleteImage,
                onAddImageToFolder = sharedViewModel::addImageToFolder,
                onRemoveImageOutOfFolder = sharedViewModel::removeImageOutOfFolder,
                allFolder = allImageFolder
            )
        }
    }
}