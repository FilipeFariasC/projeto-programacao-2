package com.excecoes;

public class LoginInvalidoException extends Exception {
	public LoginInvalidoException() {
		super("LOGIN CONTÉM DE 3-16 LETRAS OU NÚMEROS");
	}
}
