package com.dotsdev.idcaller.widget.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.dotsdev.idcaller.R

class LoadingDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_loading)
            window?.apply {
                setBackgroundDrawableResource(R.drawable.transparent)
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )

                val layoutParam = attributes
                layoutParam.dimAmount = 0.0f
                attributes = layoutParam
            }
            isCancelable = false
        }
    }
}