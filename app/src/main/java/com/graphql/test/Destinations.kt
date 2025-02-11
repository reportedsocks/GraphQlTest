package com.graphql.test

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data object CharacterList

@Serializable
data class CharacterDetails(val id: String)