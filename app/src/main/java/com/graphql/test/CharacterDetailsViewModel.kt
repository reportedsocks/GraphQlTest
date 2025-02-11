package com.graphql.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsViewModel: ViewModel() {

    val state = MutableStateFlow(CharacterDetailsState())

    fun loadCharacter(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apolloClient.query(CharacterRequestQuery(id)).execute()
            response.data?.let { data ->
                state.update { it.copy(
                    isLoading = false,
                    character = data.character
                ) }
            }
        }
    }
}

data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val character: CharacterRequestQuery.Character? = null
)