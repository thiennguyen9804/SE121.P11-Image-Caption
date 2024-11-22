package com.example.se121p11new.presentation.dashboard_screen.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se121p11new.core.presentation.utils.Resource
import com.example.se121p11new.data.local.realm_object.Image
import com.example.se121p11new.ui.theme.SE121P11NewTheme

@Composable
fun CapturedImageItem(
    image: Image
) {
    var bitmap: Resource<Bitmap> = Resource.Loading()
    val context = LocalContext.current
//    val file = File(image.pictureUri)
    LaunchedEffect(key1 = true) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, Uri.parse(image.pictureUri))
            bitmap = Resource.Success(ImageDecoder.decodeBitmap(source))
        } else {
            bitmap = Resource.Success(MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(image.pictureUri)))
        }
    }
//    context.contentResolver.query(
//        MediaStore.Images.Media.INTERNAL_CONTENT_URI,
//        projection = null
//    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when(bitmap) {
            is Resource.Error ->
                Toast.makeText(
                    context,
                    "Cannot load image from local",
                    Toast.LENGTH_SHORT
                ).show()
            is Resource.Loading ->
                CircularProgressIndicator()
            is Resource.Success ->
                Image(
                    bitmap = bitmap.data!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
        }

        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = image.name,
            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun CapturedImageItemPreview() {
    SE121P11NewTheme {
        CapturedImageItem(
            image = Image().apply {
                pictureUri = ""
                englishText = ""
                vietnameseText = ""
                name = "Image 1"
            }
        )
    }
}