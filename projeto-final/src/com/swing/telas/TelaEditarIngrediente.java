package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.excecoes.StringVaziaException;
import com.excecoes.ValorAbaixoDeZeroException;
import com.sistema.*;
import com.swing.*;
import com.swing.paineis.EditarIngredientePanel;

public class TelaEditarIngrediente extends JFramePadrao {
	private ArrayList<Ingrediente> ingredientes;
	private JTabbedPanePadrao abas;
	private Central central;
	public TelaEditarIngrediente(Central central, ArrayList<Ingrediente> ingredientes){
		this.ingredientes = ingredientes;
		this.central = central;
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(350, 350);
		base();
		setVisible(true);
	}
	private void base() {
		adicionarScroll();
		adicionarPainel();
	}

	private String abaNome(Ingrediente i){
		return String.format("%s - %s - R$ %.2f - %.2f cal", 
				i.getNome(), i.getDisponibilidade().toString(), 
				i.getPrecoPorGrama(), i.getValorCalorico());
	}
	private void adicionarScroll(){
		abas = new JTabbedPanePadrao(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		add(abas, BorderLayout.CENTER);
	}
	private void adicionarPainel() {
		for(Ingrediente i: ingredientes){
			String txt = abaNome(i);
			EditarIngredientePanel painel = new EditarIngredientePanel(central, i, abas, this);
			abas.addTab(txt, painel);
		}
	}
}
