package com.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JButtonPadrao extends JButton{
	public JButtonPadrao(String nome) {
		setText(nome);
		setSize(200, 25);
		setOpaque(true);
		setVisible(true);
	}
}
