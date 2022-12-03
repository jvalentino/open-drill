package com.opendrill.data.geometry

enum LineOrientation {	
	OUTSIDE("Outside"), INSIDE("Inside")
	
	private final String text

    /**
     * @param text
     */
    private LineOrientation(final String text) {
        this.text = text
    }

   
    @Override
    public String toString() {
        return text
    }
}
