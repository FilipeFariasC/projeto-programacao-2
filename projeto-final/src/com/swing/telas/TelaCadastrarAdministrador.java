package com.swing.telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sistema.Central;
import com.swing.*;

public class TelaCadastrarAdministrador extends JFramePadrao{
	public TelaCadastrarAdministrador(Central atual) {
		setSize(400,150);
		JButtonPadrao cadastrarADM = new JButtonPadrao("CADASTRAR ADMINISTRADOR");
		cadastrarADM.setBounds(75, 40, 250, 25);
		cadastrarADM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new TelaCadastro(atual);
			}
		});
		add(cadastrarADM);
		setVisible(true);
	}
}
