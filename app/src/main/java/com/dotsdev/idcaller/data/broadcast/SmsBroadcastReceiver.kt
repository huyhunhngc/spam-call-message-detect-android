package com.dotsdev.idcaller.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.dotsdev.idcaller.data.memory.message.MessageMemory
import com.dotsdev.idcaller.data.model.Contact
import com.dotsdev.idcaller.data.model.Message
import com.dotsdev.idcaller.data.model.MessageType
import com.dotsdev.idcaller.domain.contact.query.GetContact
import com.dotsdev.idcaller.presentation.notification.QuickReplyNotification
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
    private val getContact: GetContact by inject()

    fun onReceive(context: Context?, intent: Intent?) {
        val intentExtras = intent?.extras
        if (intentExtras != null) {
            if (intent.action.equals(SMS_RECEIVED)) {
                val sms = intentExtras[SMS_BUNDLE] as? Array<*>?
                sms?.indices?.mapNotNull {
                    val smsMessage =
                        SmsMessage.createFromPdu(sms[it] as? ByteArray) ?: return@mapNotNull null
                    val smsBody = smsMessage.messageBody.toString()
                    val address =
                        smsMessage.displayOriginatingAddress.toString()
                            .phoneNumberWithoutCountryCode()
                    val time = smsMessage.timestampMillis
                    val message = Message(
                        messageId = "${time}-${address}",
                        from = Contact(phoneNumber = address),
                        content = smsBody,
                        iat = Date(time),
                        type = MessageType.SMS,
                        messageName = "",
                        messageNumber = address
                    )
                    context?.let {
                        val contact = getContact(message.messageNumber)
                        val notification = QuickReplyNotification.newInstance(
                            context,
                            message.iat.time.toInt(),
                            message.copy(messageName = contact?.callerName ?: address)
                        )
                        kotlin.runCatching {
                            notification.showNotification()
                        }.getOrNull()
                    }
                    message
                }?.let {
                    messageMemory.add(it)
                }
            }
        }
    }

    companion object {
        const val SMS_BUNDLE = "pdus"
        const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
    }
}
