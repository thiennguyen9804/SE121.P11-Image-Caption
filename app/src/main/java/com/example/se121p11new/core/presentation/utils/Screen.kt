package com.example.se121p11new.core.presentation.utils

import kotlinx.serialization.Serializable

//sealed class Screen(open val route: String) {
//    data object AuthNav : Screen("auth_nav") {
//        data object LoginScreen : Screen("login_screen")
//        data object SignUpScreen : Screen("sign_up_screen")
//        data object ForgetPasswordScreen : Screen("forget_password_screen")
//    }
//
//    data object CameraNav : Screen("camera_nav") {
//        data object CameraScreen : Screen("camera_screen")
//        data object DescriptionScreen : Screen("description_screen")
//    }
//
//    data object DashboardNav : Screen("dashboard_nav") {
//        data object DashboardScreen : Screen("dashboard_screen")
//        data object ImageListFolderScreen : Screen("image_list_folder_screen")
//        data object VocabularyListFolderScreen : Screen("vocabulary_list_folder_screen")
//        data object VocabularyFolderScreen : Screen("vocabulary_folder_screen")
//        data object VocabularyDetailScreen : Screen("vocabulary_detail_screen")
//    }
//
//    data object UserDetail : Screen("user_detail_screen")
//}

@Serializable
object CameraGroupScreenRoute

@Serializable
object CameraScreenRoute

@Serializable
object LoginScreenRoute

@Serializable
object SignUpScreenRoute

@Serializable
object CapturedImagePreviewScreenRoute

@Serializable
data class ImageCaptioningScreenRoute(
    val uriString: String,
//    val imageName: String,
//    val captureTime: String,
//    val englishText: String = "",
//    val vietnameseText: String = ""
)

@Serializable
object DashboardScreenRoute

@Serializable
data class VocabularyDetailScreenRoute(
    val engVocab: String = ""
)

@Serializable
object ImageFolderGroupScreenRoute

@Serializable
object ImageFolderDashboardScreenRoute

@Serializable
object ImageFolderScreenRoute

@Serializable
data class ImageFolderDetailScreenRoute(
    val folderId: String = "",
)

@Serializable
object VocabularyFolderGroupScreenRoute

@Serializable
object VocabularyFolderDashboardScreenRoute

@Serializable
object VocabularyFolderScreenRoute

@Serializable
data class VocabularyFolderDetailScreenRoute(
    val folderId: String = ""
)

@Serializable
object AllSavedVocabularyScreenRoute

@Serializable
object AllCapturedImagesScreenRoute

@Serializable
object AuthGroupScreenRoute

@Serializable
object ProfileScreenRoute

@Serializable
object ForgetPasswordEmailEnterScreenRoute