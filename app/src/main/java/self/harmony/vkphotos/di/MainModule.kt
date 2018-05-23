package self.harmony.vkphotos.di

import android.app.Application
import dagger.Provides
import self.harmony.vkphotos.data.api.NetworkClient
import self.harmony.vkphotos.data.api.NetworkClientImpl
import self.harmony.vkphotos.core.Constants.HOST
import self.harmony.vkphotos.data.repository.Repository
import self.harmony.vkphotos.data.repository.RepositoryImpl
import javax.inject.Named
import javax.inject.Singleton


@dagger.Module
class MainModule(val app: Application) {


    @Singleton
    @Provides
    fun provideRepository(networkClient: NetworkClient): Repository {
        return RepositoryImpl(networkClient)
    }


    @Singleton
    @Provides
    fun provideNetworkClient( @Named("baseUrl") baseUrl: String): NetworkClient {
        return NetworkClientImpl(baseUrl)
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return HOST
    }
}