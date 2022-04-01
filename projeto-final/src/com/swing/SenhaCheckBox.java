package com.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SenhaCheckBox extends JCheckBoxPadrao {
	private JPasswordField senha;
	private JTextField exibir;
	public SenhaCheckBox(JPasswordField senha, JTextField exibir) {
		super("EXIBIR SENHA");
		this.senha = senha;
		this.exibir = exibir;
		exibir.setVisible(false);
		adicionarListener();
	}
	private void adicionarListener(){
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isSelected()){
					exibir.setText(new String(senha.getPassword()));
					senha.setVisible(false);
					exibir.setVisible(true);
					if(senha.getBackground() == Color.RED){
						exibir.setBackground(Color.RED);
					} else{
						exibir.setBackground(Color.WHITE);
					}
				} else {
					senha.setText(exibir.getText());
					exibir.setVisible(false);
					senha.setVisible(true);
					if(exibir.getBackground() == Color.RED){
						senha.setBackground(Color.RED);
					} else{
						senha.setBackground(Color.WHITE);
					}
				}
			}
		});
	}
}
