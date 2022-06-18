package com.dotsdev.idcaller.widget.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.dotsdev.idcaller.R

class LoadingDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_loading)
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

            val width = displayMetrics.widthPixels
            window?.apply {
                setBackgroundDrawableResource(R.drawable.transparent)
                setLayout(
                    width - 250,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            isCancelable = false
        }
    }
}
