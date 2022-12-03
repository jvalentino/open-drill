package com.opendrill.data.geometry

/**
 * <p>Title: Field X</p>
 *
 * <p>Description: Represents the X (yard line) position on the marching field</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: B&B Design Concepts</p>
 *
 * @author John Valentino II
 * @version 1.0
 */
class FieldX {

    FieldSide side
	LineOrientation orientation
    int yard
    double step

    String sideText
    String orientationText
    String yardText
    String stepText

    public FieldX(FieldSide side, LineOrientation orientation, int yard, double step,
                  String sideText, String orientationText, String yardText,
                  String stepText) {
				  
        this.side = side
        this.orientation = orientation
        this.yard = yard
        this.step = step
        this.sideText = sideText
        this.orientationText = orientationText
        this.yardText = yardText
        this.stepText = stepText
    }

}
