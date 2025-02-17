package com.example.se121p11new.presentation.vocabulary_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.se121p11new.data.remote.dto.RealmPhrasalVerb
//import com.example.se121p11new.domain.data.PhrasalVerb

@Composable
fun PhrasalVerbText(
    phrasalVerb: RealmPhrasalVerb,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(phrasalVerb.phrasalVerb, textDecoration = TextDecoration.Underline)
        phrasalVerb.definitions.forEach {
            DefinitionText(it, modifier = Modifier.padding(horizontal = 5.dp))
        }
    }
}