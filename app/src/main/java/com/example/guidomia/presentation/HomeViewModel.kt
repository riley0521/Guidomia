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
     * Call the fetchCarList() immediately when you create this HomeViewModel class.
     */
    init {
        fetchCarList()
    }

    private fun fetchCarList() {
        viewModelScope.launch {
            val carList: List<Car> = carRepository.getAll()

            _state.update {
                it.copy(
                    carList = carList
                )
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