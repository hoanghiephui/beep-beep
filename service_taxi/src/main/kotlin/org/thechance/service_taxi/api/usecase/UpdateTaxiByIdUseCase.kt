package org.thechance.service_taxi.api.usecase

import org.thechance.service_taxi.domain.entity.Taxi

interface UpdateTaxiByIdUseCase {
    suspend operator fun invoke(taxi: Taxi): Boolean
}