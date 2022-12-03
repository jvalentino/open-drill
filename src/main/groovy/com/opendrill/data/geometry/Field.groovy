package com.opendrill.data.geometry

import com.opendrill.data.settings.FieldColorSetting;

class Field {


	static final int FIELD_OFFSET = 30

	static final int STANDARD_FRONT_HASH = 342
	static final int STANDARD_BACK_HASH = 222
	static final int SIX_MAN_FRONT_HASH = 270 + 60
	static final int SIX_MAN_BACK_HASH = 174 + 60
	static final int SIX_MAN_FRONT_HASH_FRONT = 270 + 120
	static final int SIX_MAN_BACK_HASH_FRONT = 174 + 120
	
	static final int STANDARD_FIELD_WIDTH = 930
	
	static final int DEFAULT_FRONT_SIDE_LINE = 534
	static final int DEFAULT_FRONT_HASH = 342
	static final int DEFAULT_BACK_HASH = 222
	static final int DEFAULT_BACK_SIDE_LINE = 30
	
	/** is the location of the default front hash line (342) */
	int frontHash = DEFAULT_FRONT_HASH
	/** is the location of the default back hash line (222) */
	int backHash = DEFAULT_BACK_HASH
	/** is the location of the back side line  */
	int backSideLine = DEFAULT_BACK_SIDE_LINE
	/** is the location of the front side line */
	int frontSideLine = DEFAULT_FRONT_SIDE_LINE
	/** is the default midway point between back side line and back hash */
	int backSideLineMid = (backSideLine + backHash)/2
	/** is the midway point between front hash and back hash */
	int backHashMid = (frontHash + backHash) /2
	/** is the midway point between front hash and front side line */
	int frontHashMid = (frontHash + frontSideLine) /2
	
	FieldColorSetting settings = new FieldColorSetting()
	
	FieldType type = FieldType.STANDARD
	
	double magnitude = 1.0
	
	void updateHashes(int newFrontHash, int newBackHash) {
		frontHash = newFrontHash
		backHash = newBackHash
		updateMidPoints()
	}
	
	void updateSideLines(int newFrontSideLine, int newBackSideLine) {
		frontSideLine = newFrontSideLine
		backSideLine = newBackSideLine
		updateMidPoints()
	}
	
	void updateMidPoints() {
		backSideLineMid = (backSideLine + backHash)/2
		backHashMid = (frontHash + backHash) /2
		frontHashMid = (frontHash + frontSideLine) /2
	}
	
}
