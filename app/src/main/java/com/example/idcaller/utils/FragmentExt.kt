package com.example.idcaller.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.idcaller.data.model.Call
import com.example.idcaller.data.model.Contact
import com.example.idcaller.widget.dialog.LoadingDialogFragment

fun Fragment.showLoadingDialog() {
    val dialog = childFragmentManager.findFragmentByTag("progress")
    if (dialog == null) {
        LoadingDialogFragment().showAllowingStateLoss(childFragmentManager, "progress")
    }
}

fun Fragment.hideLoadingDialog() {
    val dialog = childFragmentManager.findFragmentByTag("progress") as? LoadingDialogFragment
        ?: return
    dialog.dismissAllowingStateLoss()
}

fun DialogFragment.showAllowingStateLoss(fm: FragmentManager, tag: String) {
    fm.beginTransaction()
        .add(this, tag)
        .commitAllowingStateLoss()
}

fun Fragment.showKeyboard(view: View) {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(view, 0)
}

fun Fragment.hideKeyboard(view: View) {
    val manager =
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showActionBar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.show()
}

fun Fragment.hideActionBar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.hide()
}

fun Fragment.hideBackPressActionBar() {
    (requireActivity() as? AppCompatActivity)?.let {
        it.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
        }
    }
}

fun Fragment.showBackPressActionBar() {
    (requireActivity() as? AppCompatActivity)?.let {
        it.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }
}

fun Fragment.setActionBarTitle(title: String) {
    if (isAdded) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }
}

fun Fragment.setActionBarSubTitle(title: String) {
    try {
        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = title
    } catch (e: Exception) {

    }
}

fun Fragment.hideActionBarSubTitle() {
    (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = null
}

fun Fragment.setActionBarTitle(@StringRes titleId: Int) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title =
        requireContext().getString(titleId)
}

fun Fragment.setActionBarIcon(@DrawableRes iconId: Int) {
    (requireActivity() as AppCompatActivity).supportActionBar?.setIcon(iconId)
}

fun Fragment.setActionBarIcon(drawable: Drawable?) {
    (requireActivity() as AppCompatActivity).supportActionBar?.setIcon(drawable)
}

@SuppressLint("ClickableViewAccessibility")
fun Fragment.setupHideKeyboard(view: View) {
    if (view !is EditText) {
        view.setOnTouchListener { v, _ ->
            hideKeyboard(v)
            false
        }
    }
}

@SuppressLint("Range")
fun Fragment.retrieveContact(): List<Contact> {
    val cursorPhone: Cursor? = activity?.contentResolver?.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )
    cursorPhone?.moveToFirst()
    val contactList = mutableListOf<Contact>()
    while (cursorPhone?.isAfterLast == false) {
        val number = cursorPhone.getString(
            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        )
        val contactId = cursorPhone.getString(
            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        )
        contactList.add(Contact(phoneNumber = number, callerName = contactId))
        cursorPhone.moveToNext()
    }
    cursorPhone?.close()
    return contactList
}

@SuppressLint("Range")
fun Fragment.retrieveCallLog(): List<Call> {
    val cursorPhone: Cursor? = activity?.contentResolver?.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )
    cursorPhone?.moveToFirst()
    val contactList = mutableListOf<Call>()
    while (cursorPhone?.isAfterLast == false) {
        val number = cursorPhone.getString(
            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
        )
        val contactId = cursorPhone.getString(
            cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        )
        contactList.add(Call(callerNumber = number, callerName = contactId))
        cursorPhone.moveToNext()
    }
    cursorPhone?.close()
    return contactList
}