package ua.fox.data.network.repository

import ua.fox.data.model.FoxModel
import ua.fox.data.network.service.ApiResult

interface NetworkRepository {

    suspend fun getFox(): ApiResult<FoxModel>

}