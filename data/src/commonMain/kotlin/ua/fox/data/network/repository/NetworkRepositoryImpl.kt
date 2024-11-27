package ua.fox.data.network.repository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.fox.data.model.FoxModel
import ua.fox.data.network.service.ApiResult
import ua.fox.data.network.service.NetworkService

class NetworkRepositoryImpl : NetworkRepository, KoinComponent {

    private val networkService: NetworkService by inject()

    override suspend fun getFox(): ApiResult<FoxModel> = networkService.getFox()

}