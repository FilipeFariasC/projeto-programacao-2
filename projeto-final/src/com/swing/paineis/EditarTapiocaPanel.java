package com.swing.paineis;

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
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.excecoes.StringVaziaException;
import com.sistema.Central;
import com.sistema.Disponibilidade;
import com.sistema.Ingrediente;
import com.sistema.Persistencia;
import com.sistema.Tapioca;
import com.sistema.Util;
import com.swing.*;
import com.swing.listeners.RadioButtonListener;

public class EditarTapiocaPanel extends JPanelPadrao implements Refreshable, Pesquisavel{
	private JTextFieldPadrao nome,
							 buscador,
							 preco,
							 valorC;
	private Tapioca tapioca;
	private DefaultTableModelPadrao modelo,
									modeloIngredientes;
	private JTable tabela,
				   tabelaIngrediente;
	private JScrollPane scrollTabela,
						scrollTabelaIngrediente;
	private JRadioButtonPadrao disponivel,
							indisponivel;
	private ArrayList<Ingrediente> ingredientes,
								   ingredientesTapioca;
	private JButtonPadrao confirmar,
						  limpar,
						  adicionar,
						  remover;
	private JPanelPadrao panelIngredientes,
						 panelTapioca;
	private Central central;
	private JTabbedPanePadrao abas;
	private Window main;
	public EditarTapiocaPanel(Central central, Tapioca tapioca, JTabbedPanePadrao abas, Window main){
		this.central = central;
		this.ingredientes = central.getIngredientes();
		this.ingredientesTapioca = tapioca.getIngredientes();
		this.tapioca = tapioca;
		this.abas = abas;
		this.main = main;
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
		public void actionPerformed(ActionEvent event) {
			int quant = tabelaIngrediente.getRowCount();
			if(quant > 0){
				try{
					String n = abaNome(tapioca);
					int indice = abas.indexOfTab(n);
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
						ingredientesTapioca.clear();
						for(Ingrediente in: ing){
							ingredientesTapioca.add(in);
						}
						escolha = JOptionPane.showConfirmDialog(main, "DESEJA FECHAR ESSA ABA?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
						if(escolha == JOptionPane.YES_OPTION){
							abas.remove(indice);
							if(abas.getTabCount() == 0){
								main.dispose();
							}
						} else{
							abas.setTitleAt(indice, abaNome(tapioca));
							nome.requestFocus();
							refresh();
						}
					}
					Persistencia.salvarCentral(central);
				}catch(StringVaziaException e) {
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
	private String abaNome(Tapioca i){
		return String.format("%s - %s - R$ %.2f - %.2f cal", 
				i.getNome(), i.getDisponibilidade().toString(), 
				i.getPreco(), i.getValorCalorico());
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
			refresh(modelo, tabela, dis);
		} else if(indisponivel.isSelected()){
			refresh(modelo, tabela, ind);
		} else{
			refresh(modelo, tabela, ing);
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
		
		nome = new JTextFieldPadrao(tapioca.getNome());
		preco = new JTextFieldPadrao("PREÇO: "+tapioca.getPreco());
		valorC = new JTextFieldPadrao("CALORIAS: "+tapioca.getValorCalorico());
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

		confirmar.addActionListener(new ConfirmarListener());
		
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
	public void refresh(DefaultTableModelPadrao modelo, JTable table, ArrayList<Ingrediente> ingredientes) {
		remover(modelo);
		adicionarLinhas(modelo, table, ingredientes);
		tabela.repaint();
		tabelaIngrediente.repaint();
	}
	public void refresh() {
		remover(modelo);
		adicionarLinhas(modelo, tabela, ingredientes);
	}
	private void adicionarColunas(DefaultTableModelPadrao modelo){
		modelo.addColumn("NOME");
		modelo.addColumn("PREÇO");
		modelo.addColumn("VALOR CALÓRICO");
		modelo.addColumn("DISPONIBILIDADE");
	}
	private void adicionarLinhas(DefaultTableModelPadrao modelo, JTable table, ArrayList<Ingrediente> ing) {
		for(Ingrediente in: ing){
			Object[] obj = {in.getNome(), in.getPrecoPorGrama(), in.getValorCalorico(), in.getDisponibilidade().toString()};
			modelo.addRow(obj);
		}
		centralizarModelo(table, modelo);
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
		refresh(modeloIngredientes, tabelaIngrediente, ingredientesTapioca);
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
