package com.opendrill.data

class Segment {

	/** Represents the marcher in this segment */
	List<Marcher> marchers = []
	/** Represents the type that this segment is. The type can be:<br />
	* block <br />
	* line<br />
	* curve<br />
	*/
	SegmentType type
	
	String id = UUID.randomUUID().toString()

	/** Creates a segment, which consits of a group of marchers */
	public Segment(SegmentType type) {
		this.type = type
	}
}
