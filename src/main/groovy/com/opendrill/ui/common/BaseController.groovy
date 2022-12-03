package com.opendrill.ui.common

import com.opendrill.service.ServiceBus;

abstract class BaseController {

	ServiceBus bus = ServiceBus.getInstance()
	
	abstract void constructionComplete()
}
