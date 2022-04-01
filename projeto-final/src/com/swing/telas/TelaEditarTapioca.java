package com.swing.telas;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.sistema.Central;
import com.sistema.Ingrediente;
import com.sistema.Tapioca;
import com.swing.JFramePadrao;
import com.swing.JTabbedPanePadrao;
import com.swing.paineis.EditarIngredientePanel;
import com.swing.paineis.EditarTapiocaPanel;

public class TelaEditarTapioca extends JFramePadrao{
	private Central central;
	private ArrayList<Tapioca> tapiocas;
	private JTabbedPanePadrao abas;

	public TelaEditarTapioca(Central central, ArrayList<Tapioca> tapiocas) {
		this.central = central;
		this.tapiocas = tapiocas;
		
		base();
		setVisible(true);
	}
	private void base() {
		setSize(840, 520);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		adicionarScroll();
		adicionarPainel();
	}
	private String abaNome(Tapioca tapioca){
		return String.format("%s - %s - R$ %.2f - %.2f cal", 
				tapioca.getNome(), tapioca.getDisponibilidade().toString(), 
				tapioca.getPreco(), tapioca.getValorCalorico());
	}
	private void adicionarScroll(){
		abas = new JTabbedPanePadrao(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		add(abas, BorderLayout.CENTER);
	}
	private void adicionarPainel() {
		for(Tapioca i: tapiocas){
			String txt = abaNome(i);
			EditarTapiocaPanel painel = new EditarTapiocaPanel(central, i, abas, this);
			abas.addTab(txt, painel);
		}
	}
}
