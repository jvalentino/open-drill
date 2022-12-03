package com.opendrill.service


import com.opendrill.data.Marcher;
import com.opendrill.data.geometry.Point;
import com.opendrill.data.geometry.Shape;

import spock.lang.Specification;

class MarcherServiceTestSpec extends Specification {

	MarcherService service
	
	def setup() {
		service = new MarcherService()
	}
	
	void "Test assigning marcher number and symbol"() {
		given: "A single marcher in set 1, 2, and 3"
		Marcher m1 = new Marcher(1, 2, 0)
		m1.id = "Set One"
		
		// That the single marcher also exists in Set 2
		Marcher m2 = new Marcher(2, 3, 0)
		m1.nextMarcher = m2
		m2.previousMarcher = m1
		m2.id = "Set Two"
		
		// That the single marcher also exists in Set 3
		Marcher m3 = new Marcher(4, 5, 0)
		m2.nextMarcher = m3
		m3.previousMarcher = m2
		m3.id = "Set Three"
		
		when: "We assign that marcher in set 2 and number and symbol"
		service.assignNumberAndSymbol(m2, 3, "foobar")
		
		then: "All of these marchers should have the number 3 and the symbol foobar"
		m1.id == "Set One"
		m1.displayNumber == "3"
		m1.imageLocation == "foobar"
		
		m2.id == "Set Two"
		m2.displayNumber == "3"
		m2.imageLocation == "foobar"
		
		m3.id == "Set Three"
		m3.displayNumber == "3"
		m3.imageLocation == "foobar"
		
	}
	
	void "Test animating a marcher between two sets"() {
		given: "A single marcher in sets 1 and 2"
		
		Marcher m1 = new Marcher(0, 0, 0)
		m1.id = "Set One"
		m1.animationPoints = new Point[6]
		
		// That the single marcher also exists in Set 2
		Marcher m2 = new Marcher(1000, 1000, 0)
		m1.nextMarcher = m2
		m2.previousMarcher = m1
		m2.id = "Set Two"
		
		when: "We animate the movement for this marcher between set 1 and 2"
		service.animate(m1)
		
		then: "We expect the animation to be from top left to bottom right"
		m1.animationPoints.length == 6
		m1.animationPoints[0].toString() == "166, 166"
		m1.animationPoints[1].toString() == "333, 333"
		m1.animationPoints[2].toString() == "500, 500"
		m1.animationPoints[3].toString() == "666, 666"
		m1.animationPoints[4].toString() == "833, 833"
		m1.animationPoints[5].toString() == "1000, 1000"
		
	}
	
	void "Test animating a marcher between two sets, when marking time before"() {
		given: "A single marcher in sets 1 and 2"
		
		Marcher m1 = new Marcher(0, 0, 0)
		m1.id = "Set One"
		m1.animationPoints = new Point[6]
		m1.markBefore = 2
		
		// That the single marcher also exists in Set 2
		Marcher m2 = new Marcher(1000, 1000, 0)
		m1.nextMarcher = m2
		m2.previousMarcher = m1
		m2.id = "Set Two"
		
		when: "We animate the movement for this marcher between set 1 and 2"
		service.animate(m1)
		
		then: "We expect the animation to be from top left to bottom right, standing still for counts 1 and 2"
		m1.animationPoints.length == 6
		m1.animationPoints[0].toString() == "0, 0"
		m1.animationPoints[1].toString() == "0, 0"
		m1.animationPoints[2].toString() == "250, 250"
		m1.animationPoints[3].toString() == "500, 500"
		m1.animationPoints[4].toString() == "750, 750"
		m1.animationPoints[5].toString() == "1000, 1000"
		
	}
	
	void "Test animating a marcher between two sets, when marking time affter"() {
		given: "A single marcher in sets 1 and 2"
		
		Marcher m1 = new Marcher(0, 0, 0)
		m1.id = "Set One"
		m1.animationPoints = new Point[6]
		m1.markAfter = 2
		
		// That the single marcher also exists in Set 2
		Marcher m2 = new Marcher(1000, 1000, 0)
		m1.nextMarcher = m2
		m2.previousMarcher = m1
		m2.id = "Set Two"
		
		when: "We animate the movement for this marcher between set 1 and 2"
		service.animate(m1)
		
		then: "We expect the animation to be from top left to bottom right, standing still for counts 1 and 2"
		m1.animationPoints.length == 6
		m1.animationPoints[0].toString() == "250, 250"
		m1.animationPoints[1].toString() == "500, 500"
		m1.animationPoints[2].toString() == "750, 750"
		m1.animationPoints[3].toString() == "1000, 1000"
		m1.animationPoints[4].toString() == "1000, 1000"
		m1.animationPoints[5].toString() == "1000, 1000"
		
	}
	
	void "Test changing marcher position"() {
		given: "A single marcher in set 1, 2, and 3"
		Marcher m1 = new Marcher(0, 0, 0)
		m1.id = "Set One"
		m1.animationPoints = new Point[3]
		
		// That the single marcher also exists in Set 2
		Marcher m2 = new Marcher(500, 500, 0)
		m1.nextMarcher = m2
		m2.previousMarcher = m1
		m2.id = "Set Two"
		m2.animationPoints = new Point[3]
		
		// That the single marcher also exists in Set 3
		Marcher m3 = new Marcher(1000, 1000, 0)
		m2.nextMarcher = m3
		m3.previousMarcher = m2
		m3.id = "Set Three"
		m3.animationPoints = new Point[3]
		
		when: "We animate the marcher in set 2"
		service.changePosition(m2, 500, 500)
		
		then: "The animation is between set 1 and set 2, and set 2 and set 3"
		m1.animationPoints.length == 3
		m1.animationPoints[0].toString() == "166, 166"
		m1.animationPoints[1].toString() == "333, 333"
		m1.animationPoints[2].toString() == "500, 500"
		
		m2.animationPoints.length == 3
		m2.animationPoints[0].toString() == "666, 666"
		m2.animationPoints[1].toString() == "833, 833"
		m2.animationPoints[2].toString() == "1000, 1000"
	}
	
	void "Test Moving one marcher to another"() {
		given:
		Marcher m1 = new Marcher(0, 0, 0)
		m1.id = "Set One"
		m1.displayNumber = "1"
		m1.imageLocation = "foobar"
		
		Marcher m2 = new Marcher(1000, 1000, 0)
		m2.id = "Set Two"
		
		when:
		service.moveToMarcher(m1, m2, 2)
		
		then:
		m1.previousMarcher == null
		m1.nextMarcher.id == "Set Two"
		m2.previousMarcher.id == "Set One"
		m2.nextMarcher == null
		m2.displayNumber == "1"
		m2.imageLocation == "foobar"
		
		m1.animationPoints.length == 2
		m1.animationPoints[0].toString() == "500, 500"
		m1.animationPoints[1].toString() == "1000, 1000"
	}
	
	void "Test Follow To"() {
		given:
		ShapeService shapeService = new ShapeService()
		
		Marcher m1 = new Marcher(0, 0, 0)
		m1.id = "Set One"
		
		Marcher m2 = new Marcher(10, 10, 0)
		m1.id = "Set Two"
		
		Shape shape = new Shape()
		shape.addPoint(m1.location.x, m1.location.y)
		shape.addPoint(m2.location.x, m2.location.y)
		shapeService.fillShape(shape)
		
		when:
		service.followTo(m1, m2, 4, shape)
		
		then:
		m1.animationPoints.length == 4
		m1.animationPoints[0].toString() == "3, 3"
		m1.animationPoints[1].toString() == "6, 6"
		m1.animationPoints[2].toString() == "8, 8"
		m1.animationPoints[3].toString() == "10, 10"
	}
	
	void "Test change counts to next set"() {
		given:
		Marcher m1 = new Marcher(0, 0, 0)
		m1.animationPoints = new Point[1]
		m1.id = "Set One"
		m1.displayNumber = "1"
		m1.imageLocation = "foobar"
		
		Marcher m2 = new Marcher(1000, 1000, 0)
		m2.id = "Set Two"
		m1.nextMarcher = m2
		m2.previousMarcher = m1
		
		when:
		service.changeCountsToNextSet(m1, 2)
		
		then:
		m1.previousMarcher == null
		m1.nextMarcher.id == "Set Two"
		m2.previousMarcher.id == "Set One"
		m2.nextMarcher == null
		
		m1.animationPoints.length == 2
		m1.animationPoints[0].toString() == "500, 500"
		m1.animationPoints[1].toString() == "1000, 1000"
	}
}
