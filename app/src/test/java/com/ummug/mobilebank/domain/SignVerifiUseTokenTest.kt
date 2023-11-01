@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ummug.mobilebank.domain

import android.annotation.SuppressLint
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepository
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SignVerifiUseTokenTest{
    @Mock
    private lateinit var authRepository: AuthRepository

    private lateinit var signVerifiUseToken: SignVerifiUseToken

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        signVerifiUseToken = SignVerifiUseToken(authRepository)
    }

    @Test
    fun `invoke with valid code returns Success state`() = runTest {
        // Arrange
        val token = "token"
        val code = "123456"
        val expectedUseToken = "useToken"

        Mockito.`when`(authRepository.getUseToken(any())).thenReturn(expectedUseToken)

        // Act
        val result = signVerifiUseToken.invoke(token, code)

        // Assert
        assertTrue(result is State.Success<*>)
//        assertEquals(expectedUseToken, (result as State.Success<*>).data)
        Mockito.verify(authRepository, Mockito.times(1)).getUseToken(any())
        Mockito.verify(authRepository, Mockito.times(1)).useToken = expectedUseToken
    }

    @Test
    fun `invoke with invalid code returns Error state`() = runTest {
        // Arrange
        val token = "token"
        val code = "1234"

        // Act
        val result = signVerifiUseToken.invoke(token, code)

        // Assert
        assertTrue(result is State.Error)
//        assertEquals(ErrorCodes.CODE_ERROR, (result as State.Error).errorCode)
        Mockito.verify(authRepository, Mockito.never()).getUseToken(any())
        Mockito.verify(authRepository, Mockito.never()).useToken = any()
    }

    fun <T> any(): T = Mockito.any()

    @After
    fun teardown() {
        // Dispatchers.resetMain() ni o'chirishga kerak yo'q
    }
}