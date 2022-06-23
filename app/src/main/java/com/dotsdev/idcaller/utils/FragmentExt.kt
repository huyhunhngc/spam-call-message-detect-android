package com.dotsdev.idcaller.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dotsdev.idcaller.data.model.Call
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageType
import com.dotsdev.idcaller.data.model.toCallType
import com.dotsdev.idcaller.presentation.main.mainflow.IBottomNavigation
import com.dotsdev.idcaller.widget.dialog.LoadingDialogFragment
import java.util.Calendar
import java.util.Date

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
        val numberStr = number.phoneNumberWithoutCountryCode()
        contactList.add(Contact(phoneNumber = numberStr, callerName = contactId))
        cursorPhone.moveToNext()
    }
    cursorPhone?.close()
    return contactList
}

@SuppressLint("Range")
fun Fragment.retrieveCallLog(): List<Call> {
    val cursorPhone = activity?.contentResolver?.query(
        CallLog.Calls.CONTENT_URI,
        null,
        null,
        null,
        null
    ) ?: return emptyList()
    cursorPhone.moveToFirst()
    val callList = mutableListOf<Call>()
    while (!cursorPhone.isAfterLast) {
        val number = cursorPhone.getString(
            cursorPhone.getColumnIndex(CallLog.Calls.NUMBER)
        )
        val type = cursorPhone.getString(
            cursorPhone.getColumnIndex(CallLog.Calls.TYPE)
        )
        val date = cursorPhone.getString(
            cursorPhone.getColumnIndex(CallLog.Calls.DATE)
        )
        val duration = cursorPhone.getString(
            cursorPhone.getColumnIndex(CallLog.Calls.DURATION)
        )

        val cal = Calendar.getInstance()

        val dateFromIatMils = Date(date.toLong())
        cal.time = dateFromIatMils

        val callId =
            number.phoneNumberWithoutCountryCode() +
                    "-$type-" +
                    "${cal.get(Calendar.DAY_OF_YEAR)}" +
                    "${cal.get(Calendar.YEAR)}"
        callList.add(
            Call(
                callId = callId,
                callerNumber = number,
                duration = duration,
                callType = type.toInt().toCallType(),
                iat = dateFromIatMils
            )
        )
        cursorPhone.moveToNext()
    }
    cursorPhone.close()
    return callList.sortedByDescending { it.iat }
}

fun Fragment.retrieveInBox(): List<Message> {
    return this.retrieveMessage(Uri.parse("content://sms/inbox"), true)
}

fun Fragment.retrieveSent(): List<Message> {
    return this.retrieveMessage(Uri.parse("content://sms/sent"), false)
}

@SuppressLint("Range")
fun Fragment.retrieveMessage(uri: Uri, isInBox: Boolean): List<Message> {
    val cursor = activity?.contentResolver?.query(
        uri,
        null,
        null,
        null,
        null
    ) ?: return emptyList()
    cursor.moveToFirst()
    val messageList = mutableListOf<Message>()
    while (!cursor.isAfterLast) {
        val address = cursor.getString(2).phoneNumberWithoutCountryCode()
        val date = cursor.getString(4)
        val dateSent = cursor.getString(5)
        val body = cursor.getString(12)
        messageList.add(
            Message(
                iat = Date(date.toLong()),
                from = Contact(phoneNumber = address),
                type = MessageType.SMS,
                content = body,
                messageName = address,
                messageNumber = address,
                sentByMe = isInBox
            )
        )
        cursor.moveToNext()
    }
    cursor.close()
    return messageList
}

fun Fragment.isAllowReadContacts(): Boolean {
    return (
            ContextCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
            )
}

fun Fragment.isAllowLocation(): Boolean {
    return (
            ContextCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            )
}

fun Fragment.backToRootFragment() {
    when (this) {
        is IBottomNavigation -> {
            this.backToRoot()
        }
    }
}

fun Fragment.changeTabFragment() {
    when (this) {
        is IBottomNavigation -> {
            this.onChangeTab()
        }
    }
}
