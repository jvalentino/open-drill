package com.opendrill.service

import java.awt.geom.Point2D;

import com.opendrill.data.geometry.Point;
import com.opendrill.data.geometry.Shape;
import com.opendrill.util.VMath;

import spock.lang.Specification;

class ShapeServiceTestSpec extends Specification {
	
	ShapeService service
	
	def setup() {
		service = new ShapeService()
	}

	void "Testing filling a shape"() {
		given: "A Shape with 3 points in it"
		Shape shape = new Shape()
		shape.addPoint(0,0)
		shape.addPoint(5,5)
		shape.addPoint(10, 10)
		
		when: "We fill the spaces between the 3 points in the shape"
		service.fillShape(shape)
		
		then:
		shape.filledPoints.size() == 11
		VMath.toString(shape.filledPoints.get(0)) == "0.0, 0.0"
		VMath.toString(shape.filledPoints.get(1)) == "1.0, 1.0"
		VMath.toString(shape.filledPoints.get(2)) == "2.0, 2.0"
		VMath.toString(shape.filledPoints.get(3)) == "3.0, 3.0"
		VMath.toString(shape.filledPoints.get(4)) == "4.0, 4.0"
		VMath.toString(shape.filledPoints.get(5)) == "5.0, 5.0"
		VMath.toString(shape.filledPoints.get(6)) == "6.0, 6.0"
		VMath.toString(shape.filledPoints.get(7)) == "7.0, 7.0"
		VMath.toString(shape.filledPoints.get(8)) == "8.0, 8.0"
		VMath.toString(shape.filledPoints.get(9)) == "9.0, 9.0"
		VMath.toString(shape.filledPoints.get(10)) == "10.0, 10.0"
	}
	
	void "Test creating a line using points"() {
		given: 
		Shape shape = new Shape()
		int points = 4
		Point pointOne = new Point(0,0)
		Point pointTwo = new Point(10,10)
		
		when:
		boolean success = service.createLinePoints(shape, points, pointOne, pointTwo)
		
		then:
		shape.points.size() == 4
		
		shape.points.get(0).toString() == "0, 0"
		shape.points.get(1).toString() == "4, 4"
		shape.points.get(2).toString() == "7, 7"
		shape.points.get(3).toString() == "10, 10"
		
		shape.filledPoints.size() == 11
		
		VMath.toString(shape.filledPoints.get(0)) == "0.0, 0.0"
		VMath.toString(shape.filledPoints.get(1)) == "1.0, 1.0"
		VMath.toString(shape.filledPoints.get(2)) == "2.0, 2.0"
		VMath.toString(shape.filledPoints.get(3)) == "3.0, 3.0"
		VMath.toString(shape.filledPoints.get(4)) == "4.0, 4.0"
		VMath.toString(shape.filledPoints.get(5)) == "5.0, 5.0"
		VMath.toString(shape.filledPoints.get(6)) == "6.0, 6.0"
		VMath.toString(shape.filledPoints.get(7)) == "7.0, 7.0"
		VMath.toString(shape.filledPoints.get(8)) == "8.0, 8.0"
		VMath.toString(shape.filledPoints.get(9)) == "9.0, 9.0"
		VMath.toString(shape.filledPoints.get(10)) == "10.0, 10.0"
		
		success == true
		
	}
	
	void "Testing snaking through anchors"() {
		given:
		Shape shape = new Shape()
		int width = 5
		
		Shape leftAnchorPoints = new Shape()
		leftAnchorPoints.addPoint(0,0)
		leftAnchorPoints.addPoint(0,5)
		
		Shape rightAnchorPoints = new Shape()
		rightAnchorPoints.addPoint(5,0)
		rightAnchorPoints.addPoint(5,5)
		
		when:
		boolean success = service.snakeThroughAnchors(shape, width, leftAnchorPoints, rightAnchorPoints)
		
		then:
		success == true
		shape.points.size() == 10
		
		shape.points.get(0).toString() == "0, 0"
		shape.points.get(1).toString() == "2, 0"
		shape.points.get(2).toString() == "3, 0"
		shape.points.get(3).toString() == "5, 0"
		shape.points.get(4).toString() == "5, 0"
		shape.points.get(5).toString() == "5, 5"
		shape.points.get(6).toString() == "3, 5"
		shape.points.get(7).toString() == "2, 5"
		shape.points.get(8).toString() == "0, 5"
		shape.points.get(9).toString() == "0, 5"
		
		shape.filledPoints.size() == 0
		
	}
	
	void "Test drawing point to"() {
		given:
		Shape shape = new Shape()
		int x = 10
		int y = 20
		
		shape.addPoint(0,0)
		shape.selectedIndex = 0
		
		when:
		service.drawPointTo(shape, x, y)
		
		then:
		shape.points.get(0).toString() == "10, 20"
	}
	
	void "Test selecting a point near"() {
		given: "a shape with a single point"
		Shape shape = new Shape()
		shape.addPoint(10,10)
		
		when: "we select a point near the one poont"
		service.selectPointNear(shape, 9, 9)
		
		then:
		shape.selectedIndex == 0
	
	}
}
