package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.excecoes.StringVaziaException;
import com.sistema.*;
import com.swing.*;

public class TelaEnviarEmail extends JFramePadrao {
	private Central central;
	private ArrayList<Cliente> clientes;
	private JTextFieldPadrao pesquisa,
							 assunto;
	private JTextAreaPadrao texto;
	private DefaultTableModelPadrao modelo;
	private JTable tabela;
	private JScrollPane scrollTabela,
						scrollText;
	private JButtonPadrao limpar,
						  todos,
						  confirmar;
	private JCheckBoxPadrao masculino,
							feminino;
	public TelaEnviarEmail(Central central) {
		this.central = central;
		clientes = central.getClientes();
		setSize(990, 450);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		base();
		setVisible(true);
	}
	private void base() {
		adicionarTabela();
		adicionarColunas();
		adicionarLinhas(clientes);
		adicionarTextArea();
		adicionarPesquisa();
		adicionarBotoes();
		adicionarAssunto();
	}
	private class SelecionarListener implements ActionListener{
		private Component main;
		public SelecionarListener(Component main) {
			this.main = main;
		}
		public void actionPerformed(ActionEvent event) {
			int escolha = JOptionPane.showConfirmDialog(main, "TEM CERTEZA?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
			if(escolha == JOptionPane.YES_OPTION){
				String txt = event.getActionCommand();
				if(txt.equals("LIMPAR SELEÇÃO")){
					tabela.clearSelection();
				} else{
					tabela.selectAll();
				}
			}
		}
	}
	private class PesquisaListener implements KeyListener{
		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			char letra = e.getKeyChar();
			if(Character.isAlphabetic(letra) || letra == '\b'){
				pesquisar();
			}
		}
		public void keyTyped(KeyEvent e) {}
		
	}
	private class ConfirmarListener implements ActionListener{
		private Component main;
		public ConfirmarListener(Component main){
			this.main = main;
		}
		public void actionPerformed(ActionEvent arg0) {
			int[] indices = tabela.getSelectedRows();
			int escolha;
			if(indices.length > 0){
				String erro = "ASSUNTO";
				try {
					Util.eVazia(assunto.getText());
					erro = "TEXTO";
					Util.eVazia(texto.getText());
				
					escolha = JOptionPane.showConfirmDialog(main, "DESEJA ENVIAR O EMAIL?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
					if(escolha == JOptionPane.YES_OPTION){
						for(int indice: indices){
							String email = modelo.getValueAt(indice, 2).toString();
							for(Cliente cliente: clientes){
								if(cliente.equals(email)){
									Mensageiro.enviarEmail(cliente.getEmail(), texto.getText(), assunto.getText());
								}
							}
						}
						escolha = JOptionPane.showConfirmDialog(main, "DESEJA FECHAR?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
						if(escolha == JOptionPane.YES_OPTION){
							dispose();
						}
					}
				} catch (StringVaziaException e) {
					JOptionPane.showMessageDialog(main, e.getMessage(), "ERRO", 0, ConstantesSwing.ERRO);
					if(erro.equals("ASSUNTO")){
						assunto.requestFocus();
					} else{
						texto.requestFocus();
					}
				} catch (MessagingException e) {
					JOptionPane.showMessageDialog(main, String.format("ERRO NO ENVIO DE EMAIL"), "ERRO", 0, ConstantesSwing.ERRO);
				}
				
			} else{
				escolha = JOptionPane.showConfirmDialog(main, "ESCOLHA AO MENOS 1 CLIENTE", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
			}
		}
	}

	private void pesquisar(){
		ArrayList<Cliente> mas = new ArrayList<>();
		ArrayList<Cliente> fem = new ArrayList<>();
		ArrayList<Cliente> clientes = new ArrayList<>();
		for (Cliente cliente: this.clientes){
			if(cliente.isNewsletter()){
				String texto = pesquisa.getText(),
					   nome = cliente.getNome(),
					   email = cliente.getEmail();
				SEXO sexo = cliente.getSexo();
				if (nome.contains(texto.toUpperCase())||
					email.contains(texto)){
					if(masculino.isSelected() && sexo == SEXO.MASCULINO){
						mas.add(cliente);
					} else if(feminino.isSelected() && sexo == SEXO.FEMININO){
						fem.add(cliente);
					} else{
						clientes.add(cliente);
					}
				}
			}
		}
		if(masculino.isSelected()){
			refresh(mas);
		} else if(feminino.isSelected()){
			refresh(fem);
		} else{
			refresh(clientes);
		}
	}
	private void adicionarTabela() {
		modelo = new DefaultTableModelPadrao();
		tabela = new JTable(modelo);
		scrollTabela = new JScrollPane(tabela);
		scrollTabela.setBounds(30, 100, 450, 250);
		scrollTabela.setOpaque(true);
		add(scrollTabela);
	}
	private void adicionarColunas(){
		modelo.addColumn("NOME");
		modelo.addColumn("SEXO");
		modelo.addColumn("EMAIL");
	}
	private void remover(){
		for(;modelo.getRowCount() > 0;){
			modelo.removeRow(0);
		}
	}
	private void refresh(ArrayList<Cliente> clientes){
		remover();
		adicionarLinhas(clientes);
	}
	private void adicionarLinhas(ArrayList<Cliente> clientes) {
		for(Cliente pessoa: clientes){
			if(pessoa.isNewsletter()){
				Object[] obj = {pessoa.getNome(), pessoa.getSexo().toString(), pessoa.getEmail()};
				modelo.addRow(obj);
			}
		}
		DefaultTableCellRenderer meio = new DefaultTableCellRenderer();
		meio.setHorizontalAlignment(SwingConstants.CENTER);
		for (int c = 0; c < tabela.getColumnCount(); c++){
			int largura;
			TableColumn coluna = tabela.getColumnModel().getColumn(c);
			if(c == 0){
				largura = 180;
			} else if(c == 1){
				largura = 100;
			} else{
				largura = 250;
			}
			coluna.setCellRenderer(meio);
			coluna.setPreferredWidth(largura);
		}
		tabela.repaint();
	}
	private void adicionarTextArea(){
		texto = new JTextAreaPadrao("");
		texto.setLineWrap(true);
		texto.setWrapStyleWord(true);
		scrollText = new JScrollPane(texto);
		scrollText.setBounds(500, 100, 450, 250);
		add(scrollText);
	}
	private void adicionarBotoes() {
		limpar = new JButtonPadrao("LIMPAR SELEÇÃO");
		todos = new JButtonPadrao("SELECIONAR TODOS");
		confirmar = new JButtonPadrao("CONFIRMAR");
		
		SelecionarListener list = new SelecionarListener(this);
		limpar.addActionListener(list);
		todos.addActionListener(list);
		confirmar.addActionListener(new ConfirmarListener(this));
		
		limpar.setBounds(30, 360, 200, 30);
		todos.setBounds(280, 360, 200, 30);
		confirmar.setBounds(750, 360, 200, 30);

		limpar.setIcon(ConstantesSwing.LIMPAR);
		todos.setIcon(ConstantesSwing.SELECIONAR_TODOS);
		confirmar.setIcon(ConstantesSwing.SIGN_IN);
		
		add(limpar);
		add(todos);
		add(confirmar);
	}
	private void adicionarPesquisa(){
		JLabelPadrao label = new JLabelPadrao("PESQUISA");
		label.setBounds(30, 10, 450, 20);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(ConstantesSwing.ARIAL_RADIO_BUTTON);
		masculino = new JCheckBoxPadrao("MASCULINO");
		feminino = new JCheckBoxPadrao("FEMININO");
		pesquisa = new JTextFieldPadrao("");
		
		masculino.setBounds(30, 30, 80,30);
		feminino.setBounds(410, 30, 80, 30);
		pesquisa.setBounds(30, 65, 450, 30);
		
		pesquisa.addKeyListener(new PesquisaListener());
		ActionListener list = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String txt = event.getActionCommand();
				if(txt.equals("MASCULINO")){
					feminino.setSelected(false);
				} else{
					masculino.setSelected(false);
				}
				pesquisar();
			}
		};
		masculino.addActionListener(list);
		feminino.addActionListener(list);
		add(masculino);
		add(feminino);
		add(label);
		add(pesquisa);
	}
	private void adicionarAssunto(){
		JLabelPadrao label = new JLabelPadrao("ASSUNTO");
		label.setBounds(500, 30, 450, 30);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(ConstantesSwing.ARIAL_RADIO_BUTTON);
		
		assunto = new JTextFieldPadrao("");
		assunto.setBounds(500, 65, 450, 30);
		
		add(label);
		add(assunto);
	}
}
