package data.gateway.fake

import data.remote.mapper.toEntity
import data.remote.model.LocationDto
import data.remote.model.RestaurantDto
import domain.entity.Restaurant
import domain.gateway.IFavoriteRestaurantGateway

class FakeFavoriteRestaurantGateway : IFavoriteRestaurantGateway {
    override suspend fun getFavoriteRestaurants(): List<Restaurant> {
        return restaurants.map { it.toEntity() }
    }

    override suspend fun addRestaurantToFavorites(restaurantId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean {
        TODO("Not yet implemented")
    }

    private val restaurants = listOf(
        RestaurantDto(
            id = "64f372095fecc11e6d917656",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "hamada",
            name = "Hamada Market",
            rate = 4.5,
            priceLevel = "$$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "07:30",
            openingTime = "22:00",
            location = LocationDto(22.0, 10.5),
            address = "Main street, 123"
        ),
        RestaurantDto(
            id = "64f373b35fecc11e6d917659",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "masala",
            name = "Masala Restaurant",
            rate = 3.5,
            priceLevel = "$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "09:00",
            openingTime = "20:00",
            location = LocationDto(12.0, 10.5),
            address = "New street, 23"
        ),
        RestaurantDto(
            id = "64f373be5fecc11e6d91765a",
            ownerId = "64f3663e5ddbc15bfd1efcfa",
            ownerUsername = "the_chance",
            name = "Chance Market",
            rate = 5.0,
            priceLevel = "$$$",
            phone = "1234567890",
            description = "This is description",
            closingTime = "08:30",
            openingTime = "17:00",
            location = LocationDto(33.0, 22.5),
            address = "Cairo street, 101"
        )
    )
}