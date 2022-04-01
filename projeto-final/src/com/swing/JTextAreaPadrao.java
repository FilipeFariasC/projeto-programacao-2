package com.swing;

import javax.swing.JTextArea;

public class JTextAreaPadrao extends JTextArea {
	public JTextAreaPadrao(String txt) {
		setText(txt);
		setFont(ConstantesSwing.TIMES_TEXTO);
		setSize(200, 200);
		setVisible(true);
	}
}
