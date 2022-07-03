package com.dotsdev.idcaller.utils

import android.graphics.Color
import androidx.annotation.ColorInt

enum class Colors(@ColorInt val color: Int, @ColorInt val bgColor: Int) {
    PINK(0xe30ea3, 0xf5aede),
    ORANGE(0xf7ad63, 0xf5cfa9),
    YELLOW(0xf5cd05, 0xfff8bb),
    GREEN(0x96d468, 0xe6f8db),
    BLUE(0x41befa, 0xace2fc),
    LAVENDER(0xb487de, 0xf0e0ff),
    DUNE(0x5e30f2, 0xb7a3f7),
    GREY(0x838584, 0xeeeeee),
    TEAL(0x38baab, 0xb6f0e1),
    SEASHELL(0x999999, 0xe6e3e3),
    RED(0xFF4B4B, 0xfacaca);

    companion object {
        private val colorList = values().toList().dropLast(1)
        fun from(color: Int): Colors = colorList[color]
    }
}

fun Colors.getPrimaryColor(): Int = Color.rgb(
    color.shr(16) and 0xFF,
    color.shr(8) and 0xFF,
    color and 0xFF,
)

fun Colors.getBackgroundColor(): Int = Color.rgb(
    bgColor.shr(16) and 0xFF,
    bgColor.shr(8) and 0xFF,
    bgColor and 0xFF,
)

fun String.getColorFromName(): Colors {
    if (this.isEmpty()) return Colors.from(0)
    return Colors.from(
        this.toCharArray().sumOf {
            it.code
        }.mod(10)
    )
}
