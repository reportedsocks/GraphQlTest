package com.graphql.test

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Optional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterListViewModel: ViewModel() {

    val state = MutableStateFlow(CharacterListState())
    private var nextPage: Int = 1
    private var loadingPageJob: Job? = null

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (loadingPageJob?.isActive != true) {
            loadingPageJob = viewModelScope.launch(Dispatchers.IO) {
                loadPage()
            }
        }
    }

    private suspend fun loadPage() {
        Log.d("GraphQL", "Loading page: $nextPage")
        val response = apolloClient
        Log.d("GraphQL", "Response data: ${response.data?.toString()}")

        response.data?.characters?.results?.let { results ->
            state.update { it.copy(
                isLoading = false,
                characters = it.characters + results.filterNotNull()
            ) }
        }

        response.data?.characters?.info?.let { info ->
            nextPage = info.next ?: 1
        }
    }
}

data class CharacterListState(
    val isLoading: Boolean = true,
    val characters: List<CharactersRequestQuery.Result> = emptyList()

)