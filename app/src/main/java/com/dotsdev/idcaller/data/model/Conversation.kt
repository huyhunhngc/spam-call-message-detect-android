package com.dotsdev.idcaller.data.model

data class Conversation(val from: Contact, val messages: MutableList<Message> = mutableListOf())

fun List<Message>.toListConversation(): List<Conversation> {
    val conversations = mutableListOf<Conversation>()

    this.forEach { message ->
        val contact = message.from
        if (conversations.map { it.from }.contains(contact)) {
            conversations.find { it.from ==  contact}?.messages?.add(message)
        } else {
            conversations.add(Conversation(contact, mutableListOf(message)))
        }
    }
    return conversations
}
