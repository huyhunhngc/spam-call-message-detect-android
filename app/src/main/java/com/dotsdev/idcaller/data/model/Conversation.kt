package com.dotsdev.idcaller.data.model

import java.io.Serializable

data class Conversation(val from: Contact, val messages: MutableList<Message> = mutableListOf()) :
    Serializable

fun List<Message>.toListConversation(): List<Conversation> {
    val conversations = mutableListOf<Conversation>()

    this.forEach { message ->
        val contact = message.from
        if (conversations.map { it.from }.contains(contact)) {
            conversations.find { it.from == contact }?.messages?.add(message)
        } else {
            conversations.add(Conversation(contact, mutableListOf(message)))
        }
    }
    return conversations
}
