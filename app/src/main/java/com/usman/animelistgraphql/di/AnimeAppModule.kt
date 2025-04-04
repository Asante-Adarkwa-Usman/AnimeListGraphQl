package com.usman.animelistgraphql.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.usman.animelistgraphql.BuildConfig
import com.usman.animelistgraphql.data.BASE_URL
import com.usman.animelistgraphql.data.repositories.AnimeListRepository
import com.usman.animelistgraphql.data.repositories.AnimeListRepositoryImpl
import com.usman.animelistgraphql.domain.AnimeListUseCase
import com.usman.animelistgraphql.domain.AnimeListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AnimeAppModule {
    //CoroutineDispatcher
    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO

    // HttpLoggingInterceptor
    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //OkHttp
    @Provides
    @Singleton
    fun providesOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient = OkHttpClient
        .Builder()
       // .certificatePinner(certificatePinner)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    //Certificate Pinner
    @Provides
    @Singleton
    fun providesCertificatePinner(): CertificatePinner = CertificatePinner
        .Builder()
        .add("graphql.anilist.co","sha256/${BuildConfig.apipublickey}")
        .build()

    //Apollo Client Service
    @Provides
    @Singleton
    fun providesApolloClient(
        okHttpClient: OkHttpClient
    ): ApolloClient = ApolloClient
        .Builder()
        .okHttpClient(okHttpClient)
        .serverUrl(BASE_URL)
        .build()

    //AnimeRepository
    @Provides
    @Singleton
    fun providesAnimeRepository(
        apolloClient: ApolloClient
    ): AnimeListRepository = AnimeListRepositoryImpl(apolloClient)

    //AnimeList use case
    @Provides
    @Singleton
    fun providesAnimeListUseCase(
       animeListRepository: AnimeListRepository
    ): AnimeListUseCase = AnimeListUseCaseImpl(animeListRepository)

}