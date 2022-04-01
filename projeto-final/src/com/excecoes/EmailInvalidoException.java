package com.excecoes;

public class EmailInvalidoException extends Exception {
	public EmailInvalidoException(String msg) {
		super(String.format("%s NÃO É UM EMAIL VÁLIDO!", msg));
	}
}
