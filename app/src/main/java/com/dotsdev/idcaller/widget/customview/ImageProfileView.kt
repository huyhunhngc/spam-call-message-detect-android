package com.dotsdev.idcaller.widget.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.dotsdev.idcaller.R
import com.dotsdev.idcaller.databinding.ViewImageProfileBinding
import com.dotsdev.idcaller.utils.getBackgroundColor
import com.dotsdev.idcaller.utils.getColorFromName
import com.dotsdev.idcaller.utils.getPrimaryColor
import com.dotsdev.idcaller.utils.isPhoneNumber
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo
import java.util.*

class ImageProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val binding =
        ViewImageProfileBinding.inflate(LayoutInflater.from(context), this, true)


    fun setProfileBackground(@ColorInt color: Int) {
        binding.organizationImage.setBackgroundColor(color)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setProfileIcon(isOrganization: Boolean, @ColorInt color: Int) {
        binding.avatarIcon.setColorFilter(color)
        if (isOrganization) {
            resources.getDrawable(
                R.drawable.ic_baseline_business,
                null
            )
        } else {
            resources.getDrawable(
                R.drawable.ic_baseline_perm_identity,
                null
            )
        }.let(binding.avatarIcon::setImageDrawable)
    }

    fun setProfileText(name: String, @ColorInt color: Int) {
        binding.avatarText.apply {
            text = kotlin.runCatching {
                name.substring(0, 1)
                    .uppercase(Locale.getDefault())
            }.getOrDefault("?")
            setTextColor(color)
        }
    }
}

@BindingAdapter("info")
fun ImageProfileView.setProfile(
    info: ContactMessageInfo?
) {
    info ?: return
    val hash = info.peerName.getColorFromName()
    setProfileBackground(hash.getBackgroundColor())
    if (info.unknownNumber) {
        val isOrganization = info.peerName.isPhoneNumber().not()
        setProfileIcon(isOrganization, hash.getPrimaryColor())
        this.binding.avatarText.isVisible = false
        this.binding.avatarIcon.isVisible = true
    } else {
        this.binding.avatarText.isVisible = true
        setProfileText(info.peerName, hash.getPrimaryColor())
    }
}