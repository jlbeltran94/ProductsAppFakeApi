package com.example.testapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testapp.getOrAwaitValue
import com.example.testapp.ui.navigation.NavigationCommand
import com.example.testapp.ui.navigation.NavigationDispatcher
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class NavigationDispatcherTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val navigationDispatcher by lazy { NavigationDispatcher() }

    @Test
    fun `validate navcomand is obtained when not handled`() {
        val command: NavigationCommand = { it.popBackStack() }
        navigationDispatcher.emit(command)
        assertEquals(command, navigationDispatcher.navigationCommands.getOrAwaitValue().getContentIfNotHandled())
    }

    @Test
    fun `validate null is obtained when previously handled`() {
        val command: NavigationCommand = { it.popBackStack() }
        navigationDispatcher.emit(command)
        val receivedCommand = navigationDispatcher.navigationCommands.getOrAwaitValue()
        receivedCommand.getContentIfNotHandled()
        assertNull(receivedCommand.getContentIfNotHandled())
    }
}