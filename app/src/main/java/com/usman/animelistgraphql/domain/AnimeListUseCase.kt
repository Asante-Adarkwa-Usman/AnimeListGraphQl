package com.usman.animelistgraphql.domain

import com.apollographql.apollo.api.ApolloResponse
import com.usman.animelistgraphql.AnimeListQuery
import com.usman.animelistgraphql.utils.UiStatus
import kotlinx.coroutines.flow.Flow

interface AnimeListUseCase{
    suspend operator fun invoke(page: Int): Flow<UiStatus<ApolloResponse<AnimeListQuery.Data>>>
}