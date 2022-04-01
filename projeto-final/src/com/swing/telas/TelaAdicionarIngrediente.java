package com.swing.telas;

import java.awt.*;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.excecoes.IngredienteCadastradoException;
import com.excecoes.StringVaziaException;
import com.excecoes.ValorAbaixoDeZeroException;
import com.sistema.*;
import com.swing.*;
import com.swing.listeners.RadioButtonListener;

public class TelaAdicionarIngrediente extends JFramePadrao {
	private JTextFieldPadrao nome,
							 preco,
							 calorias;
	private JButtonPadrao confirmar,
				  		  cancelar;
	private JRadioButtonPadrao disponivel,
							   indisponivel;
	private JPanelPadrao confirm,
						 avaliable;
	private Central central;
	public TelaAdicionarIngrediente(Central central){
		this.central = central;
		setTitle("ADICIONAR INGREDIENTE");
		setSize(300, 300);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		base();
		setVisible(true);
	}
	private class AdicionarIngredienteListener implements ActionListener{
		private Window main;
		public AdicionarIngredienteListener(Window comp){
			main = comp;
		}
		public void actionPerformed(ActionEvent event) {
			String txt = event.getActionCommand();
			if (txt.equals("CANCELAR")){
				main.dispose();
			} else{
				try{
					Util.eVazia(nome.getText());
					String name = nome.getText().toUpperCase();
					double price = Double.parseDouble(preco.getText()),
						   calories = Double.parseDouble(calorias.getText());
					if(price <= 0){
						throw new ValorAbaixoDeZeroException(price);
					}
					if(calories <= 0){
						throw new ValorAbaixoDeZeroException(calories);
					}
					Disponibilidade disp = (disponivel.isSelected())? 
						Disponibilidade.DISPONIVEL:Disponibilidade.INDISPONIVEL;
					int escolha = JOptionPane.showConfirmDialog(main, "DESEJA SALVAR O INGREDIENTE?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
					if(escolha == JOptionPane.YES_OPTION){
						central.cadastrarIngrediente(name, calories, price, disp);
						Persistencia.salvarCentral(central);
						escolha = JOptionPane.showConfirmDialog(main, "DESEJA CONTINUAR?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
						if(escolha == JOptionPane.YES_NO_OPTION){
							nome.setText("");
							preco.setText("");
							calorias.setText("");
							disponivel.setSelected(false);
							indisponivel.setSelected(false);
						}else{
							main.dispose();
						}
					}
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(main, "DIGITE UM CARACTERE VÁLIDO", "ERRO", 0, ConstantesSwing.ERRO);
				} catch (IngredienteCadastradoException | ValorAbaixoDeZeroException | StringVaziaException  e) {
					JOptionPane.showMessageDialog(main, e.getMessage(), "ERRO", 0, ConstantesSwing.ERRO);
				}
			}
		}
	}
	private void base() {
		adicionarNome();
		adicionarPreco();
		adicionarCalorias();
		adicionarRadioButton();
		adicionarButtons();
	}
	private void adicionarNome(){
		JPanelPadrao panel = gerarPanel();
		panel.setLayout(new BorderLayout());
		JLabelPadrao nome = new JLabelPadrao("NOME");
		this.nome = new JTextFieldPadrao("");
		nome.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(nome, BorderLayout.CENTER);
		add(panel);
		add(this.nome);
	}
	private void adicionarPreco(){
		JPanelPadrao panel = gerarPanel();
		JLabelPadrao preco = new JLabelPadrao("PREÇO");
		this.preco = new JTextFieldPadrao("");
		preco.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(preco, BorderLayout.CENTER);
		add(panel);
		add(this.preco);
	}
	private void adicionarCalorias(){
		JPanelPadrao panel = gerarPanel();
		JLabelPadrao calorias = new JLabelPadrao("VALOR CALÓRICO");
		calorias.setHorizontalAlignment(SwingConstants.CENTER);
		this.calorias = new JTextFieldPadrao("");
		panel.add(calorias, BorderLayout.CENTER);
		add(panel);
		add(this.calorias);
	}

	private void adicionarButtons(){
		confirm = new JPanelPadrao();
		confirm.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		confirmar = new JButtonPadrao("CONFIRMAR");
		cancelar = new JButtonPadrao("CANCELAR");
		cancelar.setBounds(10, 220, 120, 30);
		confirmar.setBounds(170, 220, 120, 30);
		AdicionarIngredienteListener list = new AdicionarIngredienteListener(this);
		confirmar.addActionListener(list);
		cancelar.addActionListener(list);
		confirm.add(cancelar);
		confirm.add(confirmar);
		add(confirm);
	}
	private void adicionarRadioButton(){
		avaliable = new JPanelPadrao();
		avaliable.setLayout(new FlowLayout(FlowLayout.CENTER));

		disponivel = new JRadioButtonPadrao("DISPONÍVEL");
		indisponivel = new JRadioButtonPadrao("INDISPONÍVEL");
		disponivel.setHorizontalAlignment(SwingConstants.CENTER);
		indisponivel.setHorizontalAlignment(SwingConstants.CENTER);
		new RadioButtonListener(disponivel, indisponivel, null);
		avaliable.add(disponivel);
		avaliable.add(indisponivel);
		add(avaliable);
	}
	private JPanelPadrao gerarPanel(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new BorderLayout());
		return panel;
	}
}
