package com.opendrill.data.geometry

import java.awt.geom.Point2D

class Shape {
	/** User clicked points */
	List<Point> points = []
	/** User clicked points and all the points in between */
	List<Point2D.Double> filledPoints = []
	/** The index of the last selected point */
	int selectedIndex = -1

	/**
	 * Clears the current shape
	 */
	void clear() {
		points.clear()
		filledPoints.clear()
		selectedIndex = -1
	}
	
	/**
	 * Returns the number of points in this shape
	 * @return
	 */
	int getNumberOfPoints() {
		return points.size()
	}

	/**
	 * Returns the number of D points in this shape
	 * @return
	 */
	int getNumberOfFilledPoints() {
		return filledPoints.size()
	}
	
	/**
	 * Returns the point at the given index
	 * @param index
	 * @return
	 */
	Point getPointAt(int index) {
		Point point = null
		if (index < points.size()) {
			point = (Point) points.get(index)
		}
		return point
	}
	
	/**
	 * Adds a point to this shape
	 * @param x
	 * @param y
	 */
	void addPoint(int x, int y) {
		Point point = new Point(x,y)
		points.add(point)
	}
	
	Point getDPointAtAsPoint(int index) {
		Point point = null
		if (index < filledPoints.size()) {
			Point2D.Double dpoint = (Point2D.Double) filledPoints.get(index)
			point = new Point((int)Math.round(dpoint.x),(int)Math.round(dpoint.y))
		}
		return point
	}
	
	/** Returns the X coordinate at the point at the given index */
	int getX(int index) {
		int x = -1
		Point point = getPointAt(index)
		if (point != null) x = point.x
		return x
	}

	/** Returns the Y coordinate of the point at the given index */
	int getY(int index) {
		int y = -1
		Point point = getPointAt(index)
		if (point != null) y = point.y
		return y
	}
}
