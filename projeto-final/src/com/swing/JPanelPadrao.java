package com.swing;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class JPanelPadrao extends JPanel{
	public JPanelPadrao() {
		base();
	}
	public JPanelPadrao(int largura, int altura) {
		base();
		setSize(largura, altura);
	}
	private void base() {
		setLayout(null);
		setVisible(true);
	}
}
