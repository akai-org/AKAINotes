package pl.kossa.akainotes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kossa.akainotes.api.RetrofitClient
import pl.kossa.akainotes.prefs.PrefsHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun retrofitClient(prefsHelper: PrefsHelper): RetrofitClient {
        return RetrofitClient(prefsHelper)
    }
}