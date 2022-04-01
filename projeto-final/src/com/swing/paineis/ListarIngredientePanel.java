package com.swing.paineis;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import com.excecoes.IngredienteEmTapiocaException;
import com.excecoes.StringVaziaException;
import com.sistema.*;
import com.swing.*;
import com.swing.api.RangeSliderPanel;
import com.swing.listeners.RadioButtonListener;
import com.swing.telas.TelaEditarIngrediente;

public class ListarIngredientePanel extends JPanelPadrao implements Refreshable, Pesquisavel{
	private DefaultTableModelPadrao modelo;
	private JTable table;
	private JScrollPane scroll;
	private ArrayList<Ingrediente> ingredientes;
	private Component main;
	private JPanelPadrao painelEditarRemover,
						 painelPesquisar;
	private RangeSliderPanel rangeSlider;
	private JTextFieldPadrao buscador;
	private JRadioButtonPadrao disponivel,
							indisponivel;
	private Central central;
	private double min,
				   max;
	public ListarIngredientePanel(Central central, ArrayList<Ingrediente> ingredientes, Component main){
		this.central = central;
		this.ingredientes = ingredientes;
		this.main = main;
		setLayout(new BorderLayout());
		base();
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


	private class ExcluirIngredienteListener implements ActionListener{
		private Component main;
		public ExcluirIngredienteListener(Component main){
			this.main = main;
		}
		public void actionPerformed(ActionEvent arg0) {
			if(ingredientes.size() > 0){
				int[] indices = table.getSelectedRows();
				if(indices.length > 0){
					String msg = String.format("DESEJA EXCLUIR %d INGREDIENTES?", indices.length);
					int escolha = JOptionPane.showConfirmDialog(main, msg, null, 0, 0, ConstantesSwing.ERRO);
					if(escolha == JOptionPane.OK_OPTION){
						for(int c = 0; c < indices.length;c++){
							Ingrediente i = null;
							String num = table.getValueAt(indices[c], 0).toString();
							for(Ingrediente in: central.getIngredientes()){
								if(in.equals(num)){
									i = in;
									break;
								}
							}
							try{
								central.deletarIngrediente(i);
								Persistencia.salvarCentral(central);
								refresh();
							} catch(IngredienteEmTapiocaException e){
								JOptionPane.showMessageDialog(main, e.getMessage(), "ERRO", 0, ConstantesSwing.ERRO);
							}
						}
					}
				} else{
					JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UMA(1) LINHA", null, 0, ConstantesSwing.INFO);
				}
			} else{
				JOptionPane.showMessageDialog(main, "CADASTRE UM INGREDIENTE", null, 0, ConstantesSwing.INFO);
			}
		}
	}
	private class EditarIngredienteListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(ingredientes.size() > 0){
				int[] indices = table.getSelectedRows();
	
				if (indices.length > 0){
					ArrayList<Ingrediente> ing = new ArrayList<>();
					ArrayList<String> nomes = new ArrayList<>();
					
					for(int indice: indices){
						String nome = table.getValueAt(indice, 0).toString();
						for(Ingrediente i: ingredientes){
							if(i.equals(nome)){
								int n = 0;
								for(int c = 0; c < nomes.size(); c++){
									if(nome.equals(nomes.get(c))){
										break;
									} else{
										n++;
									}
								}
								if(n == nomes.size()){
									ing.add(i);
									nomes.add(i.getNome());
								}
								break;
							}
						}
					}
					new TelaEditarIngrediente(central, ing);
				} else{
					JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UMA(1) LINHA", null, 0, ConstantesSwing.INFO);
				}
			} else{
				JOptionPane.showMessageDialog(main, "CADASTRE UM INGREDIENTE", null, 0, ConstantesSwing.INFO);
			}
		}
	}
	private void base() {
		adicionarTable();
		adicionarColunas();
		adicionarLinhas(ingredientes);
		adicionarPaineis();
		adicionarBuscador();
		Pessoa p = central.getLogado();
		if (p.isAdministrador() && !(main instanceof EditarIngredientePanel)){
			if(!(main instanceof ListarTapiocaPanel)){
				removerIngrediente();
			}
			editarIngrediente();
		}
	}
	public void pesquisar() {
		ArrayList<Ingrediente> dis = new ArrayList<>();
		ArrayList<Ingrediente> ind = new ArrayList<>();
		ArrayList<Ingrediente> ing = new ArrayList<>();
		for (Ingrediente in: ingredientes){
			Disponibilidade disp = in.getDisponibilidade();
			String texto = buscador.getText().toUpperCase(),
				   nome = in.getNome();
			double preco = in.getPrecoPorGrama();

			if (buscar(nome, texto)){
				if(rangeSlider != null){
					if(preco >= rangeSlider.getValorMinimo() && preco <= rangeSlider.getValorMaximo()){
						if(disponivel.isSelected() && disp == Disponibilidade.DISPONIVEL){
							dis.add(in);
						} else if(indisponivel.isSelected() && disp == Disponibilidade.INDISPONIVEL){
							ind.add(in);
						} else{
							ing.add(in);
						}
					}
				}
			}
		}
		if(disponivel.isSelected()){
			refresh(dis);
		} else if(indisponivel.isSelected()){
			refresh(ind);
		} else{
			refresh(ing);
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
		add(scroll, BorderLayout.CENTER);
	}
	private void adicionarPaineis(){
		painelEditarRemover = new JPanelPadrao();
		painelEditarRemover.setLayout(new GridLayout(1,2));
		painelPesquisar = new JPanelPadrao();
		painelPesquisar.setLayout(new BorderLayout());
		add(painelEditarRemover, BorderLayout.SOUTH);
		add(painelPesquisar, BorderLayout.NORTH);
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
	public void refresh(ArrayList<Ingrediente> ing){
		remover();
		adicionarLinhas(ing);
	}
	public void refresh() {
		remover();
		adicionarLinhas(ingredientes);
	}
	private void adicionarLinhas(ArrayList<Ingrediente> ing){
		double min = 0,
			   max = 0;
		for(int c = 0; c < ing.size(); c++){
			Ingrediente i = ing.get(c);
			double price = i.getPrecoPorGrama(),
				   calories = i.getValorCalorico();
			
			if(min > price || c == 0){
				min = price;
			}
			if(max < price){
				max = price;
			}
			String preco = String.format("R$ %.2f", price),
				   calorias = String.format("%.2f", calories);
			Object[] row ={i.getNome(), preco, calorias, i.getDisponibilidade().toString()};
			modelo.addRow(row);
		}
		this.min = min;
		this.max = max;
		if(ing == ingredientes && rangeSlider != null){
			int minimo = (int) min,
				maximo = (int) Math.ceil(max);
			rangeSlider.setValorMinimoTotal(minimo);
			rangeSlider.setValorMinimo(minimo);
			rangeSlider.setValorMaximoTotal(maximo);
			rangeSlider.setValorMaximo(maximo);
		}
		DefaultTableCellRenderer meio = new DefaultTableCellRenderer();
		meio.setHorizontalAlignment(SwingConstants.CENTER);
		for (int c = 0; c < table.getColumnCount(); c++){
			int tamanho = 0;
			TableColumn coluna = table.getColumnModel().getColumn(c);
			coluna.setCellRenderer(meio);
			if(c == 0){
				tamanho = 200;
			} else if(c == 1){
				tamanho = 60;
			} else if (c == 2){
				tamanho = 80;
			} else{
				tamanho = 125;
			}
			coluna.setPreferredWidth(tamanho);
		}
		table.repaint();
	}
	private void editarIngrediente(){
		JButtonPadrao editar = new JButtonPadrao("EDITAR INGREDIENTE");
		EditarIngredienteListener list = new EditarIngredienteListener();
		editar.addActionListener(list);
		editar.setIcon(ConstantesSwing.EDITAR);
		painelEditarRemover.add(editar);
	}
	private void removerIngrediente(){
		JButtonPadrao remover = new JButtonPadrao("EXCLUIR INGREDIENTE");
		ExcluirIngredienteListener list = new ExcluirIngredienteListener(this);
		remover.addActionListener(list);
		remover.setIcon(ConstantesSwing.EXCLUIR);
		painelEditarRemover.add(remover);
	}
	private void adicionarBuscador(){
		buscador = new JTextFieldPadrao("");
		buscador.addKeyListener(new PesquisarListener());
		
		adicionarRadioButtons();
		adicionarRangeSlider();
		
		painelPesquisar.add(buscador, BorderLayout.SOUTH);
	}
	private void adicionarRadioButtons(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new GridLayout(1, 2)); 
		
		disponivel = new JRadioButtonPadrao("DISPONÍVEL");
		indisponivel = new JRadioButtonPadrao("INDISPONÍVEL");

		new RadioButtonListener(disponivel, indisponivel, this);
		setRadioButton(disponivel);
		setRadioButton(indisponivel);
		
		panel.add(disponivel);
		panel.add(indisponivel);
		
		painelPesquisar.add(panel, BorderLayout.NORTH);
	}
	private void setRadioButton(JRadioButtonPadrao check){
		check.setFont(ConstantesSwing.TIMES_TEXTO);
		check.setHorizontalAlignment(SwingConstants.CENTER);
	}
	private void adicionarRangeSlider(){
		int min = (int) this.min,
			max = (int) Math.ceil(this.max);
		rangeSlider = new RangeSliderPanel(min, max, min, max, this);
		
		painelPesquisar.add(rangeSlider, BorderLayout.CENTER);
	}
	public int[] getSelectedRows(){
		return table.getSelectedRows();
	}
}
