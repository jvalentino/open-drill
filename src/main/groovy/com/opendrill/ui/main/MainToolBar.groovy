package com.opendrill.ui.main

import javax.swing.JButton
import javax.swing.JToolBar

import com.opendrill.ui.common.MoreButton;

class MainToolBar extends JToolBar {
	
	
	
	MainToolBar() {
		
		for (int i = 0; i < 30; i++) {
			JButton button = new JButton("" + i)
			this.add(button)
		}
		
	}
	
	

}
