package com.swing;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

public class JFramePadrao extends JFrame{
	public JFramePadrao(){
		setTitle(ConstantesSwing.TAPIOCA_MAKER);
		setSize(640, 360);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(ConstantesSwing.TAPIOCA_1.getImage());
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}