package com.excecoes;

public class IngredienteNaoCadastradoException extends Exception{
	public IngredienteNaoCadastradoException() {
		super("INGREDIENTE DIGITADO NÃO CADASTRADO");
	}
}
