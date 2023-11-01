@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ummug.mobilebank.data.repository.RegisterRepository

import com.ummug.mobilebank.datasource.AuthDataSource
import com.ummug.mobilebank.domain.entity.SignInEntity
import com.ummug.mobilebank.domain.entity.SignUpEntity
import com.ummug.mobilebank.domain.entity.SignUpResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    private val authDataSource: AuthDataSource = Mockito.mock(AuthDataSource::class.java)
    private lateinit var authRepositoryImpl: AuthRepositoryImpl

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        authRepositoryImpl = AuthRepositoryImpl(authDataSource)
    }

    @Test
    fun testSignUp() {
        runBlocking {
            val signUpEntity = SignUpEntity("username","Familya", "password","+998944406100")
            val signUpResponse = SignUpResponse("token", "code")

            Mockito.`when`(authDataSource.signUp(signUpEntity)).thenReturn(signUpResponse)

            val result = authRepositoryImpl.signUp(signUpEntity)

            assertEquals(signUpResponse, result)
            Mockito.verify(authDataSource, Mockito.times(1)).signUp(signUpEntity)
        }
    }

    @Test
    fun testSignIn() {
        runBlocking {
            val signInEntity = SignInEntity("username", "password")
            val signUpResponse = SignUpResponse("token", "code")

            Mockito.`when`(authDataSource.signIn(signInEntity)).thenReturn(signUpResponse)

            val result = authRepositoryImpl.signIn(signInEntity)

            assertEquals(signUpResponse, result)
            Mockito.verify(authDataSource, Mockito.times(1)).signIn(signInEntity)
        }
    }

    @Test
    fun testGetUseToken() {
        runBlocking {
            val signUpResponse = SignUpResponse("token", "code")
            val expectedToken = "useToken"

            Mockito.`when`(authDataSource.getUseToken(signUpResponse)).thenReturn(expectedToken)

            val result = authRepositoryImpl.getUseToken(signUpResponse)

            assertEquals(expectedToken, result)
            Mockito.verify(authDataSource, Mockito.times(1)).getUseToken(signUpResponse)
        }
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }
}