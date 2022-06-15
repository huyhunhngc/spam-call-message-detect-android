package com.dotsdev.idcaller.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.telephony.SmsMessage
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageType
import com.dotsdev.idcaller.utils.phoneNumberWithoutCountryCode
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class SmsBroadcastReceiver : BroadcastReceiver() {
    private val smsReceiveRepository: SmsReceiveRepository by lazy { SmsReceiveRepository() }
    override fun onReceive(context: Context?, intent: Intent?) {
        smsReceiveRepository.onReceive(context, intent)
    }
}

@OptIn(KoinApiExtension::class)
class SmsReceiveRepository : KoinComponent {
    private val messageMemory: MessageMemory by inject()

    fun onReceive(context: Context?, intent: Intent?) {
        val intentExtras = intent?.extras
        if (intentExtras != null) {
            val sms = (intentExtras[SMS_BUNDLE] as Array<*>?)
            sms?.indices?.mapNotNull {
                val smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    SmsMessage.createFromPdu(sms[it] as ByteArray, SMS_RECEIVED_ACTION)
                } else {
                    SmsMessage.createFromPdu(sms[it] as ByteArray)
                }
                val smsBody = smsMessage.messageBody.toString()
                val address =
                    smsMessage.originatingAddress.toString().phoneNumberWithoutCountryCode()
                val time = smsMessage.timestampMillis
                Message(
                    uniqueId = "$address$time",
                    from = Contact(phoneNumber = address),
                    content = smsBody,
                    iat = Date(time),
                    type = MessageType.SMS
                )
            }?.let(messageMemory::set)
        }
    }

    companion object {
        const val SMS_BUNDLE = "pdus"
    }
}