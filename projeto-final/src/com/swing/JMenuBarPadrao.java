package com.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;

import javax.swing.JMenuBar;

public class JMenuBarPadrao extends JMenuBar {
	private Window main;
	public JMenuBarPadrao(Window comp) {
		main = comp;
		setSize(comp.getWidth(), 20);
		setOpaque(true);
		setVisible(true);
	}
	public Window getMain() {
		return main;
	}
}
