package com.opendrill.service

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.opendrill.data.geometry.Field
import com.opendrill.data.geometry.FieldSide;
import com.opendrill.data.geometry.FieldType;
import com.opendrill.data.geometry.FieldX;
import com.opendrill.data.geometry.FieldY;
import com.opendrill.data.geometry.HashOrientation;
import com.opendrill.data.geometry.LineOrientation;
import com.opendrill.data.settings.FieldColorSetting;
import com.opendrill.util.VMath;

class FieldService {

	static final int YARD_POSITIONS = 42
	
	static final int HASH_POSITIONS = 4

	/** x coords of yard lines */
	static final int[] line = new int [YARD_POSITIONS]
	/** y coords of hash lines */
	static final int[] hashes = new int[HASH_POSITIONS]

	static final int HASH_FRONT_SIDE_LINE = 0
	static final int HASH_FRONT = 1
	static final int HASH_BACK = 2
	static final int HASH_BACK_SIDE_LINE = 3
	
	public static final int SIDE1_GOAL_YL = 0
	public static final int SIDE1_GOAL_HW = 1
	public static final int SIDE1_5_YL = 2
	public static final int SIDE1_5_HW = 3
	public static final int SIDE1_10_YL = 4
	public static final int SIDE1_10_HW = 5
	public static final int SIDE1_15_YL = 6
	public static final int SIDE1_15_HW = 7
	public static final int SIDE1_20_YL = 8
	public static final int SIDE1_20_HW = 9
	public static final int SIDE1_25_YL = 10
	public static final int SIDE1_25_HW = 11
	public static final int SIDE1_30_YL = 12
	public static final int SIDE1_30_HW = 13
	public static final int SIDE1_35_YL = 14
	public static final int SIDE1_35_HW = 15
	public static final int SIDE1_40_YL = 16
	public static final int SIDE1_40_HW = 17
	public static final int SIDE1_45_YL = 18
	public static final int SIDE1_45_HW = 19
	public static final int SIDE1_50_YL = 20
	public static final int SIDE1_50_HW = 21
	public static final int SIDE2_45_YL = 22
	public static final int SIDE2_45_HW = 23
	public static final int SIDE2_40_YL = 24
	public static final int SIDE2_40_HW = 25
	public static final int SIDE2_35_YL = 26
	public static final int SIDE2_35_HW = 27
	public static final int SIDE2_30_YL = 28
	public static final int SIDE2_30_HW = 29
	public static final int SIDE2_25_YL = 30
	public static final int SIDE2_25_HW = 31
	public static final int SIDE2_20_YL = 32
	public static final int SIDE2_20_HW = 33
	public static final int SIDE2_15_YL = 34
	public static final int SIDE2_15_HW = 35
	public static final int SIDE2_10_YL = 36
	public static final int SIDE2_10_HW = 37
	public static final int SIDE2_5_YL = 38
	public static final int SIDE2_5_HW = 39
	public static final int SIDE2_GOAL_YL = 40
	public static final int SIDE2_GOAL_HW = 41

	static {
		hashes[HASH_FRONT_SIDE_LINE] = Field.DEFAULT_FRONT_SIDE_LINE
		hashes[HASH_FRONT] = Field.DEFAULT_FRONT_HASH
		hashes[HASH_BACK] = Field.DEFAULT_BACK_HASH
		hashes[HASH_BACK_SIDE_LINE] = Field.DEFAULT_BACK_SIDE_LINE

		initField()
	}

	/** Builds the static x line coordinates */
	private static void initField() {
		//buffer line coords
		int xOffset = 30;
		int xCoord = xOffset;

		for (int i = 0; i < 42; i++) {
			line[i] = xCoord;
			xCoord += 24;
		}
	}

	/**
	 * Returns marcher's step coordinate in the x-axis.
	 * @param x
	 * @return
	 */
	String getSetCoordX(int x, boolean useQuarterSteps, FieldType type) {

		/*  Side 1
		 0 = goal	1 = goal HW
		 2 = 5 YL	3 = 5 HW
		 4 = 10 YL	5 = 10 HW
		 6 = 15 YL	7 = 15 HW
		 8 = 20 YL	9 = 20 HW
		 10 = 25 YL	11 = HW
		 12 = 30 YL	13 = HW
		 14 = 35 YL	15 = HW
		 16 = 40 YL	17 = HW
		 18 = 45 YL	19 = HW
		 20 = 50 YL	21 = HW
		 Side 2
		 22 = 45 YL
		 24 = 40 YL
		 26 = 35 YL
		 28 = 30 YL
		 30 = 25 YL
		 32 = 20 YL
		 34 = 15 YL
		 36 = 10 YL
		 38 = 5 YL
		 40 = Goal	41 = HW
		 */

		FieldX object = this.getSetCoordXObject(x, type)
		String side = object.getSideText()
		String distance = object.getStepText()

		if (useQuarterSteps) {
			distance = VMath.roundDoubleStringToQuarter(distance)
		}

		String desig = object.getOrientationText()
		String name = object.getYardText()

		String returner = side + ": " +distance + " "+desig + " " + name

		return returner

	}

	FieldX getSetCoordXObject(int x, FieldType type) {

		String name = ""
		int x2 = 0
		int yard_index = 0
		LineOrientation orientation = LineOrientation.OUTSIDE
		FieldSide sideIndex = FieldSide.SIDE_1

		if (x <= line[SIDE1_GOAL_YL]) {
			name = "Goal"
			x2 = line[SIDE1_GOAL_YL]
			yard_index = SIDE1_GOAL_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_GOAL_YL]) && (x <= line[SIDE1_GOAL_HW]) ) {
			name = "Goal"
			x2 = line[SIDE1_GOAL_YL]
			yard_index = SIDE1_GOAL_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_GOAL_HW]) && (x <= line[SIDE1_5_YL]) ) {
			name = "5"
			x2 = line[SIDE1_5_YL]
			yard_index = SIDE1_5_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_5_YL]) && (x <= line[SIDE1_5_HW]) ) {
			name = "5"
			x2 = line[SIDE1_5_YL]
			yard_index = SIDE1_5_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_5_HW]) && (x <= line[SIDE1_10_YL]) ) {
			name = "10"
			x2 = line[SIDE1_10_YL]
			//2009-07-21 Oops, x2 and yard index should always be the same
			//yard_index = SIDE1_5_HW
			yard_index = SIDE1_10_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_10_YL]) && (x <= line[SIDE1_10_HW]) ) {
			name = "10"
			x2 = line[SIDE1_10_YL]
			yard_index = SIDE1_10_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_10_HW]) && (x <= line[SIDE1_15_YL]) ) {
			name = "15"
			x2 = line[SIDE1_15_YL]
			yard_index = SIDE1_15_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_15_YL]) && (x <= line[SIDE1_15_HW]) ) {
			name = "15"
			x2 = line[SIDE1_15_YL]
			yard_index = SIDE1_15_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_15_HW]) && (x <= line[SIDE1_20_YL]) ) {
			name = "20"
			x2 = line[SIDE1_20_YL]
			yard_index = SIDE1_20_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_20_YL]) && (x <= line[SIDE1_20_HW]) ) {
			name = "20"
			x2 = line[SIDE1_20_YL]
			yard_index = SIDE1_20_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_20_HW]) && (x <= line[SIDE1_25_YL]) ) {
			name = "25"
			x2 = line[SIDE1_25_YL]
			yard_index = SIDE1_25_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_25_YL]) && (x <= line[SIDE1_25_HW]) ) {
			name = "25"
			x2 = line[SIDE1_25_YL]
			yard_index = SIDE1_25_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_25_HW]) && (x <= line[SIDE1_30_YL]) ) {
			name = "30"
			x2 = line[SIDE1_30_YL]
			yard_index = SIDE1_30_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_30_YL]) && (x <= line[SIDE1_30_HW]) ) {
			name = "30"
			x2 = line[SIDE1_30_YL]
			yard_index = SIDE1_30_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_30_HW]) && (x <= line[SIDE1_35_YL]) ) {
			name = "35"
			x2 = line[SIDE1_35_YL]
			yard_index = SIDE1_35_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_35_YL]) && (x <= line[SIDE1_35_HW]) ) {
			name = "35"
			x2 = line[SIDE1_35_YL]
			yard_index = SIDE1_35_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_35_HW]) && (x <= line[SIDE1_40_YL]) ) {
			name = "40"
			x2 = line[SIDE1_40_YL]
			yard_index = SIDE1_40_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_40_YL]) && (x <= line[SIDE1_40_HW]) ) {
			name = "40"
			x2 = line[SIDE1_40_YL]
			yard_index = SIDE1_40_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_40_HW]) && (x <= line[SIDE1_45_YL]) ) {
			name = "45"
			x2 = line[SIDE1_45_YL]
			yard_index = SIDE1_45_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_45_YL]) && (x <= line[SIDE1_45_HW]) ) {
			name = "45"
			x2 = line[SIDE1_45_YL]
			yard_index = SIDE1_45_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_45_HW]) && (x <= line[SIDE1_50_YL]) ) {
			name = "50"
			x2 = line[SIDE1_50_YL]
			yard_index = SIDE1_50_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_1
		} else if ( (x > line[SIDE1_50_YL]) && (x <= line[SIDE1_50_HW]) ) {
			name = "50"
			x2 = line[SIDE1_50_YL]
			yard_index = SIDE1_50_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE1_50_HW]) && (x <= line[SIDE2_45_YL]) ) {
			name = "45"
			x2 = line[SIDE2_45_YL]
			yard_index = SIDE2_45_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_45_YL]) && (x <= line[SIDE2_45_HW]) ) {
			name = "45"
			x2 = line[SIDE2_45_YL]
			yard_index = SIDE2_45_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_45_HW]) && (x <= line[SIDE2_40_YL]) ) {
			name = "40"
			x2 = line[SIDE2_40_YL]
			yard_index = SIDE2_40_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_40_YL]) && (x <= line[SIDE2_40_HW]) ) {
			name = "40"
			x2 = line[SIDE2_40_YL]
			yard_index = SIDE2_40_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_40_HW]) && (x <= line[SIDE2_35_YL]) ) {
			name = "35"
			x2 = line[SIDE2_35_YL]
			yard_index = SIDE2_35_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_35_YL]) && (x <= line[SIDE2_35_HW]) ) {
			name = "35"
			x2 = line[SIDE2_35_YL]
			yard_index = SIDE2_35_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_35_HW]) && (x <= line[SIDE2_30_YL]) ) {
			name = "30"
			x2 = line[SIDE2_30_YL]
			yard_index = SIDE2_30_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_30_YL]) && (x <= line[SIDE2_30_HW]) ) {
			name = "30"
			x2 = line[SIDE2_30_YL]
			yard_index = SIDE2_30_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_30_HW]) && (x <= line[SIDE2_25_YL]) ) {
			name = "25"
			x2 = line[SIDE2_25_YL]
			yard_index = SIDE2_25_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_25_YL]) && (x <= line[SIDE2_25_HW]) ) {
			name = "25"
			x2 = line[SIDE2_25_YL]
			yard_index = SIDE2_25_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_25_HW]) && (x <= line[SIDE2_20_YL]) ) {
			name = "20"
			x2 = line[SIDE2_20_YL]
			yard_index = SIDE2_20_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_20_YL]) && (x <= line[SIDE2_20_HW]) ) {
			name = "20"
			x2 = line[SIDE2_20_YL]
			yard_index = SIDE2_20_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_20_HW]) && (x <= line[SIDE2_15_YL]) ) {
			name = "15"
			x2 = line[SIDE2_15_YL]
			yard_index = SIDE2_15_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_15_YL]) && (x <= line[SIDE2_15_HW]) ) {
			name = "15"
			x2 = line[SIDE2_15_YL]
			yard_index = SIDE2_15_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_15_HW]) && (x <= line[SIDE2_10_YL]) ) {
			name = "10"
			x2 = line[SIDE2_10_YL]
			yard_index = SIDE2_10_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_10_YL]) && (x <= line[SIDE2_10_HW]) ) {
			name = "10"
			x2 = line[SIDE2_10_YL]
			yard_index = SIDE2_10_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_10_HW]) && (x <= line[SIDE2_5_YL]) ) {
			name = "5"
			x2 = line[SIDE2_5_YL]
			yard_index = SIDE2_5_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_5_YL]) && (x <= line[SIDE2_5_HW]) ) {
			name = "5"
			x2 = line[SIDE2_5_YL]
			yard_index = SIDE2_5_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		} else if ( (x > line[SIDE2_5_HW]) && (x <= line[SIDE2_GOAL_YL]) ) {
			name = "Goal"
			x2 = line[SIDE2_GOAL_YL]
			yard_index = SIDE2_GOAL_YL
			orientation = LineOrientation.INSIDE
			sideIndex = FieldSide.SIDE_2
		} else if (x > line[SIDE2_GOAL_YL]) {
			name = "Goal"
			x2 = line[SIDE2_GOAL_YL]
			yard_index = SIDE2_GOAL_YL
			orientation = LineOrientation.OUTSIDE
			sideIndex = FieldSide.SIDE_2
		}

		//distance from x position to yard line (always an integer)
		double interval = VMath.getDistance(x.intValue(),0,x2.intValue(),0)

		//always a positive step distance
		interval = VMath.convertToStep(interval)
		String distance = VMath.roundDouble(interval)

		switch (type) {
			case FieldType.SIX_MAN:
			case FieldType.SIX_MAN_FRONT:
			// attempt to convert the name to a number
				try {
					int number = Integer.parseInt(name)
					number -= 10
					if (number == 0)
						name = "Goal"
					else
						name = number+""
				} catch (NumberFormatException e) {
					name = ""
				}
				break
		}
		
		String desig = orientation.toString()
		String side = sideIndex.toString()

		FieldX object = new FieldX(sideIndex, orientation, yard_index, interval,
				side, desig, name, distance)

		return object
	}

	int convertYardToX(FieldSide side, LineOrientation orientation, int yard, double step) {

		double modifier = 1.0

		if (side == FieldSide.SIDE_1 && orientation == LineOrientation.OUTSIDE) {
			modifier = -1.0
		} else if (side == FieldSide.SIDE_2 && orientation == LineOrientation.INSIDE) {
			modifier = -1.0
		}

		//convert the step distance to interval in relation to yardline orientation
		double interval = VMath.convertStepToNumber(step) * modifier

		int position = line[yard] + ((int) interval)

		return position
	}

	int convertYardToY(HashOrientation orientation, int hash, double step) {

		double modifier = 1.0

		if (orientation == HashOrientation.BEHIND) {
			modifier = -1.0
		}

		double interval = VMath.convertStepToNumber(step) * modifier

		int position = hashes[hash] + ((int) interval)

		return position
	}

	/**
	 * Returns marcher's coordinate in the y-axis. 
	 * @param y
	 * @param useQuarterSteps
	 * @return
	 */
	String getSetCoordY(int y, boolean useQuarterSteps, Field field) {

		FieldY object = getSetCoordYObject(y, field)

		String stepText = object.getStepText()
		String orientationText = object.getOrientationText()
		String hashText = object.getHashText()

		if (useQuarterSteps) {
			stepText = VMath.roundDoubleStringToQuarter(stepText)
		}

		String returner = stepText + " "+
				orientationText + " " +
				hashText

		return returner
	}

	FieldY getSetCoordYObject(int y, Field field) {
		String distance = ""
		
		String name = ""
		int y2 = 0

		int hash_index = 0
		HashOrientation orientation = HashOrientation.INFRONT

		//mid 128

		if (y <= field.backSideLine) {
			name = "Back Side Line"
			y2 = field.backSideLine
			orientation = HashOrientation.BEHIND
			hash_index = HASH_BACK_SIDE_LINE
		} else if ( (y > field.backSideLine) && ( y <=field.backSideLineMid) ) {
			name = "Back Side Line"
			y2 = field.backSideLine
			orientation = HashOrientation.INFRONT
			hash_index = HASH_BACK_SIDE_LINE
		} else if ( ( y > field.backSideLineMid) && (y <= field.backHash) ) {
			name = "Back Hash"
			y2 = field.backHash
			orientation = HashOrientation.BEHIND
			hash_index = HASH_BACK
		} else if ( ( y > field.backHash) && (y <= field.backHashMid) ) {
			name = "Back Hash"
			y2 = field.backHash
			orientation = HashOrientation.INFRONT
			hash_index = HASH_BACK
		} else if ( ( y > field.backHashMid) && (y <= field.frontHash) ) {
			name = "Front Hash"
			y2 = field.frontHash
			orientation = HashOrientation.BEHIND
			hash_index = HASH_FRONT
		} else if ( ( y > field.frontHash) && (y <= field.frontHashMid) ) {
			name = "Front Hash"
			y2 = field.frontHash
			orientation = HashOrientation.INFRONT
			hash_index = HASH_FRONT
		} else if ( ( y > field.frontHashMid) && (y <= field.frontSideLine) ) {
			name = "Front Side Line"
			y2 = field.frontSideLine
			orientation = HashOrientation.BEHIND
			hash_index = HASH_FRONT_SIDE_LINE
		} else if ( y > field.frontSideLine)  {
			name = "Front Side Line"
			y2 = field.frontSideLine
			orientation = HashOrientation.INFRONT
			hash_index = HASH_FRONT_SIDE_LINE
		}
		
		String desig = orientation.toString()

		double interval = VMath.getDistance(0,y,0,y2)
		interval = VMath.convertToStep(interval)
		distance = VMath.roundDouble(interval)

		FieldY object = new FieldY(orientation, hash_index, interval, desig, name, distance)

		return object

	}
	
	BufferedImage generateMarchingField(Field field) {
		double magnitude = field.magnitude
		int w = (int) Math.round(1020 * magnitude);
		int h = (int) Math.round(600 * magnitude);
		
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
	  
		Graphics2D g = image.createGraphics()
		
		RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
		g.setRenderingHints(renderHints)

		int fieldOffsetX = Field.FIELD_OFFSET;
		int fieldOffsetY = Field.FIELD_OFFSET;
		int scaledOffsetY = (int) Math.round(fieldOffsetY * magnitude);
		
		switch (field.type) {
		case FieldType.SIX_MAN:
		case FieldType.SIX_MAN_FRONT:
			fieldOffsetX += 96;
			break;
		}
		
		//field offset
		int x1 = (int) Math.round(fieldOffsetX * magnitude);
		int y1 = (int) Math.round(field.backSideLine * magnitude);

		//maximum x position of lines
		int fieldLimitX = Field.STANDARD_FIELD_WIDTH + Field.FIELD_OFFSET;
		switch (field.type) {
		case FieldType.SIX_MAN:
		case FieldType.SIX_MAN_FRONT:
			fieldLimitX = 768;
			break;
		}
		int maxX = (int) Math.round(fieldLimitX * magnitude);

		//maximum y position of lines (was 504)
		int maxY = (int) Math.round((field.frontSideLine - fieldOffsetY) * magnitude);
		
		//the distance between vertical lines
		int vert = (int) Math.round(48 * magnitude);

		int frontHash = (int) Math.round(field.frontHash * magnitude);
		int backHash = (int) Math.round(field.backHash * magnitude);

		int twenty4 = (int) Math.round(24 * magnitude);

		int three = (int) Math.round(3 * magnitude);

		//draw a white rectangle in the background
		g.setColor(Color.white);
		g.fillRect(0,0,w, h);

		
		g.setColor(VMath.stringToColor(field.settings.getSolidYardLineColor()));
		//solid vertical lines, yard lines (every 5 yards), starting on goal line
		for (int i = x1; i <= maxX+x1; i +=vert) {
			g.drawLine(i,y1,i, maxY+scaledOffsetY);
		}
		
		g.setColor(VMath.stringToColor(field.settings.getDashedYardLineColor()));
		//dashed vertical lines, in between yard lines (every 2.5 yards)
		for (int i = twenty4+x1; i < maxX+x1; i +=vert) {
			for (int j = y1; j < maxY+scaledOffsetY; j+=three) {
				g.drawRect(i,j,1,1);
			}
		}

		//dashed horizontal lines, every 2.5 yards
		for (int i = x1; i < maxX+x1; i +=three) {
			for (int j = twenty4+y1; j < maxY+scaledOffsetY; j+=twenty4) {
				g.drawRect(i,j,1,1);
			}
		}

		g.setColor(VMath.stringToColor(field.settings.getSolidYardLineColor()));
		//backside line
		g.drawLine(x1,y1,maxX+x1,y1);
		//frontside line
		g.drawLine(x1,maxY+scaledOffsetY,maxX+x1,maxY+scaledOffsetY);

		
		List<String> line = new ArrayList<String>();
		switch (field.type) {
		case FieldType.SIX_MAN:
		case FieldType.SIX_MAN_FRONT:
			//line.add("");
			//line.add("");
			line.add("G");
			line.add("5");
			line.add("10");
			line.add("15");
			line.add("20");
			line.add("25");
			line.add("30");
			line.add("35");
			line.add("40");
			line.add("35");
			line.add("30");
			line.add("25");
			line.add("20");
			line.add("15");
			line.add("10");
			line.add("5");
			line.add("G");
			//line.add("");
			//line.add("");
			break;
		default:
			line.add("G");
			line.add("5");
			line.add("10");
			line.add("15");
			line.add("20");
			line.add("25");
			line.add("30");
			line.add("35");
			line.add("40");
			line.add("45");
			line.add("50");
			line.add("45");
			line.add("40");
			line.add("35");
			line.add("30");
			line.add("25");
			line.add("20");
			line.add("15");
			line.add("10");
			line.add("5");
			line.add("G");
		}

		//draw yard line numbers above back side line and bellow front side line
		g.setColor(VMath.stringToColor(field.settings.getSolidYardLineColor()));
		int count = 0;
		for (int i = x1; i <= maxX+x1; i+= vert) {
			if (count >= line.size())
				continue;
			g.drawString(line.get(count),i-5,y1-5);
			g.drawString(line.get(count),i-5,maxY+scaledOffsetY+15);
			count++;
		}

		//draw hashes right of yard lines
		g.setColor(VMath.stringToColor(field.settings.getHashColor()));
		for (int i = x1; i < maxX+x1; i+=vert) {
			//back hash
			g.drawLine(i,backHash-1,i+12,backHash-1);
			g.drawLine(i,backHash,i+12,backHash);
			g.drawLine(i,backHash+1,i+12,backHash+1);

			//front hash
			g.drawLine(i,frontHash-1,i+12,frontHash-1);
			g.drawLine(i,frontHash,i+12,frontHash);
			g.drawLine(i,frontHash+1,i+12,frontHash+1);
		}

		//draw hashes left of yard line
		for (int i = x1+vert; i <= maxX+x1; i+=vert) {
			g.drawLine(i,backHash-1,i-12,backHash-1);
			g.drawLine(i,backHash,i-12,backHash);
			g.drawLine(i,backHash+1,i-12,backHash+1);

			g.drawLine(i,frontHash-1,i-12,frontHash-1);
			g.drawLine(i,frontHash,i-12,frontHash);
			g.drawLine(i,frontHash+1,i-12,frontHash+1);
		}

		
		return image
	}
}
