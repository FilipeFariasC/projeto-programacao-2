package com.excecoes;

public class EmailInvalidoException extends Exception {
	public EmailInvalidoException(String msg) {
		super(String.format("%s N�O � UM EMAIL V�LIDO!", msg));
	}
}
