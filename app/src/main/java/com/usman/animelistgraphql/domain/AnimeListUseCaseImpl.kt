package com.usman.animelistgraphql.domain

import com.apollographql.apollo.api.ApolloResponse
import com.usman.animelistgraphql.AnimeListQuery
import com.usman.animelistgraphql.data.repositories.AnimeListRepository
import com.usman.animelistgraphql.utils.UiStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeListUseCaseImpl@Inject constructor(
    private val animeListRepository: AnimeListRepository
): AnimeListUseCase {
    override suspend fun invoke(page: Int):  Flow<UiStatus<ApolloResponse<AnimeListQuery.Data>>> = flow {
        emit(UiStatus.Loading)
        try {
            val animeList = animeListRepository.getAnimeListData(page)
               emit(UiStatus.Success(animeList))

        } catch (e: Exception){
            emit(UiStatus.Error(e))
        }
    }

}