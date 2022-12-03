package com.opendrill.data.geometry

class Point {
	int x
	int y
	
	Point(int x, int y) {
		this.x = x
		this.y = y
	}
	
	@Override
	String toString() {
		return "${x}, ${y}"
	}
}
