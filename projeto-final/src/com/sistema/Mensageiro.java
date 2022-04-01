package com.sistema;

import java.text.DateFormat;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;


public abstract class Mensageiro {

	public static boolean enviarEmail(String emailCliente, String assunto, String msg) throws MessagingException{
		Properties p = new Properties();
		String email = "tapioca.maker.20191@gmail.com";
		String senha = "tapiocamaker20191";
		//Para Conexão com o GMAIL
		
		p.put("mail.transport.protocol", "smtp" );
		p.put("mail.smtp.user", email); // EMAIL ACADÊMICO 
        p.put("mail.smtp.host", "smtp.gmail.com"); 
        p.put("mail.smtp.port", "465"); 
        p.put("mail.debug", "true"); 
        p.put("mail.smtp.auth", "true"); 
        p.put("mail.smtp.starttls.enable","true"); 
        p.put("mail.smtp.EnableSSL.enable","true");
        p.put("mail.smtp.socketFactory.port", "465");
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
        p.put("mail.smtp.socketFactory.fallback", "false");
		
        Session sessao = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication(){
        		return new PasswordAuthentication(email, senha);
        		}
        	});
        //DEBUGAR A SESSÃO
        sessao.setDebug(true);
        
        
        Message mensagem = new MimeMessage(sessao);
        
        String emailUser = emailCliente;
    	//EMAIL DO ADM
		mensagem.setFrom(new InternetAddress(email)); // quem vai enviar
		//EMAIL DO USUARIO
		Address[] toUsuario = InternetAddress.parse(emailUser); // o(s) email do usuário
		
		mensagem.setRecipients(Message.RecipientType.TO, toUsuario); // quem vai receber
		mensagem.setSentDate(new Date());
		mensagem.setSubject(assunto); // assunto
		Locale local = new Locale("pt", "pt-br");
		DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.FULL, DateFormat.DEFAULT, local);

		String date = dateFormat.format(new Date());
		String formatado = String.format("%s\nENVIADO: %s\n\n", msg, date);
		mensagem.setText(formatado);//texto da mensagem
		Transport mensageiro = sessao.getTransport("smtps");
        mensageiro.connect("smtp.gmail.com", 465 , email, senha);
      	mensageiro.sendMessage(mensagem, mensagem.getAllRecipients());
        mensageiro.close();
        return true;
	}
}
