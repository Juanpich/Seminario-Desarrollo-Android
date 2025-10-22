package ar.edu.unicen.seminario.di

import ar.edu.unicen.seminario.ddl.data.GamesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)

class GamesModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideGamesApi(
        retrofit: Retrofit
    ): GamesApi {
        return retrofit.create(GamesApi::class.java)
    }

}