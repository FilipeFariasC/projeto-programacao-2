package com.excecoes;

public class LoginInvalidoException extends Exception {
	public LoginInvalidoException() {
		super("LOGIN CONT�M DE 3-16 LETRAS OU N�MEROS");
	}
}
