package com.excecoes;

public class ValorAbaixoDeZeroException extends Exception {
	public ValorAbaixoDeZeroException(double erro) {
		super(String.format("O valor digitado %.2f é abaixo de 0.", erro));
	}
}
