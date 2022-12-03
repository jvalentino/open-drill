package com.opendrill.service

class ServiceBus {

	private static ServiceBus instance = null
	
	FieldService fieldService = new FieldService()
	MarcherService marcherService = new MarcherService()
	ShapeService shapeService = new ShapeService()
	
	static ServiceBus getInstance() {
		if (instance == null) {
			instance = new ServiceBus()
		}
		return instance
	}
}
