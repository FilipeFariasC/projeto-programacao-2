package com.swing.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.excecoes.*;
import com.sistema.*;
import com.toedter.calendar.JTextFieldDateEditor;
import com.swing.*;

public class TelaCadastro extends JFramePadrao {
	private JTextFieldPadrao nomeUsuario,
							 emailUsuario,
							 loginUsuario,
							 senhaExibida,
							 idade;
	private JPasswordFieldPadrao senha,
								 confirmarSenha;
	private JPanelPadrao painelCalendario;
	private JDateChooserPadrao calendario;
	private JTextFieldDateEditor editorData;
	private JCheckBoxPadrao exibir,
							newsletter;
	private JRadioButtonPadrao masculino,
							   feminino;
	private JButtonPadrao confirmar,
						  limpar,
						  cancelar;
	private Central central;
	private Period tempo;
	public TelaCadastro(Central atual) {
		central = atual;
		setSize(625, getHeight());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		base();
		setVisible(true);
	}
	private class CadastrarListener implements ActionListener{
		private Window main;
		public CadastrarListener(Window comp){
			main = comp;
		}
		public void actionPerformed(ActionEvent event) {
			String nome = nomeUsuario.getText().toUpperCase(),
				   email = emailUsuario.getText(),
				   datap = editorData.getText(),
				   login = loginUsuario.getText(),
				   senhaDigitada = new String(senha.getPassword()),
				   confirmarSenhaDigitada = new String(confirmarSenha.getPassword()), 
				   erro = "SEXO";
			Date data;
			SEXO sexo;
			
				   
			try{
				if (masculino.isSelected()){
					sexo = SEXO.MASCULINO;
				} else {
					sexo = SEXO.FEMININO;
				}
				erro = "NOME";
				Util.eVazia(nome);
				Util.validarNome(nome);
				erro = "EMAIL";
				Util.eVazia(email);
				Util.validarEmail(email);
				erro = "DATA";
				Util.eVazia(datap);
				Util.validarData(datap);
				data = editorData.getDate();
				if(calendario.getTempo().getYears() < 18){
					JOptionPane.showMessageDialog(main, "É PRECISO SER ACIMA DE 18 ANOS PARA CRIAR UMA CONTA", null, 0, ConstantesSwing.INFO);
				}
				erro = "NOME";
				Util.eVazia(login);
				Util.validarLogin(login);
				Util.eVazia(senhaDigitada);
				Util.validarSenha(senhaDigitada);
				if (senhaDigitada.equals(confirmarSenhaDigitada)){
					erro = "CADASTRO";
					if (central.getAdministrador() == null){
						central.cadastrarAdministrador(new Administrador(nome, email, data, login, senhaDigitada, sexo));
					} else{
						central.cadastrarCliente(new Cliente(nome, email, data, login, senhaDigitada, sexo, newsletter.isSelected()));
					}
					Persistencia.salvarCentral(central);
					Window [] windows = Window.getWindows(); 
					for(int i=0;i < windows.length;i++){ 
						windows[i].dispose(); 
					} 
					new TelaLogin(central);
				}
			} catch (NomeInvalidoException | DataInvalidaException |
					LoginInvalidoException | SenhaInvalidaException | 
					EmailInvalidoException | ClienteJaCadastradoException | 
					StringVaziaException e) {
				JOptionPane.showMessageDialog(main, e.getMessage(), "ERRO"+erro, 0, ConstantesSwing.ERRO);
			} 
		}
	}
	
	private class TextFieldListener implements FocusListener {
		public void focusLost(FocusEvent event) {
			JTextField field = (JTextField) event.getSource();
			try {
				Util.eVazia(field.getText());
				if (field == editorData){
					testarData();
				}
			} catch (StringVaziaException e) {
				field.setBackground(Color.YELLOW);
			}
		}
		public void focusGained(FocusEvent event) {
			JTextField field = (JTextField) event.getSource();
			field.setBackground(Color.WHITE);
		}
	}
	private void base(){
		adicionarLabel();
		adicionarTextField();
		adicionarSenhaField();
		adicionarPanel();
		adicionarCheckBox();
		adicionarRadioButton();
		adicionarButton();
		adicionarListener();
	}
	private void testarData(){
		Period diferenca = calendario.getTempo();
		idade.setText("");
		if(diferenca != null){
			if (diferenca.getYears() < 18){
				editorData.setBackground(Color.YELLOW);
				idade.setBackground(Color.YELLOW);
//				idade.setFont(ConstantesSwing.TIMES_NEW_ROMAN_CHECK_BOX);
				idade.setText("APENAS ACIMA DE 18 ANOS");
			}else{
				idade.setBackground(Color.WHITE);
				editorData.setBackground(Color.WHITE);
//				idade.setFont(ConstantesSwing.TIMES_TEXTO);
				idade.setText(String.format("%d anos", diferenca.getYears()));
			}
		}
	}
	private void adicionarLabel(){
		JLabelPadrao cadastro,
					 nome,
					 email,
					 login,
					 senha,
					 confirmarSenha,
					 sexo,
					 idade,					 
					 data;
		cadastro = new JLabelPadrao("CADASTRO DE USUÁRIOS");
		cadastro.setHorizontalTextPosition(JLabelPadrao.CENTER);
		cadastro.setFont(ConstantesSwing.CONSTANTIA_TITULO);
		cadastro.setBounds(190, 20, 260, 30);

		nome = new JLabelPadrao("NOME: ");
		nome.setBounds(20, 60, 50, 25);
		
		email = new JLabelPadrao("EMAIL: ");
		email.setBounds(20, 90, 50, 25);
		
		idade = new JLabelPadrao("IDADE: ");
		idade.setBounds(20, 120, 50, 25);
		
		data = new JLabelPadrao("DATA DE NASCIMENTO:");
		data.setBounds(255, 120, 135, 25);
		
		login = new JLabelPadrao("LOGIN: ");
		login.setBounds(20, 150, 50, 25);
		
		senha = new JLabelPadrao("SENHA:");
		senha.setBounds(20, 180, 50, 25);
		
		confirmarSenha = new JLabelPadrao("CONFIRMAR:");
		confirmarSenha.setBounds(20, 210, 80, 25);
		
		sexo = new JLabelPadrao("SEXO: ");
		sexo.setBounds(20, 240, 50, 25);
		
		
		add(cadastro);
		add(nome);
		add(email);
		add(idade);
		add(data);
		add(login);
		add(senha);
		add(confirmarSenha);
		add(sexo);
	}
	private void adicionarTextField(){
		nomeUsuario = new JTextFieldPadrao("", "DIGITE SEU NOME COMPLETO");
		nomeUsuario.setBounds(80, 60, 520, 25);
		
		emailUsuario = new JTextFieldPadrao("", "DIGITE SEU EMAIL");
		emailUsuario.setBounds(80, 90, 520, 25);

		idade = new JTextFieldPadrao("");
		idade.setBounds(80, 120, 170, 25);
		idade.setBackground(Color.WHITE);
		idade.setForeground(Color.BLACK);
		idade.setOpaque(true);
		idade.setEditable(false);
		idade.setFocusable(false);
		
		senhaExibida = new JTextFieldPadrao("");
		senhaExibida.setBounds(80, 180, 440, 25);
		senhaExibida.setVisible(false);
		
		loginUsuario = new JTextFieldPadrao("", "DIGITE UM LOGIN");
		loginUsuario.setBounds(80, 150, 520, 25);
		
		add(nomeUsuario);
		add(emailUsuario);
		add(idade);
		add(senhaExibida);
		add(loginUsuario);
	}
	private void adicionarSenhaField(){
		senha = new JPasswordFieldPadrao("DIGITE UMA SENHA");
		senha.setBounds(80, 180, 440, 25);
		
		confirmarSenha = new JPasswordFieldPadrao("DIGITE A SUA SENHA NOVAMENTE PARA A CONFIRMAÇÃO");                            
		confirmarSenha.setBounds(110, 210, 490, 25);

		add(senha);
		add(confirmarSenha);
	}
	private void adicionarPanel(){
		painelCalendario = new JPanelPadrao();
		painelCalendario.setBounds(400, 120, 200, 25);
		painelCalendario.setLayout(new BorderLayout());
		calendario = new JDateChooserPadrao();
		painelCalendario.add(calendario, SwingConstants.CENTER);
		editorData = (JTextFieldDateEditor) calendario.getDateEditor();
		add(painelCalendario);
	}
	private void adicionarCheckBox(){
		exibir = new JCheckBoxPadrao("EXIBIR SENHA", "EXIBE A SENHA (A CONFIRMAÇÃO NÃO É EXIBIDA).");
		exibir.setBounds(520, 180, 90, 25);
		exibir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (exibir.isSelected()){
					senhaExibida.setText(new String(senha.getPassword()));
					senha.setVisible(false);
					senhaExibida.setVisible(true);
					repaint();
				} else{
					senha.setText(senhaExibida.getText());
					senhaExibida.setVisible(false);
					senha.setVisible(true);
					senha.requestFocus();
					repaint();
				}
			}
		});
		add(exibir);
		if (central.getAdministrador() != null){
			newsletter = new JCheckBoxPadrao("RECEBER NOVIDADES");
			newsletter.setBounds(490, 240, 110, 25);
			add(newsletter);
		}
	}
	private void adicionarRadioButton(){
		masculino = new JRadioButtonPadrao("MASCULINO");
		feminino = new JRadioButtonPadrao("FEMININO");
		feminino.setSelected(true);
		
		masculino.setBounds(80, 240, 90, 25);
		feminino.setBounds(170, 240, 90, 25);
		
		ActionListener sexo = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String radio = event.getActionCommand();
				switch(radio){
				case "MASCULINO":
					feminino.setSelected(false);
					break;
				case "FEMININO":
					masculino.setSelected(false);
				}
			}
		};
		masculino.addActionListener(sexo);
		feminino.addActionListener(sexo);
		
		add(masculino);
		add(feminino);
	}
	private void adicionarButton(){
		confirmar = new JButtonPadrao("CONFIRMAR");
		confirmar.setBounds(450, 270, 150, 30);
		confirmar.addActionListener(new CadastrarListener(this));
		
		limpar = new JButtonPadrao("LIMPAR");
		limpar.setBounds(235, 270, 150, 30);
		limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeUsuario.setText("");
				emailUsuario.setText("");
			 	loginUsuario.setText("");
			 	senha.setText("");
			 	senhaExibida.setText("");
			 	idade.setText("");
			 	editorData.setText("");
			 	confirmarSenha.setText("");
			}
		});
		
		cancelar = new JButtonPadrao("CANCELAR");
		cancelar.setBounds(20, 270, 150, 30);
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				if (central.getAdministrador() != null){
					new TelaLogin(central);
				}
			}
		});
		
		cancelar.setIcon(ConstantesSwing.CANCELAR);
		limpar.setIcon(ConstantesSwing.LIMPAR);
		confirmar.setIcon(ConstantesSwing.SIGN_IN);
		
		add(confirmar);
		add(limpar);
		add(cancelar);
	}
	private void adicionarListener(){
		TextFieldListener listener = new TextFieldListener();
		nomeUsuario.addFocusListener(listener);
		emailUsuario.addFocusListener(listener);
		loginUsuario.addFocusListener(listener);
		editorData.addFocusListener(listener);
		senha.addFocusListener(listener);
		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {	}
			public void windowIconified(WindowEvent e) {	}
			public void windowDeiconified(WindowEvent e) {	}
			public void windowDeactivated(WindowEvent e) {	}
			public void windowClosing(WindowEvent e) {
				if(central.getAdministrador() != null){
					new TelaLogin(central);
				}	
				}
			public void windowClosed(WindowEvent e) {
			}
			public void windowActivated(WindowEvent e) {	}
		});
	}
}
