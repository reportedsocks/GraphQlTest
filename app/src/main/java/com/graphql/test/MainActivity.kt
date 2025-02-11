package com.graphql.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.graphql.test.ui.theme.GraphQlTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GraphQlTestTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = CharacterList) {
                    composable<CharacterList> {
                        CharacterList(
                            onCharacterClicked = {
                                navController.navigate(CharacterDetails(it))
                            }
                        )
                    }
                    composable<CharacterDetails> {
                        val characterId = it.toRoute<CharacterDetails>().id
                        CharacterDetailsScreen(characterId)
                    }
                }
            }
        }
    }
}
