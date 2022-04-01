package com.excecoes;

public class IngredienteCadastradoException extends Exception {
	public IngredienteCadastradoException(){
		super("INGREDIENTE DE MESMO NOME JÁ CADASTRADO!");
	}
}
