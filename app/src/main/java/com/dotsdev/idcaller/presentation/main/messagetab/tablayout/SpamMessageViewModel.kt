package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import com.dotsdev.idcaller.core.SingleLiveEvent
import com.dotsdev.idcaller.core.base.BaseViewModel
import com.dotsdev.idcaller.domain.detectSpam.DetectSpamMessage
import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.widget.recycler.ContactMessageInfo

class SpamMessageViewModel(
    private val detectSpamMessage: DetectSpamMessage,
    getMessageLog: GetMessageLog,
) : BaseViewModel() {
    val listMessage = SingleLiveEvent<List<ContactMessageInfo>>()
    val detailClick = SingleLiveEvent<ContactMessageInfo>()

    init {
        detectSpamMessage("Nhập miễn phí trong 2 WKLY Comp để giành chiến thắng FA Cup Final giải thưởng khuyến mãi ngày 21 tháng 5 năm 2005.")
        detectSpamMessage("Làm ăn đi")
        detectSpamMessage("Đi chơi không")
        detectSpamMessage("Ban la thue bao vip")
        detectSpamMessage("Khuyến mại hôm nay")
    }

    val onItemClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->

    }
    val onToDetailClick: ((info: ContactMessageInfo, position: Int) -> Unit) = { info, _ ->
        detailClick.postValue(info)
    }
}
