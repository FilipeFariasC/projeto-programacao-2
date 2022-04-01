package com.swing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JDialog;

public class JDialogPadrao extends JDialog {
	private Component main;
	public JDialogPadrao(Component comp) {
		main = comp;
		setLocationRelativeTo(main);
		setLayout(null);
		setSize(300, 300);
		setResizable(false);
	}
	public Component getMain() {
		return main;
	}
}
