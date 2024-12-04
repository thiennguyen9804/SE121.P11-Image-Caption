package com.example.se121p11new.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.se121p11new.R
import com.example.se121p11new.core.presentation.utils.SelectItem
import com.example.se121p11new.data.local.realm_object.RealmImage


@Composable
fun PopUpMenu(
    items: List<SelectItem>
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .height(35.dp),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            tint = item.tint
                        )
                    },
                    text = {
                        Text(text = item.title, color = Color.Black)
                    },
                    onClick = {
                        expanded = false
                        item.onClick.invoke()
                    }
                )
                if (item != items.last()) {
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .height(1.dp)
                            .background(Color.Black)
                    )
                }
            }
        }
    }
}