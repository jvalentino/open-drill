package com.opendrill.util

import java.awt.Color;

import com.opendrill.data.geometry.Point;
import java.awt.geom.Point2D;

import spock.lang.Specification;

class VMathTestSpec extends Specification {

	void "Test get distance"() {
		given:
		int x0 = 0
		int y0 = 10
		int x1 = 22
		int y1 = 33
		
		when:
		double result = VMath.getDistance(x0, y0, x1, y1)
		
		then:
		result == 31.827660925679098
	}
	
	void "Test get distance with doubles"() {
		given:
		double x0 = 0.0
		double y0 = 10.0
		double x1 = 22.0
		double y1 = 33.0
		
		when:
		double result = VMath.getDistance(x0, y0, x1, y1)
		
		then:
		result == 31.827660925679098
	}
	
	void "Test get slope"() {
		given:
		Point one = new Point(0,0)
		Point two = new Point(10,10)
		
		when:
		Point result = VMath.getSlope(one, two)
		
		then:
		result.toString() == "10, 10"
	}
	
	void "Test Slope X"() {
		given:
		int x1 = 10
		int x2 = 100
		
		when:
		int result = VMath.getSlopeX(x1, x2)
		
		then:
		result == 90
	}
	
	void "Test Slope Y"() {
		given:
		int y1 = 10
		int y2 = 100
		
		when:
		int result = VMath.getSlopeY(y1, y2)
		
		then:
		result == 90
	}
	
	void "Test create fractional slope"() {
		given:
		Point slope = new Point(100,100)
		double value = 10
		
		when:
		Point2D.Double result = VMath.createFractionalSlope(slope, value)
		
		then:
		result.x == 10.0
		result.y == 10.0
	}
	
	void "Test get largest integer"() {
		given:
		int a = -9
		int b = 9
		
		when:
		int result = VMath.returnLargestInt(a, b)
		
		then:
		result == 9
	}
	
	void "Test get fractional slope"() {
		given:
		Point slope = new Point(7,8)
		
		when:
		Point2D.Double result = VMath.getFractionalSlope(slope)
		
		then:
		result.x == 0.875
		result.y == 1.0
	}
	
	void "Test convert to decimal point"() {
		given:
		Point point = new Point(8,7)
		
		when:
		Point2D.Double result = VMath.convertToDPoint(point)
		
		then:
		result.x == 8.0
		result.y == 7.0
	}
	
	void "Test to String for fractional points"() {
		given:
		Point2D.Double point = new Point2D.Double(9.5, 6.7)
		
		when:
		String result = VMath.toString(point)
		
		then:
		result == "9.5, 6.7"
	}
	
	void "Test rounding a double to a quarter string"() {
		when:
		String string = VMath.roundDoubleStringToQuarter(value)
		
		then:
		string == result
		
		where:
		value	|| result
		"9.0"	|| "9.0"
		"9.1"	|| "9.0"
		"9.2"	|| "9.25"
		"9.3"	|| "9.25"
		"9.4"	|| "9.5"
		"9.5"	|| "9.5"
		"9.6"	|| "9.5"
		"9.7"	|| "9.75"
		"9.8"	|| "9.75"
		"9.9"	|| "10.0"
		"foo"	|| ""
	}
	
	void "Test rounding a double"() {
		when:
		String c = VMath.roundDouble(value)
		
		then:
		c == result
		
		where:
		value	|| result
		0		|| "0.0"
		0.1		|| "0.1"
		0.12	|| "0.12"
		0.123	|| "0.12"
		0.125	|| "0.13"
		0.126	|| "0.13"
		10.126	|| "10.13"
		100.126	|| "100.13"
	}
	
	void "Test convert to step"() {
		when:
		double c = VMath.convertToStep(value)
		String rounded = VMath.roundDouble(c)
		
		then:
		rounded == result
		
		where:
		value	|| result
		1.0		|| "0.17"
		1.1		|| "0.18"
		1.5		|| "0.25"
		1.6		|| "0.27"
	}
	
	void "Test convert step to number"() {
		when:
		double c = VMath.convertStepToNumber(value)
		String rounded = VMath.roundDouble(c)
		
		then:
		rounded == result
		
		where:
		value	|| result
		1.0		|| "6.0"
		1.1		|| "6.6"
		1.2		|| "7.2"
	}
	
	void "Test getting smallest slope interval"() {
		given:
		Point slope = new Point(x, y)
		
		when:
		Point result = VMath.getSmallestSlopeInterval(slope)
		
		then:
		result.x == rx
		result.y == ry
		
		where:
		x	| y		|| rx	| ry
		0	| 1		|| 0	| 1
		1	| 0		|| 1	| 0
		50	| 60	|| 0	| 1
		60	| 50	|| 1	| 1
		10	| 255	|| 0	| 1
	}
	
	void "Test GCD"() {
		when:
		int result = VMath.GCD(a, b)
		
		then:
		result == gcd
		
		where:
		a	| b		|| gcd
		0	| 1		|| 1
		1	| 0		|| 1
		10	| 8		|| 8
		100	| 15	|| 15
		100	| 20	|| 20
	}
	
	void "Test In Bounds"() {
		when:
		boolean c = VMath.inBounds(max, value)
		
		then:
		result == c
		
		where:
		max	| value	|| result
		10	| 3		|| true
		10	| -1	|| false
		10	| 11	|| false
	}
	
	void "Test string to color"() {
		when:
		Color result = VMath.stringToColor(value)
		
		then:
		color == result
		
		where:
		value		| color
		"#00ff00" 	| Color.GREEN
		"#ff0000" 	| Color.RED
		
	}
	
	void "Test color to string"() {
		when:
		String result = VMath.colorToString(color)
		
		then:
		result == text
		
		where:
		color		| text
		Color.RED	| "#ff0000"
		Color.GREEN	| "#00ff00"
	}
	
}
