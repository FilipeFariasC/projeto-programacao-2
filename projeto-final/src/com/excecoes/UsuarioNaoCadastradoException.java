package com.excecoes;

public class UsuarioNaoCadastradoException extends Exception {
	public UsuarioNaoCadastradoException() {
		super("EMAIL DIGITADO N�O CADASTRADO");
	}
}
