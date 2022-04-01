package com.swing;

import javax.swing.JRadioButton;

public class JRadioButtonPadrao extends JRadioButton {
	public JRadioButtonPadrao(String msg) {
		setText(msg);
		setFont(ConstantesSwing.ARIAL_RADIO_BUTTON);
		setVisible(true);
		repaint();
	}
}
