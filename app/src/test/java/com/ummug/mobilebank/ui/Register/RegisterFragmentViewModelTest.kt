@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.ummug.mobilebank.ui.Register

import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.domain.SignUpUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterFragmentViewModelTest{

    private val signUpUseCase = Mockito.mock(SignUpUseCase::class.java)
    private lateinit var registerFragmentViewModel: RegisterFragmentViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())
        registerFragmentViewModel= RegisterFragmentViewModel(signUpUseCase)

    }

    @Test
   fun SignUpTest()= runTest{
        Mockito.`when`(
            signUpUseCase.invoke(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                )
        ).thenReturn(State.Success<String>())

        val list= mutableListOf<String>()
        val job= launch ( UnconfinedTestDispatcher()){
            registerFragmentViewModel.openVerifyFlow.collect{
                list.add(it)
            }
        }
        registerFragmentViewModel.signUp("Ism","Familiya","password","+998944406100")
        runCurrent()
        job.cancel()
        assertTrue(list.size==1)
        assertEquals(1, list.size)
        assertEquals("1",list.size.toString())
   }

    @Test
    fun SignUpErrorTest()= runTest{

        Mockito.`when`(
            signUpUseCase.invoke(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
            )
        ).thenReturn(State.Error(406))

        registerFragmentViewModel.signUp("Ism","Familiya","password","+998944406100")
        runCurrent()

        assertEquals(406,registerFragmentViewModel.errorFlow.value)
    }

    @Test
    fun SignUpNoNetworkTest()= runTest{

        Mockito.`when`(
            signUpUseCase.invoke(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
            )
        ).thenReturn(State.NoNetwork)

        val list= mutableListOf<Unit>()
        val job= launch ( UnconfinedTestDispatcher()){
            registerFragmentViewModel.noNetworkFlow.collect{
                list.add(it)
            }
        }
        registerFragmentViewModel.signUp("Ism","Familiya","password","+998944406100")
        runCurrent()
        job.cancel()
        assertTrue(list.size==1)
        assertNotNull(list.first())
    }


    @After
    fun tesrdown(){
        Dispatchers.resetMain()
    }
}