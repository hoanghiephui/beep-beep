package org.thechance.service_restaurant.domain.usecase.cuisine

import org.thechance.service_restaurant.domain.entity.Cuisine

interface GetCuisineByIdUseCase {

    suspend operator fun invoke(id: String): Cuisine

}