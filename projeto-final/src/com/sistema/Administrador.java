package com.sistema;

import java.util.Date;

/**
 * 
 * @author Filipe
 *
 */
public class Administrador extends Pessoa{
	/**
	 * Recebe os par�metros e inicializa as vari�veis atrav�s do construtor do supertipo;
	 * @param nome Nome do usu�rio administrador;
	 * @param email Email do usu�rio administrador;
	 * @param data Data de nascimento do usu�rio administrador;
	 * @param login Login do usu�rio administrador;
	 * @param senha Senha do usu�rio administrador;
	 * @param sexo Sexo do usu�rio administrador;
	 */
	public Administrador(String nome, String email, Date data, String login, String senha, SEXO sexo) {
		super(nome, email, data, login, senha, sexo, true);
	}
}
