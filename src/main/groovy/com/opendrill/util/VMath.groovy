package com.opendrill.util

import java.awt.Color;
import java.awt.geom.Point2D;
import java.math.RoundingMode;
import java.util.StringTokenizer;

import com.opendrill.data.geometry.Point;



class VMath {

	static double getDistance(int x0, int y0, int x1, int y1) {
		return Math.sqrt(Math.pow(x1-x0,2) + Math.pow(y1-y0,2))
	}
	
	static double getDistance(double x0, double y0, double x1, double y1) {
		return Math.sqrt(Math.pow(x1-x0,2) + Math.pow(y1-y0,2))
	}
	
	static Point getSlope(Point one, Point two) {
		int xSlope = getSlopeX(one.x, two.x)
		int ySlope = getSlopeY(one.y, two.y)
		Point point = new Point(xSlope, ySlope)
		return point
	}
	
	/** Returns the x slope between the two given x coordinates */
	static int getSlopeX(int x1, int x2) {
		return (x2 - x1)
	}

	/** Returns the y slope between the two given y coorindates */
	static int getSlopeY(int y1, int y2) {
		return (y2 - y1)
	}
	
	/**
	 * Takes a given slope, change in x over change in y, and dived both x and y
	 * by the given value, and returns it as a decimal coorindate
	 * @param slope
	 * @param value
	 * @return
	 */
	static Point2D.Double createFractionalSlope(Point slope, double value) {
		 double changeX = ((double)slope.x) / value
		 double changeY = ((double)slope.y) / value
		 return (new Point2D.Double(changeX,changeY))
	 }
	
	/**
	 * Returns the absolute value of the number that has the largest absolute value 
	 * @param a
	 * @param b
	 * @return
	 */
	static int returnLargestInt(int a, int b) {
		if (Math.abs(a) >= Math.abs(b)) 
			return Math.abs(a)
		else 
			return Math.abs(b)
	}

	/**
	 * Returns a fractional value of the give slope, where the highest x or y value
	 * is made to 1, and the corresponding x or y value is a fraction.
	 * @param slope
	 * @return
	 */
	static Point2D.Double getFractionalSlope(Point slope) {
		double changeX = (double) ((double)slope.x) / ((double)returnLargestInt(slope.x,slope.y))
		double changeY = (double) ((double)slope.y) / ((double)returnLargestInt(slope.x,slope.y))
		return (new Point2D.Double(changeX,changeY))
	}
	
	/**
	 * Converts a point to a DPoint
	 * @param point
	 * @return
	 */
	static Point2D.Double convertToDPoint(Point point) {
		Point2D.Double dPoint = new Point2D.Double((double)point.x,(double)point.y)
		return dPoint
	}
	
	static String toString(Point2D.Double point) {
		return point.x + ", " + point.y
	}
	
	/**
	 * Rounds the given string double to the nearest quarter. If not a value number returns an empty string.
	 * @param value
	 * @return
	 */
	static String roundDoubleStringToQuarter(String value) {
		String result = ""
		
		float floatValue = 0f
		
		try {
			floatValue = Float.parseFloat(value)
		} catch (NumberFormatException e) {
			return result
		}
		
		float coeff = 4f
		
		float rounded = Math.round(floatValue*coeff)/coeff
		
		result = rounded + ""
		
		return result
	}
	
	/**
	 * Rounds the given double to 2 decimal places and returns it as a string
	 * @param d
	 * @return
	 */
	static String roundDouble(double d) {
		BigDecimal bd = new BigDecimal(d)
		bd = bd.setScale(2, RoundingMode.HALF_UP)
		return bd.doubleValue().toString()
	}
	
	/**
	 * converts the given pixel interval to step size 
	 * @param c
	 * @return
	 */
	static double convertToStep(double c) {
		return ((c) * (1.0/6.0))
	}
	
	/**
	 * Converts the given double to a step size 
	 * @param s
	 * @return
	 */
	static double convertStepToNumber(double s) {
		return ((s/(1.0/6.0)))
	}
	
	/**
	 * Given the slope as change in x over change in y, find the smallest slope interval
	 * by dividing the x and y by their GCD. This is used to find the next closest point
	 * along a line given the origin.
	 * @param slope
	 * @return
	 */
	static Point getSmallestSlopeInterval(Point slope) {
		 if (slope.x == 0) return (new Point(slope.x,1))
		 else if (slope.y ==0) return (new Point(1,slope.y))
		 else {
			 int gcd = GCD(slope.x,slope.y)
			 return (new Point( (int)(slope.x/gcd), (int)(slope.y/gcd) ))
		 }
	 }
	
	/**
	 * Returns the gcd of the two given numbers
	 * @param a
	 * @param b
	 * @return
	 */
	static int GCD(int a ,int b){
		if (a == 0 || b ==0 ) return returnLargestInt(a,b)
		else {
			int myGCD=0
			int c = a - (a/b)*b
			if(c==0) myGCD = b
			else myGCD = GCD(b,c)
			return myGCD
		}
	}
	
	/**
	 * Returns true if -1 < value < max
	 * @param max
	 * @param value
	 * @return
	 */
	static boolean inBounds(int max, int value) {
		if (-1 < value && value < max) return true
		else return false
	}
	
	static Color stringToColor(String value) {
		Color color = Color.black
		try {
			color = Color.decode(value)
		} catch (Exception e) {
			e.printStackTrace()
		}
		return color
	}
	
	static String colorToString(Color color) {
		String rgb = Integer.toHexString(color.getRGB())
		rgb = rgb.substring(2, rgb.length())
		return "#"+rgb
	}

}
