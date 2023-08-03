package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.api.models.mappers.toDetailsDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.usecase.address.AddressUseCasesContainer
import org.thechance.service_restaurant.usecase.restaurant.RestaurantUseCasesContainer

fun Route.restaurantRoutes() {
    val addressUseCasesContainer: AddressUseCasesContainer by inject()
    val restaurantUseCases: RestaurantUseCasesContainer by inject()

    route("/restaurants") {
        get {
            val page = call.parameters.extractInt("page") ?: 1
            val limit = call.parameters.extractInt("limit") ?: 10
            val restaurants = restaurantUseCases.getRestaurants(page, limit).toDto()
            call.respond(HttpStatusCode.OK, restaurants)
        }
    }

    route("/restaurant") {

        get("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val restaurant = restaurantUseCases.getRestaurantDetails(restaurantId).toDetailsDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        get("/{id}/addresses") {
            val restaurantId = call.parameters["id"] ?: ""
            val addressDto = addressUseCasesContainer.getAddressesInRestaurant(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, addressDto)
        }

        get("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val category = restaurantUseCases.getCategoriesInRestaurant(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, category)
        }

        get("/{id}/meals") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val meals = restaurantUseCases.getMealsInRestaurant(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, meals)
        }

        post {
            val restaurant = call.receive<RestaurantDto>()
            val result = restaurantUseCases.addRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/addCategories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val categoryIds = call.receive<List<String>>()
            val result = restaurantUseCases.addCategoryToRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/addMeals") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val mealIds = call.receive<List<String>>()
            val result = restaurantUseCases.addMealsToRestaurant(restaurantId, mealIds)
            call.respond(HttpStatusCode.Created, result)
        }

        post("/{id}/addresses") {
            val restaurantId = call.parameters["id"] ?: ""
            val addressesIds = call.receive<List<String>>()
            val result = addressUseCasesContainer.addAddressToRestaurant(restaurantId, addressesIds)
            call.respond(HttpStatusCode.OK, result)
        }

        put {
            val restaurant = call.receive<RestaurantDto>()
            val result = restaurantUseCases.updateRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val result = restaurantUseCases.deleteRestaurant(restaurantId)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}/categories") {
            val restaurantId = call.parameters.extractString("id") ?: ""
            val categoryIds = call.receive<List<String>>()
            val result = restaurantUseCases.deleteCategoriesInRestaurant(restaurantId, categoryIds)
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}/addresses") {
            val restaurantId = call.parameters["id"] ?: ""
            val addressesIds = call.receive<List<String>>()
            val result = addressUseCasesContainer.deleteAddressesInRestaurant(restaurantId, addressesIds)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}