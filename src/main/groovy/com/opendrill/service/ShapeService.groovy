package com.opendrill.service

import java.awt.geom.Point2D

import com.opendrill.data.geometry.Point
import com.opendrill.data.geometry.Shape
import com.opendrill.util.VMath


class ShapeService {

	/**
	 * Creates points by snaking through the given anchor point shapes
	 * 
	 * @param shape
	 * @param width
	 * @param leftAnchorPoints
	 * @param rightAnchorPoints
	 * @return
	 */
	boolean snakeThroughAnchors(Shape shape, int width, Shape leftAnchorPoints, Shape rightAnchorPoints) {

		boolean success = true

		//the height of the block if the same as the number of anchor points
		int height = leftAnchorPoints.getNumberOfPoints()

		//clear this shape..
		shape.clear()

		//for each row...
		for (int i = 0; i < height; i++) {

			Point pointOne = null
			Point pointTwo = null

			//if an even row move left to right
			if (i == 0 || i % 2 == 0) {
				pointOne = leftAnchorPoints.getPointAt(i)
				pointTwo = rightAnchorPoints.getPointAt(i)
			} else {
				//move right to left
				pointOne = rightAnchorPoints.getPointAt(i)
				pointTwo = leftAnchorPoints.getPointAt(i)
			}

			//assign points from point one to point two
			Shape line = new Shape()
			boolean lineOk = createLinePoints(line, width, pointOne,pointTwo)

			if (lineOk) {

				//add each point from the line to this shape...
				for (int j = 0; j < width; j++) {
					Point point = line.getPointAt(j)
					shape.addPoint(point.x, point.y)
				} //end for each new point
			} else {
				success = false
				break
			}
		}//end for each row

		return success
	}
	
	/**
	 * Creates a line of the specified number of points given two endpoints
	 * 
	 * @param shape
	 * @param points
	 * @param pointOne
	 * @param pointTwo
	 * @return
	 */
	boolean createLinePoints(Shape shape, int points, Point pointOne, Point pointTwo) {

		boolean success = false

		//clear the current shape just to be safe
		shape.clear()

		//plot the first point
		shape.addPoint(pointOne.x, pointOne.y)

		//plot the second point
		shape.addPoint(pointTwo.x, pointTwo.y)

		//fill the shape
		fillShape(shape)
		
		//now this shape is full of dpoints

		//the points of this line need to be in order to remove the last point
		
		shape.points.remove(shape.points.size()-1)

		//equally distribute points accross this line of dpoints
		//divide the given shape by the number of marchers
		double intervalIndex = ((double) shape.getNumberOfFilledPoints()) / ((double) points-1)

		boolean tooManyMarchers = false
		if (points > shape.getNumberOfFilledPoints()) {
			tooManyMarchers = true
		}

		if (!tooManyMarchers) {
			//the first point is already there

			//for each point...
			for (int i = 1; i < points - 1; i++) {

				int shapeIndex = (int) Math.round((intervalIndex * i))
				Point currentPoint = shape.getDPointAtAsPoint(shapeIndex)
				shape.addPoint(currentPoint.x, currentPoint.y)

			} //end for each point

			//add the last point
			Point currentPoint = shape.getDPointAtAsPoint(shape.getNumberOfFilledPoints() - 1)
			shape.addPoint(currentPoint.x, currentPoint.y)

			success = true
		}

		return success
	}
	
	/**
	 * Fills this shape with every possible integer point between the points already
	 * existing in the shape.
	 * @param shape
	 */
	void fillShape(Shape shape) {
 
		 //will contain all of the new points
		 shape.filledPoints.clear()
 
		 for (int i = 0; i < shape.points.size()-1; i++) {
			 //get the slope of the current point to the next point
			 Point one = shape.getPointAt(i)
			 Point two = shape.getPointAt(i+1)
			 Point slope = VMath.getSlope(one,two)
			 Point2D.Double fractionalSlope = VMath.getFractionalSlope(slope)
 
			 Point2D.Double twoD = VMath.convertToDPoint(two)
 
			 //fill in the first given point..
			 Point2D.Double oneD = VMath.convertToDPoint(one)
			 shape.filledPoints.add(oneD)
			 
			 //for each possible slope interval along the slope...
			 for (int j = 0; j < VMath.returnLargestInt(slope.x,slope.y)-1; j++) {
 
				 //add all possible points between the two given points
				 Point2D.Double point = new Point2D.Double(
					 oneD.x + (((fractionalSlope.x * (j+1)))),
					 oneD.y + (((fractionalSlope.y * (j+1))))
				 )
 
				 shape.filledPoints.add(point)
			 }
			 //add the second point if there are no more points in the shape
			 if (i == shape.points.size()-2) {
				 shape.filledPoints.add(twoD)
			 }
		 }
 
	 }
	
	/**
	 * Changes the x, y location of the currently selected point
	 * @param shape
	 * @param x
	 * @param y
	 */
	void drawPointTo(Shape shape, int x, int y) {
		if (shape.selectedIndex > -1 && shape.selectedIndex < shape.getNumberOfPoints()) {
			Point point = shape.getPointAt(shape.selectedIndex)
			point.x = x
			point.y = y
		}
	}

	/**
	 * Attempts to select the point closest to the given point
	 * @param shape
	 * @param x
	 * @param y
	 */
	void selectPointNear(Shape shape, int x, int y) {

		//for each point in the shape...
		for (int i = 0; i < shape.getNumberOfPoints(); i++) {
			int mX = shape.getX(i)
			int mY = shape.getY(i)
			if (((mX <= x + 5) && (mX >= x - 5)) && ((mY <= y + 5) && (mY >= y - 5))) {
				shape.selectedIndex = i
				break
			}
		}
	}
}
