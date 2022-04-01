package com.swing.paineis;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import com.excecoes.NaoENumeroException;
import com.excecoes.StringVaziaException;
import com.sistema.*;
import com.swing.*;
import com.swing.api.RangeSliderPanel;
import com.swing.listeners.RadioButtonListener;
import com.swing.telas.*;

public class ListarTapiocaPanel extends JPanelPadrao implements Refreshable, Pesquisavel{
	private DefaultTableModelPadrao modelo;
	private JTable table;
	private JScrollPane scroll;
	private ArrayList<Tapioca> tapiocas;
	private JButtonPadrao listar,
						  remover;
	private Component main;
	private JPanelPadrao painel,
						 painelPesquisar;
	private RangeSliderPanel rangeSlider;
	private JTextFieldPadrao buscador;
	private JRadioButtonPadrao disponivel,
							indisponivel;
	private Pessoa pessoa;
	private Central central;
	private double min,
				   max;
	public ListarTapiocaPanel(Central central, Pessoa pessoa, ArrayList<Tapioca> tapiocas, Component main){
		this.tapiocas = tapiocas;
		this.main = main;
		this.pessoa = pessoa;
		this.central = central;
		setLayout(new BorderLayout());
		base();
	}
	private class ListarOuEditarListener implements ActionListener {
		private Component main;
		public ListarOuEditarListener(Component main) {
			this.main = main;
		}
		public void actionPerformed(ActionEvent event) {
			int[] escolha = table.getSelectedRows();
			if (escolha.length > 0){
				String txt = event.getActionCommand();
				ArrayList<Tapioca> tapioca = new ArrayList<>();
				for(int indice: escolha){
					String esc = table.getValueAt(indice, 0).toString();
					for(Tapioca tap: tapiocas){
						if (tap.equals(esc)){
							tapioca.add(tap);
							break;
						}
					}
				}
				if(txt.equals("LISTAR INGREDIENTES")){
					new TelaListarIngrediente(central, tapioca, main);
				} else{
					new TelaEditarTapioca(central, tapioca);
				}
			} else{
				if(tapiocas.size() > 0){
					JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UMA(1) LINHA", null, 0, ConstantesSwing.INFO);
				} else{
					String msg = null;
					if(pessoa.isAdministrador()){
						msg = "CADASTRE UMA TAPIOCA";
					} else{
						msg = "ADICIONE UMA TAPIOCA NO CARRINHO";
					}
					JOptionPane.showMessageDialog(main, msg, null, 0, ConstantesSwing.INFO);
				}
			}
		}
	}
	private class PesquisarListener implements KeyListener{
		public void keyPressed(KeyEvent arg0) { }
		public void keyReleased(KeyEvent event) {
			char letra = event.getKeyChar();
			if(Character.isAlphabetic(letra) || letra == '\b'){
				pesquisar();
			}
		}
		public void keyTyped(KeyEvent arg0) { }
	}
	private class ExcluirButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int[] indices = table.getSelectedRows();
			if (indices.length != 0){
				String msg = String.format("TEM CERTEZA QUE DESEJA REMOVER %d TAPIOCA(S)?", indices.length);
				int escolha = JOptionPane.showConfirmDialog(main, msg, null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.ERRO);
				if (escolha == JOptionPane.OK_OPTION){
					for(int c = 0; c < indices.length; c++){
						String nome = modelo.getValueAt(indices[c], 0).toString();
						System.out.println(nome);
						for(int n = 0; n < tapiocas.size();n++){
							Tapioca tap = tapiocas.get(n);
							if(tap.equals(nome)){
								tapiocas.remove(tap);
								break;
							}
						}
					}
					Persistencia.salvarCentral(central);
					refresh();
				}
			} else{
				if(tapiocas.size() > 0){
					JOptionPane.showMessageDialog(main, "SELECIONE AO MENOS UMA(1) LINHA", null, 0, ConstantesSwing.INFO);
				} else{
					String msg = null;
					if(pessoa.isAdministrador()){
						msg = "CADASTRE UMA TAPIOCA";
					} else{
						msg = "ADICIONE UMA TAPIOCA NO CARRINHO";
					}
					JOptionPane.showMessageDialog(main, msg, null, 0, ConstantesSwing.INFO);
				}
			}
		}
	}
	private class ListarCardapioButtonListener implements ActionListener{
		private Component frame;
		
		public ListarCardapioButtonListener(Component frame) {
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent event) {
			new TelaMostrarCardapio(new ListarTapiocaCardapioPanel(central, pessoa, frame));
		}
	}
	private void base() {
		adicionarTable();
		adicionarColunas();
		adicionarLinhas(tapiocas);
		adicionarBotoesPanel();
		adicionarBuscador();
		if (!(main instanceof ListarTapiocaPanel) &&
			!(main instanceof TelaListarTapioca)){
			adicionarRemoverTapiocaButton();
			if(pessoa instanceof Cliente){
				adicionarAoCarrinho();
			} else{
				adicionarEditarTapioca();
			}
		}
		adicionarListarIngredientesButton();
		adicionarListener();
	}
	public void pesquisar() {
		ArrayList<Tapioca> dis = new ArrayList<>();
		ArrayList<Tapioca> ind = new ArrayList<>();
		ArrayList<Tapioca> tap = new ArrayList<>();
		for (Tapioca in: tapiocas){
			Disponibilidade disp = in.getDisponibilidade();
			String texto = buscador.getText().toUpperCase(),
				   nome = in.getNome();
			double preco = in.getPreco();
			
			if (buscar(nome, texto)){
				if(rangeSlider != null){
					if(preco >= rangeSlider.getValorMinimo() && preco <= rangeSlider.getValorMaximo()){
						if(disponivel.isSelected() && disp == Disponibilidade.DISPONIVEL){
							dis.add(in);
						} else if(indisponivel.isSelected() && disp == Disponibilidade.INDISPONIVEL){
							ind.add(in);
						} else{
							tap.add(in);
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
		double min = 0,
			   max = 0;
		for(int c = 0; c < tapiocas.size(); c++){
			Tapioca tap = tapiocas.get(c);
			double price = tap.getPreco(),
				   calories = tap.getValorCalorico();
			if(min > price || c == 0){
				min = price;
			}
			if(max < price){
				max = price;
			}
			String preco = String.format("R$ %.2f", price),
				   calorias = String.format("%.2f", calories);
			Object[] row ={tap.getNome(), preco, calorias, tap.getDisponibilidade().toString()};
			modelo.addRow(row);
		}
		this.min = min;
		this.max = max;
		if(tapiocas == this.tapiocas && rangeSlider != null){
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
	private void adicionarBuscador(){
		painelPesquisar = new JPanelPadrao();
		painelPesquisar.setLayout(new BorderLayout());
		
		buscador = new JTextFieldPadrao("");
		buscador.addKeyListener(new PesquisarListener());
		
		adicionarRadioButtons();
		adicionarRangeSlider();
		
		painelPesquisar.add(buscador, BorderLayout.SOUTH);
		add(painelPesquisar, BorderLayout.NORTH);
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
	private void adicionarRangeSlider(){
		int min = (int)this.min,
			max = (int) Math.ceil(this.max);
		rangeSlider = new RangeSliderPanel(min, max, min, max, this);
		
		painelPesquisar.add(rangeSlider, BorderLayout.CENTER);
	}
	private void setRadioButton(JRadioButtonPadrao check){
		check.setFont(ConstantesSwing.TIMES_TEXTO);
		check.setHorizontalAlignment(SwingConstants.CENTER);
	}
	private void adicionarBotoesPanel(){
		painel = new JPanelPadrao();
		painel.setLayout(new GridLayout(1, 5));
	}
	private void adicionarListarIngredientesButton(){
		listar = new JButtonPadrao("LISTAR INGREDIENTES");
		listar.setIcon(ConstantesSwing.LISTAR);
		listar.addActionListener(new ListarOuEditarListener(this));
		painel.add(listar);
		add(painel, BorderLayout.SOUTH);
	}

	private void adicionarRemoverTapiocaButton() {
		remover = new JButtonPadrao("");
		String txt;
		if(pessoa instanceof Cliente){
			txt = "REMOVER AO CARRINHO";
			remover.setIcon(ConstantesSwing.REMOVER_CARRINHO);
		} else{
			txt = "EXCLUIR TAPIOCA";
			remover.setIcon(ConstantesSwing.EXCLUIR);
		}
		remover.setText(txt);
		remover.addActionListener(new ExcluirButtonListener());
		
		painel.add(remover);
	}
	private void adicionarEditarTapioca(){
		JButtonPadrao editar = new JButtonPadrao("EDITAR TAPIOCA");
		editar.setIcon(ConstantesSwing.EDITAR);
		editar.addActionListener(new ListarOuEditarListener(this));
		painel.add(editar);
	}
	private void adicionarAoCarrinho(){
		JButtonPadrao listar = new JButtonPadrao("CARDÁPIO");
		listar.addActionListener(new ListarCardapioButtonListener(this));
		listar.setIcon(ConstantesSwing.TAPIOCA_1);

		painel.add(listar);
	}
	private void adicionarListener(){
		addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {}
			public void focusGained(FocusEvent e) {
				refresh();
			}
		});
	}
	
}
