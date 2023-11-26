package com.ummug.mobilebank.domain

import com.ummug.mobilebank.data.contacts.State
import com.ummug.mobilebank.data.repository.CardsRepository
import com.ummug.mobilebank.data.settings.Settings
import com.ummug.mobilebank.domain.entity.profile.My_about
import com.ummug.mobilebank.ui.MyAbout.MyAbout
import java.io.IOException
import javax.inject.Inject

class MyAboutUseCase @Inject constructor(
    private val cardsRepository: CardsRepository,
    private val settings: Settings
) {
    private var message:My_about?=null
    suspend operator fun invoke():State{

        try {
            val myAbout = cardsRepository.My_about("Bearer ${settings.usetoken}")
           message= myAbout.body()
            return State.Success(myAbout.body())
        }catch (e:Exception){
            e.printStackTrace()
            if (e is IOException){
                return State.NoNetwork
            }
        }
        return State.Success(message?:My_about("null","nuul","null",0,"null","000","0000",false,"000"))
    }
}