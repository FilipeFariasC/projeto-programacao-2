package com.excecoes;

public class TapiocaNaoDisponivelException extends Exception {
	public TapiocaNaoDisponivelException() {
		super("UMA OU MAIS TAPIOCAS DO SEU CARRINHO EST�O INDISPON�VEIS");
	}
}
