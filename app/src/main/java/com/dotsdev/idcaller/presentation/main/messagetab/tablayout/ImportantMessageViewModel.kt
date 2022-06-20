package com.dotsdev.idcaller.presentation.main.messagetab.tablayout

import com.dotsdev.idcaller.domain.message.query.GetMessageLog
import com.dotsdev.idcaller.presentation.main.messagetab.MessageListViewModel
import com.dotsdev.idcaller.presentation.main.messagetab.MessageTabViewModel

class ImportantMessageViewModel(getMessageLog: GetMessageLog) : MessageListViewModel(getMessageLog)
