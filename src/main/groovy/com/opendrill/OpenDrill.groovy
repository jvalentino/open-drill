package com.opendrill

import javax.swing.JFrame;

import com.opendrill.ui.main.MainView

class OpenDrill {

 
    static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Make sure we have nice window decorations.
		        JFrame.setDefaultLookAndFeelDecorated(true)
		 
		        //Create and set up the window.
		        MainView frame = new MainView()
				
				// FIXME: You are not going to want to close without prompting
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
		 
		        //Display the window.
		        frame.setVisible(true)
            }
        });
    }
}
