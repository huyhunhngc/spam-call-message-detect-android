package com.example.idcaller.data.model

data class Conversation(val from: Contact, val messages: List<Message>)

fun List<Message>.toListConversation(): List<Conversation> {
    return emptyList()
}
