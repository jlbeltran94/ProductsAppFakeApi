package com.example.testapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.testapp.data.api.Endpoints.URL_BASE
import com.example.testapp.data.api.ProductsService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(URL_BASE)
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("productsApp", 0)

}