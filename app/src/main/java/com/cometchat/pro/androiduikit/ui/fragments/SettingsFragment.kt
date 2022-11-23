package com.cometchat.pro.androiduikit.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cometchat.pro.androiduikit.other.Constants.KEY_NAME
import com.cometchat.pro.androiduikit.other.Constants.KEY_WEIGHT
import com.cometchat.pro.androiduikit.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_tracking.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.etWeight
import kotlinx.android.synthetic.main.fragment_setup.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsFromSharedPref()
        btnApplyChanges.setOnClickListener{
            val success = applyChangesToSharedPref()
            if (success){
                Snackbar.make(view, "Changes Saved", Snackbar.LENGTH_LONG).show()
            }else{
                Snackbar.make(requireView(), "Please enter all the fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun loadFieldsFromSharedPref(){
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)
        etWeight.setText(weight.toString())
    }

    private fun applyChangesToSharedPref(): Boolean{
        val nameText =""
        val weightText = etWeight.text.toString()
        if(weightText.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
                .putString(KEY_NAME, nameText)
                .putFloat(KEY_WEIGHT, weightText.toFloat())
                .apply()
        return true
    }

}