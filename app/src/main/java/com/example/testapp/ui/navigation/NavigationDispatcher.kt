package com.example.testapp.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.testapp.utils.Event
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

typealias NavigationCommand = (NavController) -> Unit

@ActivityRetainedScoped
class NavigationDispatcher @Inject constructor() {

    private val _navigationCommands: MutableLiveData<Event<NavigationCommand>> = MutableLiveData()
    val navigationCommands: LiveData<Event<NavigationCommand>> = _navigationCommands

    fun emit(navigationCommand: NavigationCommand) {
        _navigationCommands.value = Event(navigationCommand)
    }
}