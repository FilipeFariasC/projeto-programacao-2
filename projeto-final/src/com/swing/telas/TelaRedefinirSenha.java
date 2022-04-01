package com.swing.telas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.excecoes.SenhaIncorretaException;
import com.excecoes.SenhaInvalidaException;
import com.sistema.*;
import com.swing.*;

public class TelaRedefinirSenha extends JFramePadrao{
	private Central central;
	private Pessoa pessoa;
	private JTextFieldPadrao senhaExibida;
	private JPasswordFieldPadrao senha,
								 confirmar;
	private JButtonPadrao botao;
	private JCheckBoxPadrao exibir;
	public TelaRedefinirSenha(Central central, Pessoa pessoa) {
		this.pessoa = pessoa;
		this.central = central;
		central.setLogado(pessoa);
		base();
		setSize(350, 250);
		setVisible(true);
	}
	private void base() {
		adicionarLabels();
		adicionarPasswordField();
		adicionarCheckBox();
		adicionarButton();
	}
	private class ConfirmarListener implements ActionListener{
		private Component comp;
		public ConfirmarListener(Component comp){
			this.comp = comp;
		}
		public void actionPerformed(ActionEvent event) {
			try{
				String pass;
				if(senha.isVisible()){
					pass = new String(senha.getPassword());
				} else{
					pass = senhaExibida.getText();
				}
				String confirm = new String(confirmar.getPassword());
				if(pass.equals(confirm)){
					Util.validarSenha(pass);
					int escolha = JOptionPane.showConfirmDialog(comp, "DESEJA CONFIRMAR?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
					if(escolha == JOptionPane.YES_OPTION){
						pessoa.setSenha(pass);
						pessoa.setRedefinir(false);
						Persistencia.salvarCentral(central);
						dispose();
						Mensageiro.enviarEmail(pessoa.getEmail(), "SUA SENHA DO TAPIOCA MAKER FOI MODIFICADA", "A SENHA ATUAL É: "+pessoa.getSenha());
						if(pessoa instanceof Cliente){
							new TelaCliente(central,(Cliente)pessoa);
						} else{
							new TelaAdministrador(central);
						}
					}
				} else{
					throw new SenhaIncorretaException();
				}
			} catch (SenhaInvalidaException | SenhaIncorretaException e) {
				JOptionPane.showMessageDialog(comp, e.getMessage(), null, 0, ConstantesSwing.ERRO);
			} catch (MessagingException e) {
			}
		}
	}
	private void adicionarLabels() {
		JLabelPadrao senha = new JLabelPadrao("SENHA"),
					 confirmar = new JLabelPadrao("CONFIRMAR SENHA");
		senha.setFont(ConstantesSwing.ARIAL_RADIO_BUTTON);
		confirmar.setFont(ConstantesSwing.ARIAL_RADIO_BUTTON);
		setComponent(senha, 155, 10, 40, 20);
		setComponent(confirmar, 125, 75, 100, 20);
	}
	private void adicionarPasswordField(){
		senhaExibida = new JTextFieldPadrao("", "DIGITE SUA SENHA");
		senha = new JPasswordFieldPadrao("DIGITE SUA SENHA");
		confirmar = new JPasswordFieldPadrao("CONFIRME A SENHA");

		setComponent(senhaExibida, 15, 35, 320, 30);
		setComponent(senha, 15, 35, 320, 30);
		setComponent(confirmar, 15, 100, 320, 30);
	}
	private void adicionarCheckBox(){
		exibir = new SenhaCheckBox(senha, senhaExibida);
		setComponent(exibir, 255, 10, 100, 20);
	}
	private void adicionarButton() {
		botao = new JButtonPadrao("CONFIRMAR");
		setComponent(botao, 15, 155, 320, 30);
		botao.addActionListener(new ConfirmarListener(this));
	}
	private void setComponent(Component comp, int x, int y, int largura, int altura){
		comp.setBounds(x, y, largura, altura);
		add(comp);
	}
}
