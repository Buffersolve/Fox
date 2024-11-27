package ua.fox.di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import ua.fox.data.network.di.networkModule
import ua.fox.data.network.repository.NetworkRepository
import ua.fox.data.network.repository.NetworkRepositoryImpl
import ua.fox.home.di.homeModule
import ua.fox.viewer.di.viewerModule

private val appModule = module {
    single<NetworkRepository> { NetworkRepositoryImpl() }
}

fun initKoin() = startKoin {
    modules(appModule, networkModule, viewerModule, homeModule)
}