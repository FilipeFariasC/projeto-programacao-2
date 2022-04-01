package com.excecoes;

public class SenhaInvalidaException extends Exception{
	public SenhaInvalidaException() {
		super("A SENHA DIGITADA INVÁLIDA");
	}
}
