package com.ummug.mobilebank.ui.Verification

import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.SignUpUseCase
import com.ummug.mobilebank.domain.SignVerifiUseToken
import com.ummug.mobilebank.ui.Register.RegisterFragmentViewModel
import junit.framework.TestCase
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
class VerificationFragmentViewModelTest{


    private val signVerifiUseToken = Mockito.mock(SignVerifiUseToken::class.java)
    private lateinit var viewmodel: VerificationFragmentViewModel


    @Before
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())
        viewmodel= VerificationFragmentViewModel(signVerifiUseToken)

    }

    @Test
    fun SignVeryTest()= runTest{

        Mockito.`when`(
            signVerifiUseToken.invoke(
                Mockito.anyString(),
                Mockito.anyString(),
            )
        ).thenReturn(State.Success<String>())

        val list= mutableListOf<String>()
        val job= launch ( UnconfinedTestDispatcher()){
            viewmodel.openHomeFlow.collect{
                list.add(it)
            }
        }
        viewmodel.getToken("token","password")
        runCurrent()
        job.cancel()
        TestCase.assertTrue(list.size==1)
        TestCase.assertEquals(1, list.size)
        TestCase.assertEquals("1",list.size.toString())
    }

    @Test
    fun SignUpErrorTest()= runTest{

        Mockito.`when`(
            signVerifiUseToken.invoke(
                Mockito.anyString(),
                Mockito.anyString(),
            )
        ).thenReturn(State.Error(406))

        viewmodel.getToken("token","parol")
        runCurrent()

        TestCase.assertEquals(406, viewmodel.errorFlow.value)
    }

    @Test
    fun SignUpNoNetworkTest()= runTest{

        Mockito.`when`(
            signVerifiUseToken.invoke(
                Mockito.anyString(),
                Mockito.anyString(),
            )
        ).thenReturn(State.NoNetwork)

        val list= mutableListOf<Unit>()
        val job= launch ( UnconfinedTestDispatcher()){
            viewmodel.noNetworkFlow.collect{
                list.add(it)
            }
        }
        viewmodel.getToken("token","parol")
        runCurrent()
        job.cancel()
        TestCase.assertTrue(list.size == 1)
        TestCase.assertNotNull(list.first())
    }





    @After
    fun teardown(){
        Dispatchers.resetMain()
    }
}