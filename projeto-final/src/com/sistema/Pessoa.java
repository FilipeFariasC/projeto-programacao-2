package com.sistema;

import java.util.Date;
/**
 * Representa��o de uma pessoa;
 * @author Desktop
 *
 */
public abstract class Pessoa {
	/**
	 * Representa o nome da pessoa;
	 */
	private String nome;
	/**
	 * Representa o email de acesso da pessoa;
	 */
	private String email;
	/**
	 * Representa o login de acesso da pessoa;
	 */
	private String login;
	/**
	 * Representa a senha de acesso da pessoa;
	 */
	private String senha;
	/**
	 * Representa a data de nascimento da pessoa;
	 */
	private Date dataDeNascimento;
	/**
	 * Representa o sexo da pessoa;
	 */
	private SEXO sexo;
	
	/**
	 * Representa se a pessoa precisa redefinir sua senha ou n�o;
	 */
	private boolean redefinir = false;
	/**
	 * Representa se a pessoa � um administrador ou n�o;
	 */
	private boolean administrador = false;
	
	/**
	 * Inicializa os atributos;
	 * @param nome Nome da pessoa;
	 * @param email Email da pessoa;
	 * @param data Data de nascimento da pessoa;
	 * @param login Login da pessoa;
	 * @param senha Senha da pessoa;
	 * @param sexo Sexo da pessoa;
	 * @param adm Tipo da pessoa;
	 */
	public Pessoa(String nome, String email, Date data, String login, String senha, SEXO sexo, boolean adm){
		this.nome = nome; 
		this.email = email; 
		dataDeNascimento = data;
		this.login = login; 
		this.senha = senha; 
		this.sexo = sexo; 
		administrador = adm;
	}
	/**
	 * Atribui o par�metro ao atributo administrador;
	 * @param p valor a ser atribuido a {@link #administrador};
	 */
	public void setAdministrador(Pessoa p) {
		administrador = true;
	}
	/**
	 * retorna o valor no atributo administrador;
	 * @return {@link #administrador};
	 */
	public boolean isAdministrador() {
		return administrador;
	}
	
	/**
	 * Retorna o valor no atributo nome;
	 * @return {@link #nome};
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Atribui o par�metro ao atributo nome;
	 * @param n valor a ser atribuido a {@link #nome};
	 */
	public void setNome(String n) {
		nome = n;
	}
	/**
	 * Retorna o valor no atributo nome;
	 * @return {@link #nome};
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Atribui o par�metro ao atributo email;
	 * @param e valor a ser atribuido a {@link #email};
	 */
	public void setEmail(String e) {
		email = e;
	}
	/**
	 * Retorna o valor no atributo login;
	 * @return {@link #login};
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * Atribui o par�metro ao atributo login;
	 * @param l valor a ser atribuido a {@link #login};
	 */
	public void setLogin(String l) {
		login = l;
	}
	/**
	 * Retorna o valor no atributo senha;
	 * @return {@link #senha};
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * Atribui o par�metro ao atributo senha;
	 * @param s valor a ser atribuido a {@link #senha};
	 */
	public void setSenha(String s) {
		senha = s;
	}
	/**
	 * Testa para ver se a String par�metro � igual ao email ou login da pessoa que chama o m�todo;
	 * @param loginOuEmail String a ser testada;
	 * @return true caso a String par�metro possui o mesmo email ou login;<br>
	 * false, caso n�o possua nenhum dos dois;
	 */
	public boolean equals(String loginOuEmail){
		return (loginOuEmail.equals(email) ||
				loginOuEmail.equals(login));
	}
	/**
	 * Testa para ver se a pessoa par�metro possui, o mesmo email ou login da pessoa que chama o m�todo;
	 * @param p Pessoa a ser testada;
	 * @return true caso a pessoa par�metro possui o mesmo email ou login;
	 */
	public boolean equals(Pessoa p){
		return (email.equals(p.getEmail()) ||
				login.equals(p.getLogin()));
	}
	
	/**
	 * Retorna o valor no atributo redefinir;
	 * @return {@link #redefinir};
	 */
	public boolean isRedefinir() {
		return redefinir;
	}
	/**
	 * Atribui o par�metro ao atributo nome;
	 * @param red valor a ser atribuido a {@link #redefinir};
	 */
	public void setRedefinir(boolean red) {
		redefinir = red;
	}
	/**
	 * Retorna o valor no atributo dataDeNascimento;
	 * @return {@link #dataDeNascimento};
	 */
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}
	/**
	 * Retorna o valor no atributo sexo;
	 * @return {@link #sexo};
	 */
	public SEXO getSexo() {
		return sexo;
	}
}
