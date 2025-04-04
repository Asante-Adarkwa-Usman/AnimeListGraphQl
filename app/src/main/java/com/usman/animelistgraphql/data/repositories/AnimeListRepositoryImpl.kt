package com.usman.animelistgraphql.data.repositories

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.usman.animelistgraphql.AnimeListQuery

class AnimeListRepositoryImpl(
    private val apolloClient: ApolloClient
) : AnimeListRepository {
    override suspend fun getAnimeListData(page: Int): ApolloResponse<AnimeListQuery.Data> {
        return apolloClient
            .query(AnimeListQuery(Optional.present(page)))
            .execute()
    }

}