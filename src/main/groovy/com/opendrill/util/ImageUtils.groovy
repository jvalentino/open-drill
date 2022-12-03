package com.opendrill.util

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO

import groovy.util.logging.Log4j

@Log4j
class ImageUtils {

	static void bufferedImageToFileAsPng(File file, BufferedImage image) {
		try {
			ImageIO.write(image, "png", file)
		} catch (IOException e) {
			log.error("Could not write this image to file", e)
		}
	}
}
