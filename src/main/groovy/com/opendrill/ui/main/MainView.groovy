package com.opendrill.ui.main

import java.awt.BorderLayout;
import java.awt.Dimension
import java.awt.Toolkit

import javax.swing.JDesktopPane
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JScrollPane

import com.opendrill.ui.common.MDIDesktopPane;
import com.opendrill.ui.common.MoreButton;


class MainView extends JFrame {

	MainController controller
	
	MainToolBar toolbar = new MainToolBar()
	
	MDIDesktopPane desktop = new MDIDesktopPane()
	JMenuBar menu = new JMenuBar()
	JMenu fileMenu = new JMenu("File")
	JMenuItem newItem = new JMenuItem("New")
	JMenuItem openItem = new JMenuItem("Open")
	
	MainView() {
		controller = new MainController(this)
		
		this.constuct()
		
		controller.constructionComplete()
	}
	
	void constuct() {
		this.getContentPane().setLayout(new BorderLayout())
				
		
		this.getContentPane().add(toolbar, BorderLayout.NORTH)
		
		JScrollPane pane = new JScrollPane(desktop)
		this.getContentPane().add(pane, BorderLayout.CENTER)
		
		setJMenuBar(menu)
		
		menu.add(fileMenu)
		fileMenu.add(newItem)
		fileMenu.add(openItem)
 
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE)
	}
}
