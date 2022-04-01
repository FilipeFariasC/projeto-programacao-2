package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.*;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;

import com.sistema.*;
import com.swing.*;

public class TelaLogin extends JFramePadrao {
	private JMenuBarPadrao barra;
	private JTextFieldPadrao loginOuEmail,
							 senhaExibida;
	private JPasswordFieldPadrao senha;
	private JLabelPadrao recuperarSenha;
	private JButtonPadrao confirmar,
						  novo;
	private Central central;
	public TelaLogin(Central atual){
		central = atual;
		setSize(500, 350);
		adicionarMenu();
		adicionarLogin();
		adicionarLabel();
		adicionarSenha();
		adicionarSenhaBox();
		adicionarButton();
		adicionarListeners();
		setVisible(true);
	}
	private class RecuperarSenhaListener implements MouseListener{
		private Component main;
		public RecuperarSenhaListener(Component comp) {
			main = comp;
		}
		public void mouseClicked(MouseEvent e) {
			new RecuperarSenhaDialog(central, main);
		}
		public void mouseEntered(MouseEvent e) {	}
		public void mouseExited(MouseEvent e)  {	}
		public void mousePressed(MouseEvent e) {	}
		public void mouseReleased(MouseEvent e){	}
	}
	private class ConfirmarListener implements ActionListener {
		private Window main;
		public ConfirmarListener(Window main) {
			this.main = main;
		}
		public void actionPerformed(ActionEvent e) {
			List<Pessoa> cadastrados = central.getCadastrados();
			int contador = 0;
			for (Pessoa p: cadastrados){
				String senhaUsu = p.getSenha();
				String pass = new String(senha.getPassword());
				String passVisible = senhaExibida.getText();
				if (p.equals(loginOuEmail.getText())){
					if ((senha.isVisible() && senhaUsu.equals(pass)) ||
						(senhaExibida.isVisible() && senhaUsu.equals(passVisible))){
						dispose();
						central.setLogado(p);
						Persistencia.salvarCentral(central);
						if(p.isRedefinir()){
							new TelaRedefinirSenha(central, p);
						} else if(p.isAdministrador()){
							new TelaAdministrador(central);
						} else{
							new TelaCliente(central, (Cliente)p);
						}
					} else{
						JOptionPane.showMessageDialog(main, "SENHA OU LOGIN/EMAIL INCORRETO", "ERRO", 0, ConstantesSwing.ERRO);
						senha.setBackground(Color.RED);
						senhaExibida.setBackground(Color.RED);
					}
					break;
				}
				contador++;
			}
			if(contador == cadastrados.size()){
				JOptionPane.showMessageDialog(main, "NAO HÁ USUÁRIO CADASTRADO COM ESSE EMAIL/LOGIN", null, 0, ConstantesSwing.INFO);
				loginOuEmail.setBackground(Color.RED);
				senha.setBackground(Color.RED);
				senhaExibida.setBackground(Color.RED);
			}
		}
	}
	private class TextFieldListener implements FocusListener{
		public void focusGained(FocusEvent event) {
			JTextField evento = (JTextField) event.getSource();
			if(evento.getBackground() == Color.RED){
				evento.setBackground(Color.WHITE);
			}
		}
		public void focusLost(FocusEvent arg0) {	}
	}
	private void adicionarMenu(){
		barra = new MenuPadrao(central, this);
		barra.setBounds(0, 0, getWidth(), 25);
		add(barra);
	}
	private void adicionarLogin(){
		loginOuEmail = new JTextFieldPadrao("", "DIGITE O LOGIN OU EMAIL");
		loginOuEmail.setBounds(30, 120, 440, 30);
		
		add(loginOuEmail);
	}
	private void adicionarSenha(){
		senha = new JPasswordFieldPadrao("DIGITE SUA SENHA");
		senha.setBounds(30, 180, 360, 30);
		
		senhaExibida = new JTextFieldPadrao("", "DIGITE SUA SENHA");
		senhaExibida.setBounds(30, 180, 360, 30);
		
		add(senha);
		add(senhaExibida);
	}
	private void adicionarSenhaBox(){
		SenhaCheckBox check = new SenhaCheckBox(senha, senhaExibida);
		check.setBounds(390, 180, 90, 30);
		add(check);
		repaint();
	}
	private void adicionarLabel() {
		JLabelPadrao login = new JLabelPadrao("LOGIN DE USUARIOS", ConstantesSwing.CONSTANTIA_TITULO),
				     loginOuEmail = new JLabelPadrao("LOGIN OU EMAIL", ConstantesSwing.ARIAL_RADIO_BUTTON),
				     senha = new JLabelPadrao("SENHA", ConstantesSwing.ARIAL_RADIO_BUTTON);
		login.setBounds(145, 45, 210, 30);
		loginOuEmail.setBounds(210, 90, 90, 30);
		senha.setBounds(230, 150, 50, 30);
		
		recuperarSenha = new JLabelPadrao("RECUPERAR SENHA", ConstantesSwing.ARIAL_RADIO_BUTTON);
		recuperarSenha.setBounds(30, 210, 110, 30);
		recuperarSenha.setForeground(Color.GRAY);
		recuperarSenha.addMouseListener(new RecuperarSenhaListener(this));
		add(login);
		add(loginOuEmail);
		add(senha);
		add(recuperarSenha);
		repaint();
	}
	private void adicionarButton(){
		novo = new JButtonPadrao("CADASTRAR USUARIO");
		confirmar = new JButtonPadrao("CONFIRMAR");
		
		novo.setBounds(30, 250, 200, 30);
		confirmar.setBounds(270, 250, 200, 30);
		
		novo.setIcon(ConstantesSwing.ADD_USER);
		confirmar.setIcon(ConstantesSwing.LOGIN);
		
		add(novo);
		add(confirmar);
	}
	private void adicionarListeners(){
		TextFieldListener list = new TextFieldListener();
		senha.addFocusListener(list);
		senhaExibida.addFocusListener(list);
		loginOuEmail.addFocusListener(list);

		novo.addActionListener(new CadastroListener(this, central));
		confirmar.addActionListener(new ConfirmarListener(this));
	}
	public JTextFieldPadrao getLoginOuEmail() {
		return loginOuEmail;
	}
}
