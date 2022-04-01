package com.swing.paineis;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.sistema.Central;
import com.sistema.Cliente;
import com.sistema.Disponibilidade;
import com.sistema.Persistencia;
import com.sistema.Pessoa;
import com.sistema.Tapioca;
import com.swing.telas.TelaListarIngrediente;
import com.swing.*;
import com.swing.listeners.RadioButtonListener;

public class ListarTapiocaCardapioPanel extends JPanelPadrao implements Refreshable, Pesquisavel{
	private DefaultTableModelPadrao modelo;
	private JTable table;
	private JScrollPane scroll;
	private ArrayList<Tapioca> tapiocas;
	private JButtonPadrao listar;
	private Component main;
	private JPanelPadrao painel;
	private JPanelPadrao painelPesquisar;
	private JTextFieldPadrao buscador;
	private JRadioButtonPadrao disponivel,
							indisponivel;
	private Pessoa pessoa;
	private Central central;
	public ListarTapiocaCardapioPanel(Central central, Pessoa pessoa, Component main){
		this.central = central;
		this.pessoa = pessoa;
		this.tapiocas = central.getTapiocas();
		this.main = main;
		setLayout(new BorderLayout());
		base();
	}
	private class ListarIngredienteListener implements ActionListener {
		private Component main;
		public ListarIngredienteListener(Component main){
			this.main = main;
		}
		public void actionPerformed(ActionEvent e) {	
			int[] escolha = table.getSelectedRows();
			if(escolha.length > 0){
				ArrayList<Tapioca> t = new ArrayList<>();
				for(int c = 0; c < escolha.length; c++){
					String esc = table.getValueAt(escolha[c], 0).toString();
					for(Tapioca tap: tapiocas){
						if (tap.getNome().equals(esc)){
							t.add(tap);
						}
					}
				}
				new TelaListarIngrediente(central, t, main);
			} else{
				JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UMA(1) LINHA", null, 0, ConstantesSwing.INFO);
			}
		}
	}
	private class PesquisarListener implements KeyListener{
		public void keyPressed(KeyEvent arg0) { }
		public void keyReleased(KeyEvent event) {
			char letra = event.getKeyChar();
			if (Character.isAlphabetic(letra) || letra == '\b'){
				pesquisar();
			}
		}
		public void keyTyped(KeyEvent arg0) { }
		
	}
	private class AdicionarButtonListener implements ActionListener{
		private Component main;
		public AdicionarButtonListener(Component main){
			this.main = main;
		}
		public void actionPerformed(ActionEvent event) {
			String txt = event.getActionCommand();
			int[] indices = table.getSelectedRows();
			if(indices.length > 0){
				String msg = String.format("TEM CERTEZA QUE DESEJA ADICIONAR %d TAPIOCA(S)?",  indices.length);
				int escolha = JOptionPane.showConfirmDialog(main, msg, null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.ERRO);
				if (escolha == JOptionPane.OK_OPTION){
					for(int indice:indices){
						String nome = modelo.getValueAt(indice, 0).toString();
						for(Tapioca tap: central.getTapiocas()){
							if(tap.equals(nome)){
								((Cliente)pessoa).adicionarAoCarrinho(tap);
							}
						}
					}
					Persistencia.salvarCentral(central);
					refresh();
				}
			} else{
				JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UMA(1) LINHA", null, 0, ConstantesSwing.INFO);
			}
		}
	}
	private void base() {
		adicionarTable();
		adicionarColunas();
		adicionarLinhas(tapiocas);
		adicionarBotoesPanel();
		adicionarBuscador();
		adicionarAoCarrinhoButton();
		adicionarListarIngredientesButton();
	}
	public void pesquisar() {
		ArrayList<Tapioca> dis = new ArrayList<>();
		ArrayList<Tapioca> ind = new ArrayList<>();
		ArrayList<Tapioca> tap = new ArrayList<>();
		for (Tapioca in: tapiocas){
			Disponibilidade disp = in.getDisponibilidade();
			String texto = buscador.getText().toUpperCase(),
				   nome = in.getNome();

			if (buscar(nome, texto)){
				if(disponivel.isSelected() && disp == Disponibilidade.DISPONIVEL){
					dis.add(in);
				} else if(indisponivel.isSelected() && disp == Disponibilidade.INDISPONIVEL){
					ind.add(in);
				} else{
					tap.add(in);
				}
			}
		}
		if(disponivel.isSelected()){
			refresh(dis);
		} else if(indisponivel.isSelected()){
			refresh(ind);
		} else{
			refresh(tap);
		}
	}
	private boolean buscar(String nome, String txt) {
		if(nome.contains(txt)){
			return true;
		}
		return false;
	}
	private void adicionarTable(){
		modelo = new DefaultTableModelPadrao();
		table = new JTable(modelo);
		scroll = new JScrollPane(table);
		add(scroll);
	}
	private void adicionarColunas(){
		Object[] colunas = {"NOME", "PREÇO", "VALOR CALÓRICO", "DISPONIBILIDADE"};
		for(Object col: colunas){
			modelo.addColumn(col);
		}
	}
	private void remover(){
		for(; table.getRowCount() > 0;){
			modelo.removeRow(0);
		}
	}
	private void refresh(ArrayList<Tapioca> tapiocas){
		remover();
		adicionarLinhas(tapiocas);
	}
	public void refresh() {
		remover();
		adicionarLinhas(tapiocas);
	}
	private void adicionarLinhas(ArrayList<Tapioca> tapiocas){
		for(Tapioca tap: tapiocas){
			String preco = String.format("R$ %.2f", tap.getPreco()),
				   calorias = String.format("%.2f", tap.getValorCalorico());
			Object[] row ={tap.getNome(), preco, calorias, tap.getDisponibilidade().toString()};
			modelo.addRow(row);
		}
		DefaultTableCellRenderer meio = new DefaultTableCellRenderer();
		meio.setHorizontalAlignment(SwingConstants.CENTER);
		for (int c = 0; c < table.getColumnCount(); c++){
			table.getColumnModel().getColumn(c).setCellRenderer(meio);
		}
		table.repaint();
	}
	private void adicionarBuscador(){
		painelPesquisar = new JPanelPadrao();
		painelPesquisar.setLayout(new BorderLayout());
		buscador = new JTextFieldPadrao("");

		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new GridLayout(1, 2));
		
		disponivel = new JRadioButtonPadrao("DISPONÍVEL");
		indisponivel = new JRadioButtonPadrao("INDISPONÍVEL");

		new RadioButtonListener(disponivel, indisponivel, this);
		setRadioButton(disponivel);
		setRadioButton(indisponivel);
		
		panel.add(disponivel);
		panel.add(indisponivel);
		buscador.addKeyListener(new PesquisarListener());
		
		painelPesquisar.add(panel, BorderLayout.NORTH);
		painelPesquisar.add(buscador, BorderLayout.SOUTH);
		add(painelPesquisar, BorderLayout.NORTH);
	}
	private void setRadioButton(JRadioButtonPadrao check){
		check.setFont(ConstantesSwing.TIMES_TEXTO);
		check.setHorizontalAlignment(SwingConstants.CENTER);
	}
	private void adicionarBotoesPanel(){
		painel = new JPanelPadrao();
		painel.setLayout(new GridLayout(1, 5));

		add(painel, BorderLayout.SOUTH);
	}
	private void adicionarListarIngredientesButton(){
		listar = new JButtonPadrao("LISTAR INGREDIENTES");
		ListarIngredienteListener list = new ListarIngredienteListener(this);
		listar.setIcon(ConstantesSwing.LISTAR);
		painel.add(listar);
		listar.addActionListener(list);
	}
	private void adicionarAoCarrinhoButton() {
		JButtonPadrao adicionar = new JButtonPadrao("ADICIONAR AO CARRINHO");
		adicionar.addActionListener(new AdicionarButtonListener(this));
		adicionar.setIcon(ConstantesSwing.ADD_CARRINHO);
		painel.add(adicionar);
	}
}
