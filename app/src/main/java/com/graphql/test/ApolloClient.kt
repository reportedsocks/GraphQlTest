package com.graphql.test

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://rickandmortyapi.com/graphql")
    .okHttpClient(
        OkHttpClient()
            .newBuilder()
            .apply { addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }) }
            .build()
    )
    .build()