package com.swing;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sistema.Central;
import com.swing.telas.TelaCadastro;

public class CadastroListener implements ActionListener{
	private Window main;
	private Central central;
	public CadastroListener(Window comp, Central atual) {
		main = comp;
		central = atual;
	}
	public void actionPerformed(ActionEvent event) {
		main.dispose();
		new TelaCadastro(central);
	}
}
