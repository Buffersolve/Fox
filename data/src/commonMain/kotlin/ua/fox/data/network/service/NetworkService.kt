package ua.fox.data.network.service

import ua.fox.data.model.FoxModel

internal interface NetworkService {

    suspend fun getFox(): ApiResult<FoxModel>

}