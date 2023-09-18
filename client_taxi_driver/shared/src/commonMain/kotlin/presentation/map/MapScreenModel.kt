package presentation.map

import domain.entity.Location
import domain.entity.Order
import domain.usecase.ManageOrderUseCase
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val order: ManageOrderUseCase,
) : BaseScreenModel<MapScreenUiState, MapUiEffect>(MapScreenUiState()),
    MapScreenInteractionListener {

    init {
        getLiveLocation()
        getTaxiDriverName()
        findingNewOrder()
    }

    private fun findingNewOrder() {
        tryToExecute(
            function = order::foundNewOrder,
            onSuccess = ::onFoundNewOrderSuccess,
            onError = ::onError
        )
    }

    private fun onFoundNewOrderSuccess(order: Order) {
        updateState {
            it.copy(
                isLoading = false,
                isNewOrderFound = true,
                error = null,
                orderInfoUiState = order.toUiState()
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState {
            it.copy(
                isLoading = false,
                error = errorState,
            )
        }
    }


    private fun getLiveLocation() {
        tryToCollect(
            function = order::getLiveLocation,
            onNewValue = ::onGetLiveLocationSuccess,
            onError = ::onError,
        )
    }

    private fun onGetLiveLocationSuccess(location: Location) {
        updateState {
            it.copy(
                error = null,
                currentLocation = location.toUiState()
            )
        }
    }


    private fun getTaxiDriverName() {
        tryToExecute(
            function = order::getTaxiDriverName,
            onSuccess = ::onGetTaxiDriverNameSuccess,
            onError = ::onError,
        )
    }

    private fun onGetTaxiDriverNameSuccess(name: String) {
        updateState {
            it.copy(
                error = null,
                userName = name
            )
        }
    }


    override fun onClickAccept() {
        updateState {
            it.copy(
                isLoading = false,
                error = null,
                isNewOrderFound = false,
                isAcceptedOrder = true,
            )
        }
    }

    override fun onClickCancel() {
        updateState {
            it.copy(
                isLoading = true,
                isNewOrderFound = false,
                isAcceptedOrder = false,
                error = null
            )
        }
        findingNewOrder()
    }

    override fun onClickArrived() {
        updateState {
            it.copy(
                isLoading = false,
                error = null,
                orderInfoUiState = it.orderInfoUiState.copy(
                    isArrived = true
                )
            )
        }
    }

    override fun onClickDropOff() {
        updateState {
            it.copy(
                isLoading = true,
                isAcceptedOrder = false,
                error = null,
                orderInfoUiState = it.orderInfoUiState.copy(
                    isArrived = false
                )
            )
        }
        findingNewOrder()
    }

    override fun onClickCloseIcon() {
        sendNewEffect(MapUiEffect.PopUp)
    }
}