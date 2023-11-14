package com.ummug.mobilebank.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hanks.passcodeview.PasscodeView
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import com.ummug.mobilebank.R
import com.ummug.mobilebank.data.settings.Preferens
import com.ummug.mobilebank.ui.History.HistoryFragment
import com.ummug.mobilebank.ui.Home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinFragment:Fragment(R.layout.fragment_pin) {

    private lateinit var passcodeView: PasscodeView
    private lateinit var preferens: Preferens
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferens = Preferens.getSettings(requireContext())

        passcodeView = view.findViewById(R.id.passcodeView)

        if (preferens.getPincode()?.isNullOrEmpty() == false){
            passcodeView.localPasscode=preferens.getPincode().toString()
        }
            passcodeView.listener = object : PasscodeViewListener {
                override fun onFail() {
                    Toast.makeText(requireContext(), "Wrong password", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onSuccess(number: String?) {
                    Toast.makeText(requireContext(), number.toString(), Toast.LENGTH_SHORT).show()
                    preferens.setPincode(number.toString())
                    parentFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container, HomeFragment())
                        .commit()

                }
            }

    }
}





