package ua.fox.data.network.di

import org.koin.dsl.module
import ua.fox.data.httpClient
import ua.fox.data.network.service.NetworkService
import ua.fox.data.network.service.NetworkServiceImpl

val networkModule = module {
    single { httpClient }
    single<NetworkService> { NetworkServiceImpl() }
}