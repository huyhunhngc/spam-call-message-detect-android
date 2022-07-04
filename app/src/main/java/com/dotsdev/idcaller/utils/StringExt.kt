package com.dotsdev.idcaller.utils

import android.util.Patterns
import java.util.regex.Pattern

fun String.phoneNumberWithoutCountryCode(): String {
    val compile: Pattern = Pattern.compile(
        "\\+(?:84|82|81|66|65|64|63|62|61|60|58|57|56|55|54|53|52|51|49|48|47|46|45|44\\D?1624|44\\D?1534|44\\D?1481|44|43|41|40|39|36|34|33|32|31|30|27|20|7|1\\D?939|1\\D?876|1\\D?869|1\\D?868|1\\D?849|1\\D?829|1\\D?809|1\\D?787|1\\D?784|1\\D?767|1\\D?758|1\\D?721|1\\D?684|1\\D?671|1\\D?670|1\\D?664|1\\D?649|1\\D?473|1\\D?441|1\\D?345|1\\D?340|1\\D?284|1\\D?268|1\\D?264|1\\D?246|1\\D?242|1)\\D?"
    )
    return this.formatPhoneNUmber().replace(compile.pattern().toRegex(), "0")
}

fun String.formatPhoneNUmber(): String {
    return this.replace(Regex("[.*?\\-]"), "").filterNot { it.isWhitespace() }
}

fun String.isPhoneNumber(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}

fun String.toVietnamese(): List<String> {
    val regex = Regex("[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+")

    return regex.findAll(this).map {
        it.value
    }.toList()
}