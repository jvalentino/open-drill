package com.opendrill.ui.show

import java.awt.Font;
import java.awt.image.BufferedImage;

import com.opendrill.data.Show;

class ShowModel {
	Show show
	BufferedImage marchingFieldImage
	String fontName = "Times"
	Font numberFont = new Font(fontName,Font.PLAIN,9)
}
