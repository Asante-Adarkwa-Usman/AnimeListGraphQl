package com.usman.animelistgraphql.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.ApolloResponse
import com.usman.animelistgraphql.AnimeListQuery
import com.usman.animelistgraphql.domain.AnimeListUseCase
import com.usman.animelistgraphql.utils.UiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewmodel@Inject constructor(
    private val animeListUseCase: AnimeListUseCase,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _animeData = MutableStateFlow<UiStatus<ApolloResponse<AnimeListQuery.Data>>>(UiStatus.Loading)
    val animeData: StateFlow<UiStatus<ApolloResponse<AnimeListQuery.Data>>> = _animeData.asStateFlow()

    private var currentPage = 1
    //call upon vm creation
    init {
     fetchAnimeData(currentPage)
    }

   //Grab all anime list
    fun fetchAnimeData(page:Int){
        viewModelScope.launch(dispatcher) {
           animeListUseCase.invoke(page).collect {
               _animeData.value = it
           }
        }
   }


    fun fetchNextPage() {
        currentPage++
        fetchAnimeData(currentPage)
    }

    fun refresh() {
        currentPage = 1;
        fetchAnimeData(currentPage);
    }



}