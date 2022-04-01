package com.swing;

import java.awt.Font;

import javax.swing.JLabel;

public class JLabelPadrao extends JLabel {
	public JLabelPadrao(String msg) {
		super(msg);
		base();
	}
	public JLabelPadrao(String msg, String dica) {
		super(msg);
		base();
		setToolTipText(dica);
	}
	public JLabelPadrao(String msg, Font fonte) {
		super(msg);
		base();
		setFont(fonte);
	}
	private void base(){
		setVisible(true);
		setFont(ConstantesSwing.TIMES_TEXTO);
		setOpaque(true);
		setVisible(true);
	}
}
