package com.opendrill.data

import spock.lang.Specification;

class MarcherTestSpec extends Specification {

	void "Test Marcher Default Construction"() {
		when:
		Marcher marcher = new Marcher(10, 20, 0)
		
		then:
		marcher != null
		marcher.location.x == 10
		marcher.location.y == 20
		marcher.previousMarcher == null
		marcher.nextMarcher == null
		marcher.animationPoints == null
		marcher.creationIndex == 0
		marcher.displayNumber == ""
		marcher.imageLocation == "/marchers/square.gif"
		marcher.displayNumberLocation.x == 10
		marcher.displayNumberLocation.y == 20
		marcher.markBefore == 0
		marcher.markAfter == 0
	}
}
