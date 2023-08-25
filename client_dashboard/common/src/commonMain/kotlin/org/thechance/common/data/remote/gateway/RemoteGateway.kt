package org.thechance.common.data.remote.gateway

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.entity.AddTaxi
import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.InvalidCredentialsException
import org.thechance.common.domain.entity.NoInternetException
import org.thechance.common.domain.entity.Restaurant
import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.entity.UnknownErrorException
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.entity.UserNotFoundException
import org.thechance.common.domain.entity.UserTokens
import org.thechance.common.domain.getway.IRemoteGateway
import org.thechance.common.domain.util.TaxiStatus
import java.net.ConnectException


class RemoteGateway(
    private val client: HttpClient
) : IRemoteGateway {

    override fun getUserData(): Admin {
        return Admin("aaaa")
    }

    override fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User> {
        return DataWrapper(
            totalPages = 0,
            numberOfResult = 0,
            result = emptyList(),
        )
    }

    override suspend fun getTaxis(): List<Taxi> {
        return emptyList()
    }

    override suspend fun createTaxi(taxi: AddTaxi): Taxi {
        println("createTaxi: $taxi")
        return Taxi("1", "1", CarColor.BLACK, "1", 4, "1", TaxiStatus.OFFLINE, "1")
    }

    override suspend fun findTaxiByUsername(username: String): List<Taxi> {
        return emptyList()
    }

    override suspend fun getRestaurants(): List<Restaurant> {
        return emptyList()
    }

    override suspend fun searchRestaurantsByRestaurantName(restaurantName: String): List<Restaurant> {
        return emptyList()
    }

    override suspend fun loginUser(username: String, password: String): UserTokens {
        return tryToExecute<ServerResponse<UserTokensRemoteDto>> {
            submitForm(
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ) {
                url("login")
                //TODO left until complete get user preferences
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value?.toEntity() ?: throw Exception()
    }


    private suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<*>>().status.errorMessages
            errorMessages?.let { throwMatchingException(it) }
            throw UnknownErrorException()
        } catch (e: ConnectException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException()
        }
    }

    private fun throwMatchingException(errorMessages: Map<String, String>) {
        errorMessages.let {
            if (it.containsErrors(WRONG_PASSWORD)) {
                throw InvalidCredentialsException(it.getOrEmpty(WRONG_PASSWORD))
            } else {
                if (it.containsErrors(USER_NOT_EXIST)) {
                    throw UserNotFoundException(it.getOrEmpty(USER_NOT_EXIST))
                } else {
                    throw UnknownErrorException()
                }
            }
        }
    }

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        const val WRONG_PASSWORD = "1013"
        const val USER_NOT_EXIST = "1043"
    }

}