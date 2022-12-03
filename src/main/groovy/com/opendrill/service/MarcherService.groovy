package com.opendrill.service

import groovy.util.logging.Log4j

import java.awt.geom.Point2D

import com.opendrill.data.Marcher
import com.opendrill.data.geometry.Point
import com.opendrill.data.geometry.Shape
import com.opendrill.util.VMath

@Log4j
class MarcherService {

	/**
	 * Assigns the given number and symbol to this marcher
	 * @param marcher
	 * @param number
	 * @param imageLocation
	 */
	void assignNumberAndSymbol(Marcher marcher, int number, String imageLocation) {

		marcher.displayNumber = number + ""
		marcher.imageLocation = imageLocation
		
		// TODO: How to we handle deriving images?
		//deriveImages()

		//move backwards...
		if (marcher.previousMarcher != null) {
			assignNumberAndSymbolBackward(marcher.previousMarcher, number,imageLocation)
		}

		//move fowards...
		if (marcher.nextMarcher != null) {
			assignNumberAndSymbolFoward(marcher.nextMarcher, number,imageLocation)
		}
	}
	
	/**
	 * Assigns number and symbol and only moves backwards
	 * @param marcher
	 * @param number
	 * @param imageLocation
	 */
	protected void assignNumberAndSymbolBackward(Marcher marcher, int number, String imageLocation) {
		marcher.displayNumber = number + ""
		marcher.imageLocation = imageLocation
		
		// TODO: How to we handle deriving images?
		//deriveImages()

		if (marcher.previousMarcher != null) {
			assignNumberAndSymbolBackward(marcher.previousMarcher, number,imageLocation)
		}
	}

	/**
	 * Assigns number and symbol and only moves fowards 
	 * @param marcher
	 * @param number
	 * @param imageLocation
	 */
	protected void assignNumberAndSymbolFoward(Marcher marcher, int number, String imageLocation) {
		marcher.displayNumber = number + ""
		marcher.imageLocation = imageLocation
		
		// TODO: How to we handle deriving images?
		//deriveImages()

		if (marcher.nextMarcher != null) {
			assignNumberAndSymbolFoward(marcher.nextMarcher, number,imageLocation)
		}
	}
	
	void changePosition(Marcher m, int x, int y) {
		
		m.location = new Point(x, y)
		m.displayNumberLocation = new Point(x, y)

		//if this marcher is moving to another marcher...
		if (m.nextMarcher != null) {
			//re-animate its movement
			animate(m)
		}

		//if this marcher has another marcher moving to it...
		if (m.previousMarcher != null) {
			//re-animnate the previous marcher's movement to this one
			//*next pointers were not being corrected
			// setNextPosition(m.previousMarcher, x, y) likely not needed, is no nextX/Y anymore
			animate(m.previousMarcher)
		}
	}
	
	void animate(Marcher m) {
		if (m.nextMarcher == null) {
			return
		}
		
		if (m.animationPoints == null) {
			return
		}
		
		//get the counts to the next set
		int countsToNextSet = m.animationPoints.length

		//adjust counts for mark time
		int markTime = 0
		if (m.markBefore > 0) {
			markTime = m.markBefore
		} else if (m.markAfter > 0 ) {
			markTime = m.markAfter
		}

		// update the counts to next set to take into account marking time
		countsToNextSet = countsToNextSet - markTime

		//determine the pixel distance between this marcher and the next
		double pixelDistance = VMath.getDistance(m.location.x, m.location.y, 
			m.nextMarcher.location.x, m.nextMarcher.location.y)

		//determine the slope
		Point slope = VMath.getSlope(m.location, m.nextMarcher.location)

		//determine the fractional slope based on the number of counts
		Point2D.Double fractionalSlope = VMath.createFractionalSlope(slope, countsToNextSet)

		for (int i = 0; i < countsToNextSet; i++) {
			Point point = new Point(
					m.location.x + ((int) ((fractionalSlope.x * (i + 1)))),
					m.location.y + ((int) ((fractionalSlope.y * (i + 1))))
			)

			if (i != countsToNextSet - 1) {
				m.animationPoints[i] = new Point(point.x, point.y)
			} else {
				//always make the last marcher directly on the last point
				m.animationPoints[countsToNextSet -1] = new Point(
					m.nextMarcher.location.x, m.nextMarcher.location.y)
			}
		}

		this.animateMarkTime(m, markTime, countsToNextSet)
		
	}
	
	protected void animateMarkTime(Marcher m, int markTime, int countsToNextSet) {
		//depending on the mark time, adjust animation point array
		if (markTime > 0) {
			Point[] tempArray = new Point[countsToNextSet + markTime]

			//if marking before...
			if (markTime == m.markBefore) {

				//make the marcher stand still
				for (int i = 0; i < markTime; i++) {
					tempArray[i] = new Point(m.location.x, m.location.y)
				}

				//have the marcher move
				int counter = 0
				for (int i = markTime; i < tempArray.length; i++) {
					tempArray[i] = new Point(
							m.animationPoints[counter].x,
							m.animationPoints[counter].y)
					counter++
				}

			} else {
				//else if marking after...

				for (int i = 0; i < tempArray.length-markTime; i++) {
					tempArray[i] = new Point(
							m.animationPoints[i].x,
							m.animationPoints[i].y)
				}

				for (int i = tempArray.length-markTime; i < tempArray.length; i++) {
					tempArray[i] = new Point(m.nextMarcher.location.x, m.nextMarcher.location.y)
				}

			}

			//copy the temp array...
			m.animationPoints = new Point[tempArray.length]
			for (int i = 0; i < m.animationPoints.length; i++) {
				m.animationPoints[i] = new Point(tempArray[i].x,tempArray[i].y)
			}
		}
	}
	
	/**
	 * Sets this marchers next pointers and locations to the given marcher 
	 * 
	 * @param m1
	 * @param m2
	 * @param countsToNextSet
	 */
	void moveToMarcher(Marcher current, Marcher marcher, int countsToNextSet) {
		
		//if the next marcher already has a marcher moving to it, erase that motion...
		if (marcher.previousMarcher != null) {
			marcher.previousMarcher = null
		}

		//if this marcher is already moving somewhere, erase that motion...
		if (current.nextMarcher != null) {
			current.nextMarcher.previousMarcher = null
		}

		if (marcher != null) {

			//have the next marcher pointer point to the given marcher
			current.nextMarcher = marcher

			//have the next marcher's previous marcher point to this marcher
			current.nextMarcher.previousMarcher = current

			//form the animation point array based on the given number of counts
			current.animationPoints = new Point[countsToNextSet]

			//animates this marcher to the next pointer
			animate(current)

			//check to see if this marcher has an icon or not...
			//look elsewhere for a symbol for a marcher connected to this one
			moveFowardToFindSymbol(current)
			moveBackwardToFindSymbol(current)

		}

	}
	
	/**
	 * Used during a float to determine if this marcher is now connected to
	 * a marcher that does have a number and symbol, and upon finding a symbol
	 * steps the recursive search and runs the assignNumberAndSymbol method
	 * to assign to all connected marchers
	 * @param m
	 */
	void moveFowardToFindSymbol(Marcher m) {
		//if this marcher has a number and symbol
		if (hasNumberAndSymbol(m)) {
			//assign this marcher's number and symbol to everyone that it touches...
			int number = Integer.parseInt(m.displayNumber)
			assignNumberAndSymbol(m, number, m.imageLocation)
		} else {
			//this marcher does not have an icon, look foward...
			if (m.nextMarcher != null) {
				moveFowardToFindSymbol(m.nextMarcher)
			}
		}
	}

	/**
	 * Used during a float to determine if this marcher is now connected to
	 * a marcher that does have a number and symbol, and upon finding a symbol
	 * steps the recursive search and runs the assignNumberAndSymbol method
	 * to assign to all connected marchers
	 * @param m
	 */
	void moveBackwardToFindSymbol(Marcher m) {
		//if this marcher has a number and symbol
		if (hasNumberAndSymbol(m)) {
			//assign this marcher's number and symbol to everyone that it touches...
			int number = Integer.parseInt(m.displayNumber)
			assignNumberAndSymbol(m, number, m.imageLocation)
		} else {
			//this marcher does not have an icon, look back...
			if (m.previousMarcher != null) {
				moveBackwardToFindSymbol(m.previousMarcher)
			}
		}
	}

	/**
	 * Returns true if this marcher has been assigned a number and symbol
	 * @param m
	 * @return
	 */
	boolean hasNumberAndSymbol(Marcher m) {
		if (m.displayNumber.equals("")) {
			return false
		} else {
			return true
		}
	}
	
	/**
	 * Has this marcher follow a shape pattern to the given marcher
	 * @param current
	 * @param marcher
	 * @param countsToNextSet
	 * @param shape
	 */
	void followTo(Marcher current, Marcher marcher, int countsToNextSet, Shape shape) {

		//if the next marcher already has a marcher moving to it, erase that motion...
		if (marcher.previousMarcher != null) {
			marcher.previousMarcher.nextMarcher = null
		}

		//if this marcher is already moving somewhere, erase that motion...
		if (current.nextMarcher != null) {
			current.nextMarcher.previousMarcher = null
		}
		
		if (marcher == null) {
			return
		}

		//have the next marcher pointer point to the given marcher
		current.nextMarcher = marcher

		//have the next marcher's previous marcher point to this marcher
		current.nextMarcher.previousMarcher = current

		//form the animation point array based on the given number of counts
		current.animationPoints = new Point[countsToNextSet]

		//create the animation array of this marcher using the given shape
		//divide the given shape by the number of counts
		double intervalIndex =
				((double) shape.getNumberOfFilledPoints()) /
				((double) countsToNextSet )


		//the first count is the location at time 1
		Point currentPoint = shape.getDPointAtAsPoint(0)

		for (int i = 1; i < countsToNextSet; i++) {
			/* this is the index of the coordinates in the shape to be assigned to the
			marcher at i */
			int shapeIndex = (int) Math.round( (intervalIndex * i) )
			currentPoint = shape.getDPointAtAsPoint(shapeIndex)
			current.animationPoints[i-1] = new Point(currentPoint.x,currentPoint.y)
		}

		currentPoint = shape.getDPointAtAsPoint(shape.getNumberOfFilledPoints()-1)
		current.animationPoints[countsToNextSet-1] = new Point(currentPoint.x,currentPoint.y)

		//check to see if this marcher has an icon or not...
		//look elsewhere for a symbol for a marcher connected to this one
		moveFowardToFindSymbol(current)
		moveBackwardToFindSymbol(current)

	}
	
	/**
	 * Re-animates this marcher for the given number of counts
	 * @param m
	 * @param countsToNextSet
	 */
	void changeCountsToNextSet(Marcher m, int countsToNextSet) {

		//there are too many potential problems with allowing mark time to be
		//kept when changing counts...
		m.markBefore = 0
		m.markAfter = 0

		//if this marcher has somewhere to be animated to...
		if (m.nextMarcher != null) {
			//if this marcher is not already moving the specified number of counts...
			if (m.animationPoints.length != countsToNextSet) {

				//re-form the animation point array based on the given number of counts
				m.animationPoints = new Point[countsToNextSet]

				//re-animate this marcher
				animate(m)
			}
		}
	}
}
