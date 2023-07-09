package com.example.guidomia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.guidomia.MyApp
import com.example.guidomia.domain.model.Car
import com.example.guidomia.domain.repository.CarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val carRepository: CarRepository
) : ViewModel() {

    /**
     * Here we defined a private MutableStateFlow<HomeState> so the other developers will know
     * that this should be only mutated inside this class.
     * We only need to expose an immutable StateFlow<HomeState> to the UI
     */
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    /**
     * Here we also need to define a private _allCarList to store all the cars available
     * because we will override the carList in _state when we are querying by make or by model.
     */
    private val _allCarList: MutableStateFlow<List<Car>> = MutableStateFlow(emptyList())

    /**
     * Call the fetchCarList() immediately when you create this HomeViewModel class.
     */
    init {
        fetchCarList()
    }

    private fun fetchCarList() {
        viewModelScope.launch {
            val carList: List<Car> = carRepository.getAll()

            _allCarList.update { carList }

            _state.update {
                it.copy(
                    carList = carList
                )
            }
        }
    }

    /**
     * This is an MVI architecture approach.
     * This onEvent() centralizes all event that the UI can do that should affect the _state field,
     * submitting forms, or anything that has relations with business logic that the UI should know nothing about.
     */
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnMakeFilterQueryChanged -> {
                _state.update { it.copy(makeFilterQuery = event.value) }

                if (event.value.isNotBlank()) {
                    _state.update {
                        it.copy(
                            carList = _allCarList.value.filter { car ->
                                car.make.contains(event.value, true)
                            }
                        )
                    }
                } else {
                    _state.update { it.copy(carList = _allCarList.value) }
                }
            }
            is HomeEvent.OnModelFilterQueryChanged -> {
                _state.update { it.copy(modelFilterQuery = event.value) }

                if (event.value.isNotBlank()) {
                    _state.update {
                        it.copy(
                            carList = _allCarList.value.filter { car ->
                                car.model.contains(event.value, true)
                            }
                        )
                    }
                } else {
                    _state.update { it.copy(carList = _allCarList.value) }
                }
            }
        }
    }

    companion object {

        /**
         * Since we have constructor field in our HomeViewModel we need to create a Factory because ViewModelProvider does not know by default how to pass the CarRepository field.
         */
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val context =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return HomeViewModel(carRepository = (context as MyApp).carRepository) as T
            }
        }
    }
}