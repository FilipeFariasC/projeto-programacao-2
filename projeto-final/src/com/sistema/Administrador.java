package com.sistema;

import java.util.Date;

/**
 * 
 * @author Filipe
 *
 */
public class Administrador extends Pessoa{
	/**
	 * Recebe os parâmetros e inicializa as variáveis através do construtor do supertipo;
	 * @param nome Nome do usuário administrador;
	 * @param email Email do usuário administrador;
	 * @param data Data de nascimento do usuário administrador;
	 * @param login Login do usuário administrador;
	 * @param senha Senha do usuário administrador;
	 * @param sexo Sexo do usuário administrador;
	 */
	public Administrador(String nome, String email, Date data, String login, String senha, SEXO sexo) {
		super(nome, email, data, login, senha, sexo, true);
	}
}
