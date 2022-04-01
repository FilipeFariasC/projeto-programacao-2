package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.excecoes.EmailInvalidoException;
import com.excecoes.UsuarioNaoCadastradoException;
import com.sistema.*;
import com.swing.*;

public class RecuperarSenhaDialog extends JDialogPadrao {
	private JTextFieldPadrao email;
	private JButtonPadrao confirmar;
	private Central central;
	private Component main;
	public RecuperarSenhaDialog(Central central, Component comp) {
		super(comp);
		this.central = central;
		main = this;
		setTitle("RECUPERAR SENHA");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(300, 150);
		adicionarLabels();
		adicionarTextField();
		adicionarButton();
		setVisible(true);
	}
	private void adicionarLabels(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new BorderLayout());
		JLabelPadrao email = new JLabelPadrao("EMAIL");
		email.setFont(ConstantesSwing.ARIAL_RADIO_BUTTON);
		email.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(email, BorderLayout.CENTER);
		add(panel);
	}
	private void adicionarTextField() {
		email = new JTextFieldPadrao("");
		email.setBounds(20, 45, 260, 30);
		add(email);
		repaint();
	}
	private void adicionarButton(){
		JPanelPadrao panel = new JPanelPadrao();
		panel.setLayout(new BorderLayout());
		confirmar = new JButtonPadrao("CONFIRMAR");
		confirmar.setHorizontalAlignment(SwingConstants.CENTER);
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String em = email.getText();
					Util.validarEmail(em);
					Pessoa pessoa = null;
					for(Pessoa cad: central.getCadastrados()){
						if(cad.equals(em)){
							pessoa = cad;
							break;
						}
					}
					if(pessoa == null){
						throw new UsuarioNaoCadastradoException();
					}
					int escolha = JOptionPane.showConfirmDialog(getMain(), "DESEJA CONFIRMAR?", null, JOptionPane.YES_NO_OPTION, 0, ConstantesSwing.QUESTAO);
					if (escolha == JOptionPane.YES_OPTION){
						
						String novaSenha = Util.criarSenha();

						pessoa.setRedefinir(true);
						pessoa.setSenha(novaSenha);
						Mensageiro.enviarEmail(em, "NOVA SENHA PARA LOGIN NO TAPIOCA MAKER", novaSenha);
						Persistencia.salvarCentral(central);

						dispose();
					}
				} catch (EmailInvalidoException | MessagingException | UsuarioNaoCadastradoException e) {
					JOptionPane.showMessageDialog(main, e.getMessage(), "ERRO", 0, ConstantesSwing.ERRO);
				}
			}
		});
		panel.add(confirmar, BorderLayout.CENTER);
		add(panel);
		repaint();
	}
}
