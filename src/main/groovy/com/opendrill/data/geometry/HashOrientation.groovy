package com.opendrill.data.geometry

enum HashOrientation {
	INFRONT("Infront"), BEHIND("Behind")

	private final String text

	/**
	 * @param text
	 */
	private HashOrientation(final String text) {
		this.text = text
	}


	@Override
	public String toString() {
		return text
	}
}
