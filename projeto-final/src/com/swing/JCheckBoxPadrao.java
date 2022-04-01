package com.swing;

import javax.swing.JCheckBox;

public class JCheckBoxPadrao extends JCheckBox{
	public JCheckBoxPadrao(String msg) {
		super(msg);
		base();
	}
	public JCheckBoxPadrao(String msg, String dica) {
		super(msg);
		base();
		setToolTipText(dica);
	}
	public void base(){
		setFont(ConstantesSwing.TIMES_NEW_ROMAN_CHECK_BOX);
		setSize(100, 25);
		setVisible(true);
	}
}
