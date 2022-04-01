package com.excecoes;

public class ValorAbaixoDeZeroException extends Exception {
	public ValorAbaixoDeZeroException(double erro) {
		super(String.format("O valor digitado %.2f � abaixo de 0.", erro));
	}
}
