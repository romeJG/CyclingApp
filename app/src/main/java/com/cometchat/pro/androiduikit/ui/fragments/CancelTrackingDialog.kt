package com.cometchat.pro.androiduikit.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cometchat.pro.androiduikit.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CancelTrackingDialog: DialogFragment() {

    private var yesListener: (() -> Unit)? = null

    fun setYesListener(listener: () -> Unit){
        yesListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel?")
            .setMessage("All the data will be lost are you sure?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes"){_,_->
                yesListener?.let{ yes ->
                    yes()
                }
            }
            .setNegativeButton("No"){ dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}