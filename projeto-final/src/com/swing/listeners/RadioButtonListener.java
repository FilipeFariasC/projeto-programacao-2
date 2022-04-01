package com.swing.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.swing.JRadioButtonPadrao;
import com.swing.Pesquisavel;

public class RadioButtonListener  implements ActionListener{
	private JRadioButtonPadrao botaoUm,
							   botaoDois;
	private Pesquisavel pesquisar;
	public RadioButtonListener(JRadioButtonPadrao botaoUm, 
							   JRadioButtonPadrao botaoDois, 
							   Pesquisavel pesquisar) {
		this.botaoUm = botaoUm;
		this.botaoDois = botaoDois;
		this.pesquisar = pesquisar;
		botaoUm.addActionListener(this);
		botaoDois.addActionListener(this);
	}
	public void actionPerformed(ActionEvent event) {
		String txt = event.getActionCommand();
		if(txt.equals("DISPONÍVEL") ||
		   txt.equals("ENTREGUE")){
			botaoDois.setSelected(false);
		} else{
			botaoUm.setSelected(false);
		}
		if(pesquisar != null){
			pesquisar.pesquisar();
		}
	}
}
