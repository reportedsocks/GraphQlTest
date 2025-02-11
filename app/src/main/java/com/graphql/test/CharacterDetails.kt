package com.graphql.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.graphql.test.ui.theme.PurpleGrey80

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterDetailsScreen(id: String) {
    val viewModel = viewModel(CharacterDetailsViewModel::class)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.loadCharacter(id)
    }

    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if(state.isLoading) {
                CircularProgressIndicator()
            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    state.character?.let { character ->
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillParentMaxHeight(0.25f)
                            ) {
                                val painter = rememberAsyncImagePainter(character.image)
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .blur(20.dp)
                                )
                                Image(
                                    painter = painter,
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.width(150.dp)
                                )
                            }
                        }

                        item {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = character.name ?: "",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                FlowRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    SuggestionChip(
                                        onClick = {},
                                        label = {
                                            Text(text = "Gender: ${character.gender}")
                                        }
                                    )
                                    SuggestionChip(
                                        onClick = {},
                                        label = {
                                            Text(text = "Status: ${character.status}")
                                        }
                                    )
                                    SuggestionChip(
                                        onClick = {},
                                        label = {
                                            Text(text = "Species: ${character.species}")
                                        }
                                    )
                                    SuggestionChip(
                                        onClick = {},
                                        label = {
                                            Text(text = "Origin: ${character.origin?.name}")
                                        }
                                    )
                                }

                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "Appears in following episodes:",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }

                        character.episode.filterNotNull().forEach { episode ->
                            item {
                                ListItem(
                                    headlineContent = {
                                        Text(text = episode.name ?: "unknown")
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = PurpleGrey80
                                    ),
                                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}