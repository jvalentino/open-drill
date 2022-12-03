package com.opendrill.ui.main

import groovy.util.logging.Log4j

import java.awt.Dimension
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.opendrill.data.Show;
import com.opendrill.service.ServiceBus;
import com.opendrill.ui.common.BaseController;
import com.opendrill.ui.show.ShowView

@Log4j
class MainController extends BaseController {

	MainView view
		
	MainController(MainView view) {
		this.view = view
	}
		
	void constructionComplete() {
		MainController me = this
		
		view.newItem.setMnemonic(KeyEvent.VK_N)
		view.newItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK))
		
		view.newItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				me.newItemSelected()
			}
		})
		
		this.stretchFrame()
	}
	
	void stretchFrame() {
		int inset = 50
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize()
		int width = screenSize.width  - inset * 2
		int height = screenSize.height - inset * 2
		view.setBounds(inset, inset, width, height)
	}
	
	void newItemSelected() {
		Show show = new Show()
		ShowView frame = new ShowView(show)
		view.desktop.add(frame)
		
	}
}
