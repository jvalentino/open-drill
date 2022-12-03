package com.opendrill.ui.show

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JInternalFrame
import javax.swing.JPanel;

import com.opendrill.data.Show;

class ShowView extends JInternalFrame {
	static int openFrameCount = 0;
	static final int xOffset = 30, yOffset = 30;

	ShowController controller
	
	JPanel showPanel

	public ShowView(Show show) {
		super("Document #" + (++openFrameCount),
		true, //resizable
		true, //closable
		false, //maximizable
		false)//iconifiable

		controller = new ShowController(this, show)

		//...Create the GUI and put it in the window...

		//...Then set the window size or call pack...
		//
		this.showPanel = new JPanel() {
			@Override
			void paintComponent(Graphics g) {
				super.paintComponent(g)
				controller.paintComponent(g)
			}
		}
	
		//showPanel.setPreferredSize(new Dimension(100, 900))
		this.getContentPane().add(showPanel)
		
	
		//this.setPreferredSize(new Dimension(1020, 900))
		//setSize(1020,900)

		//Set the window's location.
		setLocation(xOffset*openFrameCount, yOffset*openFrameCount)

		//this.setBounds(0, 0, 100, 900)
		this.setVisible(true)
		this.setSelected(true)
		
		controller.constructionComplete()
		
		
	}

	
}
