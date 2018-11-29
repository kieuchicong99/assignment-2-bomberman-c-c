package uet.oop.bomberman.gui.menu;

import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.gui.InfoDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Help extends JMenu {

	public Help(Frame frame)  {
		super("Help");
		
		/*
		 * How to play
		 */
		JMenuItem instructions = new JMenuItem("How to play");
		instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		instructions.addActionListener(new MenuActionListener(frame));
		add(instructions);
		
		/*
		 * Credits
		 */
		JMenuItem about = new JMenuItem("About");
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		about.addActionListener(new MenuActionListener(frame));
		add(about);
		
	}
	
	class MenuActionListener implements ActionListener {
		public Frame _frame;
		public MenuActionListener(Frame frame) {
			_frame = frame;
		}
		
		  @Override
		public void actionPerformed(ActionEvent e) {
			  
			  if(e.getActionCommand().equals("How to play")) {
				  new InfoDialog(_frame, "How to Play", "Moves: W,A,S,D or UP,DOWN, RIGHT, LEFT\nPut Bombs: SPACE, X\nUse Items for increate power:\n        Increate Flame Segment\n        Increate speed bomberman\n        Increate amount of bomb puted ", JOptionPane.QUESTION_MESSAGE);
			  }
				  
			  if(e.getActionCommand().equals("About")) {
				  new InfoDialog(_frame, "About", "Version: " + "1.0.0" + "\n Author: \n          Trần Mạnh Cường \n          Kiều Chí Công\n", JOptionPane.INFORMATION_MESSAGE);
			  }
			  
		  }
	}
}
