package com.swing;

import javax.swing.JTextField;

public class JTextFieldPadrao extends JTextField{
	public JTextFieldPadrao(String msg){
		setText(msg);
		base();
	}
	public JTextFieldPadrao(String msg, String dica) {
		setText(msg);
		setToolTipText(dica);
		base();
	}
	private void base(){
		setFont(ConstantesSwing.TIMES_TEXTO);
		setHorizontalAlignment(CENTER);
		setVisible(true);
	}
}
