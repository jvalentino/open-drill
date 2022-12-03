package com.opendrill.data

import com.opendrill.data.geometry.Point


class Marcher {

	String id = UUID.randomUUID().toString()
	
	/** The location of this marcher */
	Point location
	
	/** Represents the marcher that this marcher was in the previous set */
	Marcher previousMarcher = null
	
	/** Represents the marcher that this marcher is going to in the next set */
	Marcher nextMarcher = null
	
	
	/** Represents the coordinates of this marcher during animation,
	* where each index is the location of this marcher on a particular count.
	* The first point is the given x,y and the last point is nextX,nextY. */
	Point[] animationPoints
	
	/** The creation number of this marcher within its segment. Always 0 to number of
	 * marcher in segment -1 */
	int creationIndex = -1
	
	/** The display number of this marcher */
	String displayNumber = ""
	
	/** The image location of the image for this marcher */
	String imageLocation = "/marchers/square.gif"
	
	/** The x/y location of this marcher's display number */
	Point displayNumberLocation
	
	/** The amount to mark time before moving */
	int markBefore = 0
	
	/** The amount to mark after moving */
	int markAfter = 0

	// TODO: Not sure where to handle this, maybe a UI wrapper class
	/** The image of this marcher */
	//private Image image = null
	/** The image of this marcher when it is in the next set */
	//private Image nextImage = null
	/** The image of this marcher when it is in the previous set */
	//private Image previousImage = null
	
	Marcher(int x, int y, int creationIndex) {
		this.location = new Point(x, y)
		this.creationIndex = creationIndex
		this.displayNumberLocation = new Point(x, y)
	}
}
