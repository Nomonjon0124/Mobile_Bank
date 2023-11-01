package com.ummug.mobilebank.domain

import android.annotation.SuppressLint
import com.ummug.mobilebank.data.contacts.ErrorCodes
import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.RegisterRepository.AuthRepository
import com.ummug.mobilebank.domain.entity.SignUpResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpUseCaseTest{

    private val authRepository:AuthRepository=Mockito.mock(AuthRepository::class.java)
    private lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())
        signUpUseCase= SignUpUseCase(authRepository)
    }

    @SuppressLint("CheckResult")
    @Test
    fun SignTest()= runTest{
        val token="token"
        val code="125463"
        Mockito.`when`(authRepository.signUp(any())).thenReturn(SignUpResponse(token,code))
        signUpUseCase.invoke("Ism","Familiya","parol","+998944406100")

        Mockito.verify(authRepository,Mockito.times(1)).code=code
        Mockito.verify(authRepository,Mockito.times(1)).temporaryToken=token
    }

    @Test
    fun `sign up first name error test`() = runTest {
        val token = "token"
        val code = "123456"
        Mockito.`when`(authRepository.signUp(any())).thenReturn(SignUpResponse(token, code))
        val state = signUpUseCase.invoke("is", "familiya", "parol", "+998944406100")

        Mockito.verify(authRepository, Mockito.never()).temporaryToken = Mockito.anyString()
        Mockito.verify(authRepository, Mockito.never()).code = Mockito.anyString()
        assertTrue(state is State.Error && state.code == ErrorCodes.FIRST_NAME_ERROR)
    }
    @Test
    fun `sign up last name error test`() = runTest {
        val token = "token"
        val code = "123456"
        Mockito.`when`(authRepository.signUp(any())).thenReturn(SignUpResponse(token, code))
        val state = signUpUseCase.invoke("is", "familiya", "parol", "+998944406100")

        Mockito.verify(authRepository, Mockito.never()).temporaryToken = Mockito.anyString()
        Mockito.verify(authRepository, Mockito.never()).code = Mockito.anyString()
        assertTrue(state is State.Error && state.code == ErrorCodes.LAST_NAME_ERROR)
    }
    @Test
    fun `sign up password name error test`() = runTest {
        val token = "token"
        val code = "123456"
        Mockito.`when`(authRepository.signUp(any())).thenReturn(SignUpResponse(token, code))
        val state = signUpUseCase.invoke("is", "familiya", "parol", "+998944406100")

        Mockito.verify(authRepository, Mockito.never()).temporaryToken = Mockito.anyString()
        Mockito.verify(authRepository, Mockito.never()).code = Mockito.anyString()
        assertTrue(state is State.Error && state.code == ErrorCodes.PASSWORD)
    }
    @Test
    fun `sign up phone name error test`() = runTest {
        val token = "token"
        val code = "123456"
        Mockito.`when`(authRepository.signUp(any())).thenReturn(SignUpResponse(token, code))
        val state = signUpUseCase.invoke("ism", "familiya", "parol", "+998944406100")

        Mockito.verify(authRepository, Mockito.never()).temporaryToken = Mockito.anyString()
        Mockito.verify(authRepository, Mockito.never()).code = Mockito.anyString()
        assertTrue(state is State.Error && state.code == ErrorCodes.PHONE_NUMBER)
    }

    @Test
    fun `sign up io exception test`() = runTest {
        val exception = Mockito.spy(IOException())
        Mockito.`when`(authRepository.signUp(any())).then { throw exception }
        val state = signUpUseCase.invoke("ism", "familiya", "parol", "+998944406100")

        Mockito.verify(authRepository, Mockito.never()).temporaryToken = Mockito.anyString()
        Mockito.verify(authRepository, Mockito.never()).code = Mockito.anyString()
        Mockito.verify(exception).printStackTrace()
        assertTrue(state is State.NoNetwork)
    }
    fun <T> any():T=Mockito.any()

    @After
    fun teardown(){
        Dispatchers.resetMain()
    }
}