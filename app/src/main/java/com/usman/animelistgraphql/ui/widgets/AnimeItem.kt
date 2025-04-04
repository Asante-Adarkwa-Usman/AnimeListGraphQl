package com.usman.animelistgraphql.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun AnimeItem(title: String, coverImage: String?, bannerImage: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = title)

        if (coverImage != null) {
            Image(
                painter = rememberAsyncImagePainter(model = coverImage),
                contentDescription = "Cover Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        if (bannerImage != null) {
            Image(
                painter = rememberAsyncImagePainter(model = bannerImage),
                contentDescription = "Banner Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}