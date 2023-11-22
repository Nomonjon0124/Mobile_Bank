package com.ummug.mobilebank.screen

import com.kaspersky.kaspresso.screens.KScreen
import com.ummug.mobilebank.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object LoginScreen : KScreen<LoginScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val usernameEditText = KEditText { withId(R.id.phoneSignIn) }
    val usernameErrorText = KTextView { withId(R.id.phoneSignInError) }
    val passwordEditText = KEditText { withId(R.id.passwordSignIn) }
    val passwordErrorText = KTextView { withId(R.id.passwordSignInError) }
    val login = KButton { withId(R.id.bottomSignIn) }
    val goToRegister = KButton { withId(R.id.registratsiya) }
}