package pl.kossa.akainotes.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.kossa.akainotes.prefs.PrefsHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {

    @Provides
    @Singleton
    fun prefsHelper(@ApplicationContext context: Context): PrefsHelper {
        return PrefsHelper(context)
    }
}