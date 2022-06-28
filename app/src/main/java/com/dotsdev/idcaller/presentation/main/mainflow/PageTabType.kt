package com.dotsdev.idcaller.presentation.main.mainflow

import androidx.annotation.IdRes
import com.dotsdev.idcaller.R

enum class PageTabType(@IdRes val menuId: Int) {
    NAV_CALL(R.id.nav_call),
    NAV_CONTACT(R.id.nav_contact),
    NAV_MESSAGE(R.id.nav_message),
    NAV_BLOCKING(R.id.nav_blocking)
}
