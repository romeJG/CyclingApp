package com.cometchat.pro.androiduikit.ComponentFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.cometchat.pro.androiduikit.R
import com.cometchat.pro.uikit.ui_components.shared.cometchatUserPresence.CometChatUserPresence

class StatusIndicatorFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_status_indicator, container, false)
        val cometChatUserPresence: CometChatUserPresence = view.findViewById(R.id.statusIndicator)
        val statusChangeGroup = view.findViewById<RadioGroup>(R.id.statusChange)
        cometChatUserPresence.setUserStatus("online")
        statusChangeGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.online) {
                cometChatUserPresence.setUserStatus("online")
            } else {
                cometChatUserPresence.setUserStatus("offline")
            }
        }
        return view
    }
}