package org.thechance.service_taxi.data.gateway

import com.mongodb.client.model.Updates
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.ne
import org.thechance.service_taxi.api.models.trip.toCollection
import org.thechance.service_taxi.api.models.trip.toEntity
import org.thechance.service_taxi.data.DataBaseContainer
import org.thechance.service_taxi.data.collection.TripCollection
import org.thechance.service_taxi.data.utils.paginate
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.gateway.TripGateway
import org.thechance.service_taxi.utils.Constants

@Single
class TripGatewayImpl(container: DataBaseContainer) : TripGateway {
    private val tripCollection by lazy { container.database.getCollection<TripCollection>(Constants.TRIP_COLLECTION_NAME) }

    override suspend fun addTrip(trip: Trip): Boolean {
        return tripCollection.insertOne(trip.toCollection()).wasAcknowledged()
    }

    override suspend fun getTripById(tripId: String): Trip {
        return tripCollection.findOne(TripCollection::isDeleted ne true)?.toEntity()
            ?: throw Throwable()
    }

    override suspend fun getAllTrips(page: Int, limit: Int): List<Trip> {
        return tripCollection.find(TripCollection::isDeleted ne true).paginate(page, limit).toList()
            .toEntity()
    }

    override suspend fun getDriverTripsHistory(driverId: String, page: Int, limit: Int): List<Trip> {
        return tripCollection.find(
            and(
                TripCollection::isDeleted ne true,
                TripCollection::driverId eq ObjectId(driverId)
            )
        ).paginate(page, limit).toList().toEntity()
    }

    override suspend fun getClientTripsHistory(clientId: String, page: Int, limit: Int): List<Trip> {
        return tripCollection.find(
            and(
                TripCollection::isDeleted ne true,
                TripCollection::clientId eq ObjectId(clientId)
            )
        ).paginate(page, limit).toList().toEntity()
    }

    override suspend fun deleteTrip(tripId: String): Boolean {
        return tripCollection.updateOneById(
            id = ObjectId(tripId),
            update = Updates.set(TripCollection::isDeleted.name, true)
        ).modifiedCount > 0
    }

    override suspend fun updateTrip(trip: Trip): Boolean {
        return tripCollection.updateOneById(
            id = ObjectId(trip.id),
            update = trip.toCollection(),
            updateOnlyNotNullProperties = true
        ).modifiedCount > 0
    }
}