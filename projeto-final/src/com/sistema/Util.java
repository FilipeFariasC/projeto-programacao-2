package com.sistema;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excecoes.*;

public final class Util {
	private Util(){
		
	}
	public static boolean validarLogin(String login) throws LoginInvalidoException{
		try{String regex = "[0-9a-zA-Z\\s]{3,16}";
			Pattern padrao = Pattern.compile(regex);
			Matcher match = padrao.matcher(login);
			if(!(match.find() && match.group().equals(login))){
				throw new LoginInvalidoException();
			}
			return true;
		}catch(NullPointerException e){
			throw new LoginInvalidoException();
		}
	}
	public static boolean validarNome(String nome) throws NomeInvalidoException{
		try{
			String regex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
			Pattern padrao = Pattern.compile(regex);
			Matcher match = padrao.matcher(nome);
			if(!(match.find() && match.group().equals(nome))){
				throw new NomeInvalidoException();
			}
			return true;
		}catch(NullPointerException e){
			throw new NomeInvalidoException();
		}
	}
	public static boolean validarData(String data) throws DataInvalidaException{
		try{
			String regex = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
			Pattern padrao = Pattern.compile(regex);
			Matcher match = padrao.matcher(data);
			if(!(match.find() && match.group().equals(data))){
				throw new DataInvalidaException();
			}
			return true;
		}catch(NullPointerException e){
			throw new DataInvalidaException();
		}
	}
	public static boolean validarEmail(String email) throws EmailInvalidoException{
		try{
			String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern padrao = Pattern.compile(regex);
			Matcher match = padrao.matcher(email);
			if(!(match.find() && match.group().equals(email))){
				throw new EmailInvalidoException(email);
			}
			return true;
		}catch(NullPointerException e){
			throw new EmailInvalidoException(email);
		}
	}
	public static boolean validarSenha(String senha) throws SenhaInvalidaException{
		try{
			String regex = "^[a-zA-Z0-9]+[^\\& \\¨ \\% \\$ \\@ \\! \\( \\) \\+ \\=]+";
			Pattern padrao = Pattern.compile(regex);
			Matcher match = padrao.matcher(senha);
			if(!(match.find() && match.group().equals(senha))){
				throw new SenhaInvalidaException();
			}
			return true;
		}catch(NullPointerException e){
			throw new SenhaInvalidaException();
		}
	}
	public static boolean caracteresInvalido(String palavras) throws CaractereInvalidoException{
		String regex = "[\\s \\+ , : ; = \\? ' < > \\. \\- ^ ( ) % ! / \\[ \\] { } ´ ` \\] | \" ¨]+";
		Pattern padrao = Pattern.compile(regex);
		Matcher match = padrao.matcher(palavras);
		if((match.find() && match.group().equals(palavras))){
			throw new CaractereInvalidoException();
		}
		return false;
	}
	public static boolean checarString(String str) throws StringInvalidaException{
		String regex = "^\\s+[\\s\\S]+";
		Pattern padrao = Pattern.compile(regex);
		Matcher match = padrao.matcher(str);
		if((match.find() && match.group().equals(str))){
			throw new StringInvalidaException();
		}
		return false;
	}
	public static boolean eVazia(String linha) throws StringVaziaException{
		for(char letra:linha.toCharArray()){
			if(letra != ' '){
				return false;
			}
		}
		throw new StringVaziaException();
	}
	public static boolean eNumero(String linha) throws NaoENumeroException{
		for(char letra: linha.toCharArray()){
			if(!(Character.isDigit(letra) || letra == '.')){
				throw new NaoENumeroException();
			}
		}
		return true;
	}
	public static String retornaNumero(String linha){
		StringBuilder letras = new StringBuilder();
		for(char letra: linha.toCharArray()){
			if(Character.isDigit(letra) || letra == '.'){
				letras.append(letra);
			}
		}
		return letras.toString();
	}
	public static String letras(){
		String letras = "";
		for (char letra = 'A'; letra <= 'Z'; letra++) {
			letras += "" + letra;
		}
		return letras;
	}
	public static String criarSenha() {
		String retornar = "";
		char letra;
		String l = letras();
		
		String adicionar;
		for (int tamanho = 0; tamanho < 10; tamanho++) {
			letra = l.charAt((int) (Math.random() * l.length()));
			double random = Math.random();
			if (random <= 0.5) {
				adicionar = "" + letra;
				if (random >= 0.25) {
					adicionar = adicionar.toLowerCase();
				}
			} else{
				adicionar = "" + ((int) (Math.random() * 10));
			}
			retornar += adicionar;
		}
		return retornar;
	}
	public static String criarID() {
		String retornar = "";
		char letra;
		String l = letras();
		
		String adicionar;
		for (int tamanho = 0; tamanho < 8; tamanho++) {
			letra = l.charAt((int) (Math.random() * l.length()));
			double random = Math.random();
			if (random >= 0.5) {
				adicionar = "" + letra;
			} else{
				adicionar = "" + ((int) (Math.random() * 10));
			}
			retornar += adicionar;
		}
		return retornar;
	}
}
