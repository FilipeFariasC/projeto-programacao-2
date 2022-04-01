package com.excecoes;

public class SenhaIncorretaException extends Exception{
	public SenhaIncorretaException() {
		super("A SENHA DIGITADA NÃO CORRESPONDE.");
	}
}
