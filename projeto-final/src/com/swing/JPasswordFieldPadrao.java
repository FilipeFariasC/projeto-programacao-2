package com.swing;

import javax.swing.JPasswordField;

public class JPasswordFieldPadrao extends JPasswordField {
	public JPasswordFieldPadrao(String dica) {
		setToolTipText(dica);
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVisible(true);
	}
}
