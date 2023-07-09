package com.example.guidomia.presentation

sealed class HomeEvent {
    data class OnMakeFilterQueryChanged(val value: String): HomeEvent()
    data class OnModelFilterQueryChanged(val value: String): HomeEvent()
}