package com.opendrill.data.geometry

class FieldY {

	HashOrientation orientation
	int hash
	double step

	String orientationText
	String hashText
	String stepText

	public FieldY(HashOrientation orientation, int hash, double step,
				  String orientationText, String hashText, String stepText) {
		this.orientation = orientation
		this.hash = hash
		this.step = step
		this.orientationText = orientationText
		this.hashText = hashText
		this.stepText = stepText
	}
}
