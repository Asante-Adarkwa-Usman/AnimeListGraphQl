package com.usman.animelistgraphql.data.repositories

import com.apollographql.apollo.api.ApolloResponse
import com.usman.animelistgraphql.AnimeListQuery

interface AnimeListRepository {
    suspend fun getAnimeListData(page: Int): ApolloResponse<AnimeListQuery.Data>
}