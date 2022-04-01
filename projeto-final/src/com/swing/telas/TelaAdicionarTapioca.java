package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.excecoes.StringVaziaException;
import com.excecoes.TapiocaCadastradaException;
import com.sistema.*;
import com.swing.*;
import com.swing.listeners.RadioButtonListener;

public class TelaAdicionarTapioca extends JFramePadrao implements Refreshable, Pesquisavel{
	private JTextFieldPadrao nome,
							 buscador,
							 preco,
							 valorC;
	private DefaultTableModelPadrao modelo,
									modeloIngredientes;
	private JTable tabela,
				   tabelaIngrediente;
	private JScrollPane scrollTabela,
						scrollTabelaIngrediente;
	private JRadioButtonPadrao disponivel,
							indisponivel;
	private ArrayList<Ingrediente> ingredientes;
	private JButtonPadrao confirmar,
						  limpar,
						  adicionar,
						  remover;
	private JPanelPadrao panelIngredientes,
						 panelTapioca;
	private Central central;
	public TelaAdicionarTapioca(Central central, ArrayList<Ingrediente> ingredientes){
		this.central = central;
		this.ingredientes = ingredientes;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		base();
		setVisible(true);
	}
	private class PesquisarListener implements KeyListener{
		public void keyPressed(KeyEvent event)  {
			
		}
		public void keyReleased(KeyEvent event) {
			char letra = event.getKeyChar();
			if (Character.isAlphabetic(letra) || letra == '\b'){
				pesquisar();
			}
		}
		public void keyTyped(KeyEvent event)    {	}
	}

	private class ConfirmarListener implements ActionListener{
		private Component main;
		public ConfirmarListener(Component main){
			this.main = main;
		}
		public void actionPerformed(ActionEvent event) {
			int quant = tabelaIngrediente.getRowCount();
			if(quant > 0){
				try{
					String name = nome.getText().toUpperCase();
					Util.eVazia(name);
					ArrayList<Ingrediente> ing = new ArrayList<>();
					for(int c = 0; c < quant; c++){
						String objeto = modeloIngredientes.getValueAt(c, 0).toString();
						for(Ingrediente in: ingredientes){
							if(in.equals(objeto)){
								ing.add(in);
							}
						}
					}
					int escolha = JOptionPane.showConfirmDialog(main, "DESEJA SALVAR ESSA TAPIOCA?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
					if(escolha == JOptionPane.YES_OPTION){
						central.cadastrarTapioca(new Tapioca(name, ing));
						Persistencia.salvarCentral(central);
						escolha = JOptionPane.showConfirmDialog(main, "DESEJA SAIR?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
						if(escolha == JOptionPane.YES_OPTION){
							dispose();
						} else{
							limpar.doClick();
							nome.requestFocus();
							for(;modeloIngredientes.getRowCount() > 0;){
								modeloIngredientes.removeRow(0);
							}
						}
					}
					Persistencia.salvarCentral(central);
				}catch(TapiocaCadastradaException | StringVaziaException e) {
					JOptionPane.showMessageDialog(main, e.getMessage(), "ERRO", 0, ConstantesSwing.ERRO);
				}
			} else{
				JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UM(1) INGREDIENTE", "ERRO", 0, ConstantesSwing.ERRO);
			}
		}
	}
	private class AdicionarLimparListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String txt = event.getActionCommand();
			if(txt.equals("LIMPAR")){
				nome.setText("");
				tabela.getSelectionModel().clearSelection();
			} else if (txt.equals("ADICIONAR")){
				int[] indices = tabela.getSelectedRows();
				if(indices.length > 0){
					for(int indice: indices){
						String nome = modelo.getValueAt(indice, 0).toString();
						for(Ingrediente ingrediente: ingredientes){
							if(ingrediente.equals(nome)){
								Object[] obj = {ingrediente.getNome(), ingrediente.getPrecoPorGrama(), 
										ingrediente.getValorCalorico(), ingrediente.getDisponibilidade().toString()};
								modeloIngredientes.addRow(obj);
								
							}
						}
					}
					centralizarModelo(tabelaIngrediente, modeloIngredientes);
				}
			} else{
				int[] indices = tabelaIngrediente.getSelectedRows();
				if(indices.length > 0){
					for(int indice = indices.length-1; indice >= 0; indice--){
						modeloIngredientes.removeRow(indices[indice]);
					}
				}
			}
			refreshPrecoECaloricas();
		}
	}
	private void base() {
		setSize(840, 490);
		setLayout(new GridLayout(1, 2));
		adicionarPanels();
		adicionarIngredientes();
		adicionarTapioca();
	}
	private void adicionarPanels(){
		panelIngredientes = gerarPanel();
		panelTapioca = gerarPanel();
		add(panelIngredientes);
		add(panelTapioca);
	}
	private JPanelPadrao gerarPanel(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		return panel;
	}
	public void pesquisar() {
		ArrayList<Ingrediente> dis = new ArrayList<>();
		ArrayList<Ingrediente> ind = new ArrayList<>();
		ArrayList<Ingrediente> ing = new ArrayList<>();
		for (Ingrediente in: ingredientes){
			Disponibilidade disp = in.getDisponibilidade();
			String texto = buscador.getText().toUpperCase(),
				   nome = in.getNome();

			if (buscar(nome, texto)){
				if(disponivel.isSelected() && disp == Disponibilidade.DISPONIVEL){
					dis.add(in);
				} else if(indisponivel.isSelected() && disp == Disponibilidade.INDISPONIVEL){
					ind.add(in);
				} else{
					ing.add(in);
				}
			}
		}
		if(disponivel.isSelected()){
			refresh(modelo, dis);
		} else if(indisponivel.isSelected()){
			refresh(modelo, ind);
		} else{
			refresh(modelo, ing);
		}
	}
	private boolean buscar(String nome, String txt) {
		if(nome.contains(txt)){
			return true;
		}
		return false;
	}
	private void adicionarIngredientes(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new FlowLayout());
		JLabelPadrao pesquisa = new JLabelPadrao("PESQUISA");
		buscador = new JTextFieldPadrao("");
		buscador.addKeyListener(new PesquisarListener());

		panel.add(pesquisa);
		panelIngredientes.add(panel);
		adicionarRadioButton();
		panelIngredientes.add(buscador);
		adicionarTabelaIngredientes();
		adicionarBotoesIngredientes();
	}
	
	private void adicionarTapioca(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new FlowLayout());
		
		JLabelPadrao name = new JLabelPadrao("NOME");
		name.setHorizontalAlignment(SwingConstants.CENTER);
		
		nome = new JTextFieldPadrao("");
		preco = new JTextFieldPadrao("PREÇO: 0");
		valorC = new JTextFieldPadrao("CALORIAS: 0");
		confirmar = new JButtonPadrao("CONFIRMAR");
		
		setTextField(preco);
		setTextField(valorC);
		
		panel.add(name);
		panelTapioca.add(panel);
		panelTapioca.add(nome);
		
		panel = new JPanelPadrao();
		panel.setLayout(new BorderLayout());
		
		JTextFieldPadrao ingredientes = new JTextFieldPadrao("INGREDIENTES");
		ingredientes.setEditable(false);

		ingredientes.setForeground(Color.BLACK);
		panel.add(ingredientes, BorderLayout.CENTER);
		panelTapioca.add(panel);
		
		adicionarTabelaTapioca();
		
		panel = new JPanelPadrao();
		panel.setLayout(new GridLayout(1,3));

		confirmar.addActionListener(new ConfirmarListener(this));
		
		panel.add(preco);
		panel.add(valorC);
		panel.add(confirmar);
		panelTapioca.add(panel);
	}

	private void setTextField(JTextFieldPadrao txt){
		txt.setEditable(false);
		txt.setOpaque(true);
		txt.setBackground(Color.WHITE);
	}
	private void adicionarRadioButton(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new GridLayout(1, 2));
		disponivel = new JRadioButtonPadrao("DISPONÍVEL");
		disponivel.setHorizontalAlignment(SwingConstants.CENTER);
		indisponivel = new JRadioButtonPadrao("INDISPONÍVEL");
		indisponivel.setHorizontalAlignment(SwingConstants.CENTER);
		
		new RadioButtonListener(disponivel, indisponivel, this);
		
		panel.add(disponivel);
		panel.add(indisponivel);
		panelIngredientes.add(panel);
	}

	private void remover(DefaultTableModelPadrao modelo) {
		for(;modelo.getRowCount() > 0;){
			modelo.removeRow(0);
		}
	}
	public void refresh(DefaultTableModelPadrao modelo, ArrayList<Ingrediente> ingredientes) {
		remover(modelo);
		adicionarLinhas(modelo, ingredientes);
		tabela.repaint();
		tabelaIngrediente.repaint();
	}
	public void refresh() {
		remover(modelo);
		adicionarLinhas(modelo, ingredientes);
		tabela.repaint();
	}
	private void adicionarColunas(DefaultTableModelPadrao modelo){
		modelo.addColumn("NOME");
		modelo.addColumn("PREÇO");
		modelo.addColumn("VALOR CALÓRICO");
		modelo.addColumn("DISPONIBILIDADE");
	}
	private void adicionarLinhas(DefaultTableModelPadrao modelo, ArrayList<Ingrediente> ing) {
		for(Ingrediente in: ing){
			Object[] obj = {in.getNome(), in.getPrecoPorGrama(), in.getValorCalorico(), in.getDisponibilidade().toString()};
			modelo.addRow(obj);
		}
		centralizarModelo(tabela, modelo);
	}
	private void centralizarModelo(JTable tabela, DefaultTableModelPadrao modelo) {
		DefaultTableCellRenderer meio = new DefaultTableCellRenderer();
		meio.setHorizontalAlignment(SwingConstants.CENTER);
		for (int c = 0; c < tabela.getColumnCount(); c++){
			int tamanho = 0;
			TableColumn coluna = tabela.getColumnModel().getColumn(c);
			coluna.setCellRenderer(meio);
			if(c == 0){
				tamanho = 120;
			} else if(c == 1){
				tamanho = 60;
			} else if (c == 2){
				tamanho = 130;
			} else{
				tamanho = 130;
			}
			coluna.setPreferredWidth(tamanho);
		}
		tabela.repaint();
	}
	private void refreshPrecoECaloricas(){
		int indices = tabelaIngrediente.getRowCount();
		double preco = 0;
		double valorC = 0;
		for(int c = 0; c < indices; c++){
			String nome = tabelaIngrediente.getValueAt(c, 0).toString();
			for(Ingrediente in: ingredientes){
				if(in.equals(nome)){
					preco += in.getPrecoPorGrama();
					valorC += in.getValorCalorico();
				}
			}
		}
		String price = String.format("PREÇO: R$ %.2f", preco),
			   value = String.format("CALORIAS: %.2f", valorC);
		
		this.preco.setText(price);
		this.valorC.setText(value);
	}
	private void adicionarTabelaIngredientes(){
		modelo = new DefaultTableModelPadrao();
		adicionarColunas(modelo);
		tabela = new JTable(modelo);
		refresh();
		scrollTabela = new JScrollPane(tabela);
		panelIngredientes.add(scrollTabela);
	}
	private void adicionarTabelaTapioca(){
		modeloIngredientes = new DefaultTableModelPadrao();
		adicionarColunas(modeloIngredientes);
		tabelaIngrediente = new JTable(modeloIngredientes);
		scrollTabelaIngrediente = new JScrollPane(tabelaIngrediente);
		panelTapioca.add(scrollTabelaIngrediente);
	}
	private void adicionarBotoesIngredientes(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new GridLayout(1,3));
		limpar = new JButtonPadrao("LIMPAR");
		limpar.setToolTipText("LIMPA A SELEÇÃO, MAS NÃO OS SELECIONADOS");
		adicionar = new JButtonPadrao("ADICIONAR");
		remover = new JButtonPadrao("REMOVER");
		
		AdicionarLimparListener list = new AdicionarLimparListener();
		limpar.addActionListener(list);
		adicionar.addActionListener(list);
		remover.addActionListener(list);
		
		panel.add(limpar);
		panel.add(remover);
		panel.add(adicionar);
		panelIngredientes.add(panel);
	}
}
