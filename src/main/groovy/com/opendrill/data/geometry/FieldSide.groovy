package com.opendrill.data.geometry

enum FieldSide {
	SIDE_1("Side 1"), SIDE_2("Side 2")

	private final String text

	/**
	 * @param text
	 */
	private FieldSide(final String text) {
		this.text = text
	}


	@Override
	public String toString() {
		return text
	}
}
