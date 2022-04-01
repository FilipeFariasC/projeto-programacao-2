package com.excecoes;

public class IngredienteEmTapiocaException extends Exception {
	public IngredienteEmTapiocaException() {
		super("O(S) INGREDIENTE(S) SELECIONADO(S) ESTÁ(ÃO) SENDO UTILIZADO EM UMA TAPIOCA");
	}
}
