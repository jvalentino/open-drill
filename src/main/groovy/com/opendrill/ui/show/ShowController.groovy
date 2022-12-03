package com.opendrill.ui.show

import groovy.util.logging.Log4j;

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

import com.opendrill.data.Show
import com.opendrill.ui.common.BaseController

@Log4j
class ShowController extends BaseController {

	ShowView view
	ShowModel model
	
	ShowController(ShowView view, Show show) {
		this.view = view
		this.model = new ShowModel()
		this.model.show = show
	}
	
	@Override
	void constructionComplete() {
		model.marchingFieldImage = bus.getFieldService().generateMarchingField(this.model.getShow().getField())
				
		this.view.setSize(model.marchingFieldImage.getWidth(), model.marchingFieldImage.getHeight())
		
	}
	
	void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g
		
		//draw the background of the entire window as white
		g.setColor(Color.white)
		g.fillRect(0,0, view.getSize().getWidth().intValue(), view.getSize().getHeight().intValue())

		//set the default color as black
		g.setColor(Color.black)

		//7-29-2006 Changed to apply to g instead of this
		g.setFont(model.numberFont)

		//if the field has not been drawn do so...
		if (model.marchingFieldImage != null) {
			g.drawImage(model.marchingFieldImage,0,0, this.view.showPanel)
		}

		//this is for the fucking mac, who likes to anti alias every fucking thing
		RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF)
		g2d.setRenderingHints(renderHints)
	}

}
