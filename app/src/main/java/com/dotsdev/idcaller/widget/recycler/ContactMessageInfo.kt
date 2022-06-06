package com.dotsdev.idcaller.widget.recycler

data class ContactMessageInfo(
    val peerPhotoUrl: String = "",
    val peerName: String = "",
    val subLine: String = "",
    val type: ItemType = ItemType.CONTACT
)

enum class ItemType {
    CONTACT,
    CALL,
    MESSAGE
}
