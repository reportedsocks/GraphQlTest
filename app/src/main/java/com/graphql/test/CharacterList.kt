package com.graphql.test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.graphql.test.ui.theme.PurpleGrey80

@Composable
fun CharacterList(
    onCharacterClicked: (String) -> Unit
) {
    val viewModel = viewModel(CharacterListViewModel::class)

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            if(state.isLoading) {
                CircularProgressIndicator()
            } else {
                val listState = rememberLazyListState()

                LaunchedEffect(listState.canScrollForward) {
                    if (!listState.canScrollForward) {
                        viewModel.loadNextPage()
                    }
                }

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    items(state.characters, key =  { it.id ?: ""}) { character ->
                        ListItem(
                            headlineContent = {
                                Text(text = character.name ?: "Name")
                            },
                            leadingContent = {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(character.image)
                                        .build(),
                                    contentDescription = "avatar",
                                    modifier = Modifier.size(50.dp)
                                )
                            },
                            supportingContent = {
                                Text(text = character.species ?: "Species")
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = PurpleGrey80
                            ),
                            modifier = Modifier.clickable {
                                onCharacterClicked(character.id ?: "")
                            }
                        )
                    }
                }
            }
        }
    }
}