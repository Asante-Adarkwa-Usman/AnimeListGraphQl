package com.usman.animelistgraphql.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.apollographql.apollo.api.ApolloResponse
import com.usman.animelistgraphql.AnimeListQuery
import com.usman.animelistgraphql.data.domain.toDomain
import com.usman.animelistgraphql.ui.widgets.AnimeItem
import com.usman.animelistgraphql.utils.UiStatus
import com.usman.animelistgraphql.vm.AnimeListViewmodel

@Composable
fun AnimeScreen(viewModel: AnimeListViewmodel = viewModel()) {
    val animeList by viewModel.animeData.collectAsState()

    Column {
        when (animeList) {
            is UiStatus.Loading -> {
                Text("Loading...")
            }
            is UiStatus.Success -> {
                val response = (animeList as UiStatus.Success<ApolloResponse<AnimeListQuery.Data>>).data
                val animeDomain = response.data?.toDomain() ?: return@Column // Handle null

                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(animeDomain.media) { media ->
                            AnimeItem(media.title, media.coverImage, media.bannerImage)
                        }
                        item {
                            Row {
                                Button(onClick = { viewModel.refresh() }) {
                                    Text("Refresh")
                                }
                                Button(onClick = { viewModel.fetchNextPage() }) {
                                    Text("Next Page")
                                }
                            }
                        }
                    }
                }
            }
            is UiStatus.Error -> {
                val errorMessage = (animeList as UiStatus.Error).message
                Text("Error: $errorMessage")
            }
        }
    }
}
