package com.excecoes;

public class ClienteJaCadastradoException extends Exception {
	public ClienteJaCadastradoException() {
		super("USUÁRIO JÁ CADASTRADO");
	}
}
